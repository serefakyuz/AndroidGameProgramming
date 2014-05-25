package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.particle.ParticleSystem;
import org.anddev.andengine.entity.particle.emitter.CircleOutlineParticleEmitter;
import org.anddev.andengine.entity.particle.initializer.RotationInitializer;
import org.anddev.andengine.entity.particle.initializer.VelocityInitializer;
import org.anddev.andengine.entity.particle.modifier.ScaleModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.hardware.SensorManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class ParcacikSistemleri extends BaseGameActivity
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
    private PhysicsWorld physicsWorld;
    private FixtureDef fixDef = PhysicsFactory.createFixtureDef(1f, 1f, 1f);
	Scene sahne;
	
	// Texture nesneleri tan�mlan�yor
	private Texture texSaha, texOyuncu1, texOyuncu2;
	private Texture texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
					texSahaSolAlt, texSahaSolUst;
	private Texture texParcacik;
	
	// TextureRegion nesneleri tan�mlan�yor
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private TextureRegion texRegSahaUst, texRegSahaAlt, texRegSahaSagAlt, 
					texRegSahaSagUst, texRegSahaSolAlt, texRegSahaSolUst;
	private TextureRegion texRegParcacik;
	
	// Sprite nesneleri tan�mlan�yor
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	private Sprite spriteSahaUst, spriteSahaAlt, spriteSahaSagAlt, 
					spriteSahaSagUst, spriteSahaSolAlt, spriteSahaSolUst;
	
	// Par�ac�k sistemleri i�in gerekli nesneler tan�mlan�yor
	private CircleOutlineParticleEmitter particleEmitterParcacik;
	private ParticleSystem parcacikSistemi;
	private float parcacikX, parcacikY;
	
	// Body nesneleri olu�uyor
	private Body bodyOyuncu1, bodyOyuncu2;
	// Saha kenarlar�(static body olmal�)
	private Body bodySahaUst, bodySahaAlt, bodySahaSagAlt, bodySahaSagUst, bodySahaSolAlt, bodySahaSolUst;
    
	TimerHandler timerParcacikSistemiBitis;
	private boolean sahaUstTemas = false, sahaAltTemas = false, sahaSagUstTemas = false, sahaSagAltTemas = false,
			sahaSolUstTemas = false, sahaSolAltTemas = false;
	
	@Override
	public Engine onLoadEngine() 
	{
		// TODO Auto-generated method stub
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), camera);
        engineOptions.getTouchOptions().setRunOnUpdateThread(true);
        engine = new Engine(engineOptions);
		
		return engine;
	}

	@Override
	public void onLoadResources() 
	{
		// TODO Auto-generated method stub
		
		// Texture nesneleri olu�turuluyor
		texSaha = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		texOyuncu1 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texOyuncu2 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// Par�ac�k Texture nesnesi olu�turuluyor
		texParcacik = new Texture(16, 16, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		texSahaAlt = new Texture(1024, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaUst = new Texture(1024, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSagAlt = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSolAlt = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSagUst = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSolUst = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		// TextureRegion nesneleri olu�turuluyor
		texRegSaha = TextureRegionFactory.createFromAsset(texSaha, this, "gfx/Arkaplan.jpg", 0, 0);
		texRegOyuncu1 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu2 = TextureRegionFactory.createFromAsset(texOyuncu2, this, "gfx/kol2.png", 0, 0);
		// Par�ac�k Texture nesnesi olu�turuluyor
		texRegParcacik = TextureRegionFactory.createFromAsset(texParcacik, this, "gfx/particlekar.png", 0, 0);
		
		// Sahay� kaplayan textureRegion nesneleri olu�turuluyor
		texRegSahaAlt = TextureRegionFactory.createFromAsset(texSahaAlt, this, "gfx/duvar.png", 0, 0);
		texRegSahaUst = TextureRegionFactory.createFromAsset(texSahaUst, this, "gfx/duvar.png", 0, 0);
		texRegSahaSagAlt = TextureRegionFactory.createFromAsset(texSahaSagAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolAlt = TextureRegionFactory.createFromAsset(texSahaSolAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSagUst = TextureRegionFactory.createFromAsset(texSahaSagUst, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolUst = TextureRegionFactory.createFromAsset(texSahaSolUst, this, "gfx/kaleduvari.png", 0, 0);
		
		Texture [] textures = {texSaha, texOyuncu1, texOyuncu2, texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
				texSahaSolAlt, texSahaSolUst, texParcacik};
		
		// Texture nesneleri y�kleniyor.
		mEngine.getTextureManager().loadTextures(textures);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		// TODO Auto-generated method stub		
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
		
		particleEmitterParcacik = new CircleOutlineParticleEmitter(parcacikX, parcacikY, 10,10);
		parcacikSistemi = new ParticleSystem(particleEmitterParcacik, 25,250, 100, texRegParcacik);
		
		parcacikSistemi.addParticleInitializer(new VelocityInitializer(-100,100, -100, 100));
		parcacikSistemi.addParticleInitializer(new RotationInitializer(45.0f, 145.0f));
		parcacikSistemi.addParticleModifier(new ScaleModifier(1.0f, 0.00f, 0.75f, 1.0f));
			
		parcacikSistemi.setParticlesSpawnEnabled(false);
		
		nesneleriOlustur();
		carpismaKontrolEt();
		
		bodyOyuncu1.setLinearVelocity(0, 300);
		
		this.sahne.attachChild(parcacikSistemi);

		this.sahne.registerUpdateHandler(physicsWorld);
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}

	// �arp��malar�n kontrol edilece�i metod
	// Meydana gelen �arp��malar sonucunda, par�ac�k sistemleri
	// �arp��malar�n oldu�u b�lgede aktif hale gelecek.
	// B�ylece par�ac�klar do�al bir bi�imde buz s��ramas� g�r�n�m� verecek.
	private void carpismaKontrolEt()
	{
		this.sahne.registerUpdateHandler(new IUpdateHandler() 
		{
			@Override
			public void reset()
			{
				
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) 
			{
				
				if(spriteOyuncu1.collidesWith(spriteSahaAlt))
				{
					if(!sahaAltTemas)
					{
					parcacikX = spriteOyuncu1.getX() + 64;
					parcacikY = CAMERA_HEIGHT - 34;
					particleEmitterParcacik.setCenter(parcacikX, parcacikY);
					parcacikSistemi.setParticlesSpawnEnabled(true);
					mEngine.registerUpdateHandler(timerParcacikSistemiBitis=new TimerHandler(0.1f,false, new ITimerCallback()
	                {                      
	                    public void onTimePassed(final TimerHandler pTimerHandler)
	                    {  
	                    	parcacikSistemi.setParticlesSpawnEnabled(false);
	                    	mEngine.unregisterUpdateHandler(timerParcacikSistemiBitis);
	                    }
	                }));			
					sahaAltTemas = true;
					}
				}
				else
				{
					sahaAltTemas = false;
				}

			
				if(spriteOyuncu1.collidesWith(spriteSahaUst))
				{
					if(!sahaUstTemas)
					{	
						parcacikX = spriteOyuncu1.getX() + 64;
						parcacikY = 30;
						particleEmitterParcacik.setCenter(parcacikX, parcacikY);
						parcacikSistemi.setParticlesSpawnEnabled(true);
						mEngine.registerUpdateHandler(timerParcacikSistemiBitis=new TimerHandler(0.1f,false, new ITimerCallback()
		                {                      
		                    public void onTimePassed(final TimerHandler pTimerHandler)
		                    {  
		                    	parcacikSistemi.setParticlesSpawnEnabled(false);
		                    	mEngine.unregisterUpdateHandler(timerParcacikSistemiBitis);
		                    }
		                }));		
						
						sahaUstTemas = true;
					}
				}
				else
				{
					sahaUstTemas = false;
				}
				

				if(spriteOyuncu1.collidesWith(spriteSahaSagAlt))
				{
					if(!sahaSagAltTemas)
					{						
						parcacikX = spriteOyuncu1.getX() + 128;
						parcacikY = spriteOyuncu1.getY() + 64f;
						particleEmitterParcacik.setCenter(parcacikX, parcacikY);
						parcacikSistemi.setParticlesSpawnEnabled(true);
						mEngine.registerUpdateHandler(timerParcacikSistemiBitis=new TimerHandler(0.1f,false, new ITimerCallback()
		                {                      
		                    public void onTimePassed(final TimerHandler pTimerHandler)
		                    {  
		                    	parcacikSistemi.setParticlesSpawnEnabled(false);
		                    	mEngine.unregisterUpdateHandler(timerParcacikSistemiBitis);
		                    }
		                }));			
						
						sahaSagAltTemas = true;
					}
				}
				else
				{
					sahaSagAltTemas = false;
				}
				
				
				if(spriteOyuncu1.collidesWith(spriteSahaSagUst))
				{		
					if(!sahaSagUstTemas)
					{
						
						parcacikX = spriteOyuncu1.getX() + 128;
						parcacikY = spriteOyuncu1.getY() + 64f;
						particleEmitterParcacik.setCenter(parcacikX, parcacikY);
						parcacikSistemi.setParticlesSpawnEnabled(true);
						mEngine.registerUpdateHandler(timerParcacikSistemiBitis=new TimerHandler(0.1f,false, new ITimerCallback()
		                {                      
		                    public void onTimePassed(final TimerHandler pTimerHandler)
		                    {  
		                    	parcacikSistemi.setParticlesSpawnEnabled(false);
		                    	mEngine.unregisterUpdateHandler(timerParcacikSistemiBitis);
		                    }
		                }));			
						
						sahaSagUstTemas = true;
					}
				}
				else
				{
					sahaSagUstTemas = false;
				}

				if(spriteOyuncu1.collidesWith(spriteSahaSolUst))
				{	
					
					if(!sahaSolUstTemas)
					{
						
						parcacikX = spriteOyuncu1.getX();
						parcacikY = spriteOyuncu1.getY() + 64f;
						particleEmitterParcacik.setCenter(parcacikX, parcacikY);
						parcacikSistemi.setParticlesSpawnEnabled(true);
						mEngine.registerUpdateHandler(timerParcacikSistemiBitis=new TimerHandler(0.1f,false, new ITimerCallback()
		                {                      
		                    public void onTimePassed(final TimerHandler pTimerHandler)
		                    {  
		                    	parcacikSistemi.setParticlesSpawnEnabled(false);
		                    	mEngine.unregisterUpdateHandler(timerParcacikSistemiBitis);
		                    }
		                }));			
						
						sahaSolUstTemas = true;
					}					
				}
				else
				{
					sahaSolUstTemas = false;
				}
				
				if(spriteOyuncu1.collidesWith(spriteSahaSolAlt))
				{	
					
					if(!sahaSolAltTemas)
					{
						
						parcacikX = spriteOyuncu1.getX();
						parcacikY = spriteOyuncu1.getY() + 64f;
						particleEmitterParcacik.setCenter(parcacikX, parcacikY);
						parcacikSistemi.setParticlesSpawnEnabled(true);
						mEngine.registerUpdateHandler(timerParcacikSistemiBitis=new TimerHandler(0.01f,false, new ITimerCallback()
		                {                      
		                    public void onTimePassed(final TimerHandler pTimerHandler)
		                    {  
		                    	parcacikSistemi.setParticlesSpawnEnabled(false);
		                    	mEngine.unregisterUpdateHandler(timerParcacikSistemiBitis);
		                    }
		                }));			
						
						sahaSolAltTemas = true;
					}
				}
				else
				{
					sahaSolAltTemas = false;
				}
			}
		});
	}
	
	// Body, sprite nesnelerinin olu�turulmas� ve sahneye 
	// �izdirilme i�lemleri bu metotta yap�lmaktad�r
	private void nesneleriOlustur()
	{

		// Sahay� kaplayan sprite nesneleri
		spriteSahaUst = new Sprite(0.0f, 5.0f, this.texRegSahaUst);
		spriteSahaAlt = new Sprite(0.0f, CAMERA_HEIGHT - 37.0f, this.texRegSahaAlt);
		spriteSahaSagUst = new Sprite(CAMERA_WIDTH - 37.0f, -93.0f, this.texRegSahaSagUst);
		spriteSahaSagAlt = new Sprite(CAMERA_WIDTH - 37.0f, 318.0f, this.texRegSahaSagAlt);
		spriteSahaSolUst = new Sprite(5.0f, -93.0f, this.texRegSahaSolUst);
		spriteSahaSolAlt = new Sprite(5.0f, 318.0f, this.texRegSahaSolAlt);
		
		// Sprite nesnesi olu�turuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		
		// SpriteOyuncu1 olu�turuluyor. 
		spriteOyuncu1 = new Sprite(600, 32, texRegOyuncu1);
		
		// SpriteOyuncu2 olu�turuluyor.
		spriteOyuncu2 = new Sprite(200, 240 - 64, texRegOyuncu2);
		
		// Body nesneleri olu�turuluyor
		bodyOyuncu1 = PhysicsFactory.createCircleBody(physicsWorld,spriteOyuncu1.getX() + 64,spriteOyuncu1.getY() + 64, 47, 0,BodyType.DynamicBody, fixDef);
		bodyOyuncu2 = PhysicsFactory.createCircleBody(physicsWorld,spriteOyuncu2.getX() + 64,spriteOyuncu2.getY() + 64, 47, 0,BodyType.DynamicBody, fixDef);
		
		// Saha kenarlar�n� kaplayan body nesneleri
		bodySahaUst = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaUst, BodyType.StaticBody, fixDef);
		bodySahaAlt = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaAlt, BodyType.StaticBody, fixDef);
		bodySahaSagUst = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSagUst, BodyType.StaticBody, fixDef);
		bodySahaSagAlt = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSagAlt, BodyType.StaticBody, fixDef);
		bodySahaSolUst = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSolUst, BodyType.StaticBody, fixDef);
		bodySahaSolAlt = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSolAlt, BodyType.StaticBody, fixDef);
		
		// Body ve Sprite nesneleri birbirlerine ba�lan�yor
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu1, bodyOyuncu1, true, true));
		// Sahan�n kenarlar�n� kaplayan static body ve sprite nesneleri birbirlerine ba�lan�yor
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaUst, bodySahaUst, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaAlt, bodySahaAlt, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSagUst, bodySahaSagUst, true, true));		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSagAlt, bodySahaSagAlt, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSolUst, bodySahaSolUst, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSolAlt, bodySahaSolAlt, true, true));
		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu2, bodyOyuncu2, true, true));
		
		// Sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		
		// Saha kenarlar�n�n sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahne.attachChild(spriteSahaUst);
		this.sahne.attachChild(spriteSahaAlt);
		this.sahne.attachChild(spriteSahaSagUst);
		this.sahne.attachChild(spriteSahaSagAlt);
		this.sahne.attachChild(spriteSahaSolUst);
		this.sahne.attachChild(spriteSahaSolAlt);
		
		// Sprite nesnelerinin dokunma alanlar� tan�mlan�yor.
		this.sahne.registerTouchArea(spriteOyuncu1);
		this.sahne.registerTouchArea(spriteOyuncu2);
	}
}

