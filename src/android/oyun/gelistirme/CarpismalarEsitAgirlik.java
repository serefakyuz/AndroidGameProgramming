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
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class CarpismalarEsitAgirlik extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
    private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
    private PhysicsWorld physicsWorldd = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
    private FixtureDef fixDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
	Scene sahne;
	
	private Texture texSaha, texOyuncu1, texOyuncu2, texOyuncu3;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2, texRegOyuncu3;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2, spriteOyuncu3;
	private Body bodyOyuncu1, bodyOyuncu2, bodyOyuncu3;
    
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
		texOyuncu3 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		// TextureRegion nesneleri olu�turuluyor
		texRegSaha = TextureRegionFactory.createFromAsset(texSaha, this, "gfx/Arkaplan.jpg", 0, 0);
		texRegOyuncu1 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu2 = TextureRegionFactory.createFromAsset(texOyuncu2, this, "gfx/kol2.png", 0, 0);
		texRegOyuncu3 = TextureRegionFactory.createFromAsset(texOyuncu2, this, "gfx/kol2.png", 0, 0);
		
		
		Texture [] textures = {texSaha, texOyuncu1, texOyuncu2, texOyuncu3};
		
		// Texture nesneleri y�kleniyor.
		mEngine.getTextureManager().loadTextures(textures);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		// TODO Auto-generated method stub
		
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		
		// Sprite nesnesi olu�turuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		
		// SpriteOyuncu1 olu�turuluyor. 
		spriteOyuncu1 = new Sprite(600, 32, texRegOyuncu1);
		
		
		// SpriteOyuncu2 olu�turuluyor.
		spriteOyuncu2 = new Sprite(600, 480 - 160, texRegOyuncu2);
		spriteOyuncu3 = new Sprite(600, 480 - 160, texRegOyuncu2);
		
		// Body nesneleri olu�turuluyor
		bodyOyuncu1 = PhysicsFactory.createCircleBody(physicsWorld,spriteOyuncu1.getX()+64,spriteOyuncu1.getY()+64,47,0,BodyType.DynamicBody, fixDef);
		bodyOyuncu2 = PhysicsFactory.createCircleBody(physicsWorldd,spriteOyuncu2.getX()+64,spriteOyuncu2.getY()+64,47,0,BodyType.DynamicBody, fixDef);
		bodyOyuncu3 = PhysicsFactory.createCircleBody(physicsWorld,spriteOyuncu3.getX()+64,spriteOyuncu3.getY()+64,47,0,BodyType.DynamicBody, fixDef);
		
		// Body ve Sprite nesneleri birbirlerine ba�lan�yor
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu1, bodyOyuncu1, true, true));
		
		this.physicsWorldd.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu2, bodyOyuncu2, true, true));

		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu3, bodyOyuncu3, true, true));
		
		// H�z verme i�lemleri yap�l�yor
		bodyOyuncu1.setLinearVelocity(-10, 5);
		bodyOyuncu2.setLinearVelocity(-10, -5);
		bodyOyuncu3.setLinearVelocity(-10, -5);
		
		// Sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		this.sahne.attachChild(spriteOyuncu3);
		
		this.sahne.registerUpdateHandler(physicsWorld);
		this.sahne.registerUpdateHandler(physicsWorldd);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}   
}
