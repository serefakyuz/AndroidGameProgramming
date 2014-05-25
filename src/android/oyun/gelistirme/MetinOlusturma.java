package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
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
public class MetinOlusturma extends BaseGameActivity
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
    private PhysicsWorld physicsWorld;
    private FixtureDef fixDef = PhysicsFactory.createFixtureDef(1f, 1f, 1f);
	Scene sahne;
	
	// Texture nesneleri tanımlanıyor
	private Texture texSaha, texOyuncu1, texOyuncu2;
	private Texture texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
					texSahaSolAlt, texSahaSolUst;
	
	// TextureRegion nesneleri tanımlanıyor
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private TextureRegion texRegSahaUst, texRegSahaAlt, texRegSahaSagAlt, 
					texRegSahaSagUst, texRegSahaSolAlt, texRegSahaSolUst;
	
	// Sprite nesneleri tanımlanıyor
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	private Sprite spriteSahaUst, spriteSahaAlt, spriteSahaSagAlt, 
					spriteSahaSagUst, spriteSahaSolAlt, spriteSahaSolUst;
	
	
	// Body nesneleri oluşuyor
	private Body bodyOyuncu1, bodyOyuncu2;
	// Saha kenarları(static body olmalı)
	private Body bodySahaUst, bodySahaAlt, bodySahaSagAlt, bodySahaSagUst, bodySahaSolAlt, bodySahaSolUst;
	
	// ************************************ //
	// Ekrana metin yazabilmek için gereken
	// nesneler tanımlanıyor
	Font font;
	BuildableTexture bTex;
	ChangeableText cTexSkor1, cTexSkor2;
    
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
		
		// Texture nesneleri oluşturuluyor
		texSaha = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		texOyuncu1 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texOyuncu2 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		
		texSahaAlt = new Texture(1024, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaUst = new Texture(1024, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSagAlt = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSolAlt = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSagUst = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texSahaSolUst = new Texture(32, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		// TextureRegion nesneleri oluşturuluyor
		texRegSaha = TextureRegionFactory.createFromAsset(texSaha, this, "gfx/Arkaplan.jpg", 0, 0);
		texRegOyuncu1 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu2 = TextureRegionFactory.createFromAsset(texOyuncu2, this, "gfx/kol2.png", 0, 0);
		

		// Sahayı kaplayan textureRegion nesneleri oluşturuluyor
		texRegSahaAlt = TextureRegionFactory.createFromAsset(texSahaAlt, this, "gfx/duvar.png", 0, 0);
		texRegSahaUst = TextureRegionFactory.createFromAsset(texSahaUst, this, "gfx/duvar.png", 0, 0);
		texRegSahaSagAlt = TextureRegionFactory.createFromAsset(texSahaSagAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolAlt = TextureRegionFactory.createFromAsset(texSahaSolAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSagUst = TextureRegionFactory.createFromAsset(texSahaSagUst, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolUst = TextureRegionFactory.createFromAsset(texSahaSolUst, this, "gfx/kaleduvari.png", 0, 0);
		
		// ***************************** //
		// btex nesnesi oluşturuluyor.
		if(bTex == null)
		{
			bTex = new BuildableTexture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		}
		else
		{
			bTex = null;
			bTex = new BuildableTexture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		}
		
		// BuildableTexture sınıfından türettiðimiz bTex nesnesi de 
		// donanıma yüklenmeye ihtiyaç duyar. Temelde bir Texture nesnesidir.
		Texture [] textures = {bTex, texSaha, texOyuncu1, texOyuncu2, texSahaUst, 
				texSahaAlt, texSahaSagAlt, texSahaSagUst,  texSahaSolAlt, texSahaSolUst};
		// Texture nesneleri yükleniyor.
		mEngine.getTextureManager().loadTextures(textures);
		
		// ************************* //
		// font nesnesi oluşturuluyor
		this.font = FontFactory.createStrokeFromAsset(this.bTex, this, "gfx/CONTACT.ttf", 75, true, Color.WHITE, 2, Color.rgb(97, 134, 147));
		
		// font nesneleri donanıma yükleniyor
		mEngine.getFontManager().loadFont(font);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		// TODO Auto-generated method stub		
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
		
		
		nesneleriOlustur();
		
		// ************************************ //
		// ChangeableText nesneleri oluşturuluyor
		cTexSkor1 = new ChangeableText(430, 40, this.font, "", 1);
		cTexSkor2 = new ChangeableText(330, 40, font, "", 1);
		
		cTexSkor1.setText("5");
		cTexSkor2.setText("2");
		
		this.sahne.attachChild(cTexSkor1);
		this.sahne.attachChild(cTexSkor2);
		
		this.sahne.registerUpdateHandler(physicsWorld);
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}


	// Body, sprite nesnelerinin oluşturulması ve sahneye 
	// çizdirilme işlemleri bu metotta yapılmaktadır
	private void nesneleriOlustur()
	{

		// Sahayı kaplayan sprite nesneleri
		spriteSahaUst = new Sprite(0.0f, 5.0f, this.texRegSahaUst);
		spriteSahaAlt = new Sprite(0.0f, CAMERA_HEIGHT - 37.0f, this.texRegSahaAlt);
		spriteSahaSagUst = new Sprite(CAMERA_WIDTH - 37.0f, -93.0f, this.texRegSahaSagUst);
		spriteSahaSagAlt = new Sprite(CAMERA_WIDTH - 37.0f, 318.0f, this.texRegSahaSagAlt);
		spriteSahaSolUst = new Sprite(5.0f, -93.0f, this.texRegSahaSolUst);
		spriteSahaSolAlt = new Sprite(5.0f, 318.0f, this.texRegSahaSolAlt);
		
		// Sprite nesnesi oluşturuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		
		// SpriteOyuncu1 oluşturuluyor. 
		spriteOyuncu1 = new Sprite(600, CAMERA_HEIGHT/2 - 64, texRegOyuncu1);
		
		// SpriteOyuncu2 oluşturuluyor.
		spriteOyuncu2 = new Sprite(50, CAMERA_HEIGHT/2 - 64, texRegOyuncu2);
		
		// Body nesneleri oluşturuluyor
		bodyOyuncu1 = PhysicsFactory.createCircleBody(physicsWorld,spriteOyuncu1.getX() + 64,spriteOyuncu1.getY() + 64, 47, 0,BodyType.DynamicBody, fixDef);
		bodyOyuncu2 = PhysicsFactory.createCircleBody(physicsWorld,spriteOyuncu2.getX() + 64,spriteOyuncu2.getY() + 64, 47, 0,BodyType.DynamicBody, fixDef);
		
		// Saha kenarlarını kaplayan body nesneleri
		bodySahaUst = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaUst, BodyType.StaticBody, fixDef);
		bodySahaAlt = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaAlt, BodyType.StaticBody, fixDef);
		bodySahaSagUst = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSagUst, BodyType.StaticBody, fixDef);
		bodySahaSagAlt = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSagAlt, BodyType.StaticBody, fixDef);
		bodySahaSolUst = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSolUst, BodyType.StaticBody, fixDef);
		bodySahaSolAlt = PhysicsFactory.createBoxBody(this.physicsWorld, spriteSahaSolAlt, BodyType.StaticBody, fixDef);
		
		// Body ve Sprite nesneleri birbirlerine baðlanıyor
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu1, bodyOyuncu1, true, true));
		// Sahanın kenarlarını kaplayan static body ve sprite nesneleri birbirlerine baðlanıyor
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaUst, bodySahaUst, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaAlt, bodySahaAlt, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSagUst, bodySahaSagUst, true, true));		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSagAlt, bodySahaSagAlt, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSolUst, bodySahaSolUst, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteSahaSolAlt, bodySahaSolAlt, true, true));
		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu2, bodyOyuncu2, true, true));
		
		// Sprite nesneleri ekrana(sahneye) çizdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		
		// Saha kenarlarının sprite nesneleri ekrana(sahneye) çizdiriliyor.
		this.sahne.attachChild(spriteSahaUst);
		this.sahne.attachChild(spriteSahaAlt);
		this.sahne.attachChild(spriteSahaSagUst);
		this.sahne.attachChild(spriteSahaSagAlt);
		this.sahne.attachChild(spriteSahaSolUst);
		this.sahne.attachChild(spriteSahaSolAlt);
		
		// Sprite nesnelerinin dokunma alanları tanımlanıyor.
		this.sahne.registerTouchArea(spriteOyuncu1);
		this.sahne.registerTouchArea(spriteOyuncu2);
	}
}
