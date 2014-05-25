package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnectorManager;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
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
public class AnimasyonOlusturma extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
	Scene sahne;
    private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
    private FixtureDef fixDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
	
	private Texture texAnime;
	private TiledTextureRegion tiledTexReg;
	private AnimatedSprite animSprite;
    
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
	public void onLoadResources() {
		// TODO Auto-generated method stub
		
		// Sahan�n Texture nesnesi olu�turuluyor
		texAnime = new Texture(1024, 8, TextureOptions.BILINEAR_PREMULTIPLYALPHA);		
		
		// Sahan�n TextureRegion nesnesi olu�turuluyor
		tiledTexReg = TextureRegionFactory.createTiledFromAsset(texAnime, this, "gfx/wave256.png", 0, 0, 4, 1);
		
		// Texture nesnesi motora y�kleniyor.
		mEngine.getTextureManager().loadTexture(texAnime);
	}
	
	Body body;
	
	@Override
	public Scene onLoadScene() 
	{
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		
		// Sprite nesnesi olu�turuluyor
		animSprite = new AnimatedSprite(0,0,tiledTexReg);
		
		body = PhysicsFactory.createCircleBody(physicsWorld, animSprite, BodyType.DynamicBody, fixDef);
		
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(animSprite, body));
		
		animSprite.animate(500);
		//body.setLinearVelocity(1,1);
			
		// Sprite nesnesi ekrana(sahne) cizdiriliyor.
		this.sahne.attachChild(animSprite);
		
		mEngine.registerUpdateHandler(physicsWorld);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() 
	{
		// TODO Auto-generated method stub
	}   
}