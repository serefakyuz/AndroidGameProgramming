package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
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
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class StaticDinamicBodyTipleri extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
    private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
    private FixtureDef fixDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
	Scene sahne;
	
	// Texture nesneleri tan�mlan�yor
	private Texture texSaha, texOyuncu1, texOyuncu2;
	private Texture texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
					texSahaSolAlt, texSahaSolUst;
	
	// TextureRegion nesneleri tan�mlan�yor
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private TextureRegion texRegSahaUst, texRegSahaAlt, texRegSahaSagAlt, 
					texRegSahaSagUst, texRegSahaSolAlt, texRegSahaSolUst;
	
	// Sprite nesneleri tan�mlan�yor
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	private Sprite spriteSahaUst, spriteSahaAlt, spriteSahaSagAlt, 
					spriteSahaSagUst, spriteSahaSolAlt, spriteSahaSolUst;
	
	private Body bodyOyuncu1, bodyOyuncu2;
	// Saha kenarlar�(static body olmal�)
	private Body bodySahaUst, bodySahaAlt, bodySahaSagAlt, bodySahaSagUst, bodySahaSolAlt, bodySahaSolUst;
    
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
		
		// Sahay� kaplayan textureRegion nesneleri olu�turuluyor
		texRegSahaAlt = TextureRegionFactory.createFromAsset(texSahaAlt, this, "gfx/duvar.png", 0, 0);
		texRegSahaUst = TextureRegionFactory.createFromAsset(texSahaUst, this, "gfx/duvar.png", 0, 0);
		texRegSahaSagAlt = TextureRegionFactory.createFromAsset(texSahaSagAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolAlt = TextureRegionFactory.createFromAsset(texSahaSolAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSagUst = TextureRegionFactory.createFromAsset(texSahaSagUst, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolUst = TextureRegionFactory.createFromAsset(texSahaSolUst, this, "gfx/kaleduvari.png", 0, 0);
		
		Texture [] textures = {texSaha, texOyuncu1, texOyuncu2, texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
				texSahaSolAlt, texSahaSolUst};
		
		// Texture nesneleri y�kleniyor.
		mEngine.getTextureManager().loadTextures(textures);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		// TODO Auto-generated method stub		
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
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
		spriteOyuncu2 = new Sprite(600, 480 - 160, texRegOyuncu2);
		
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
		
		// H�z verme i�lemleri yap�l�yor
		bodyOyuncu1.setLinearVelocity(-10, 10);
		bodyOyuncu2.setLinearVelocity(-10, -10);
		
		// bodyOyuncu1 nesnesine a��rl�k veriliyor
		MassData massDataO1 = bodyOyuncu1.getMassData();
		massDataO1.mass = 200;
		bodyOyuncu1.setMassData(massDataO1);
		
		// bodyOyuncu2 nesnesine a��rl�k veriliyor
		MassData massDataO2 = bodyOyuncu2.getMassData();
		massDataO2.mass = 50;
		bodyOyuncu2.setMassData(massDataO2);
		
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
		
		this.sahne.registerUpdateHandler(physicsWorld);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}   
}

