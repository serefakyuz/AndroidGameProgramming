package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.Scene.IOnSceneTouchListener;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.physics.box2d.PhysicsConnector;
import org.anddev.andengine.extension.physics.box2d.PhysicsFactory;
import org.anddev.andengine.extension.physics.box2d.PhysicsWorld;
import org.anddev.andengine.extension.physics.box2d.util.constants.PhysicsConstants;
import org.anddev.andengine.input.touch.TouchEvent;
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
public class FizikHizVerme extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
    private PhysicsWorld physicsWorld = new PhysicsWorld(new Vector2(0, SensorManager.GRAVITY_DEATH_STAR_I), false);
    private FixtureDef fixDef = PhysicsFactory.createFixtureDef(0.5f, 0.5f, 0.5f);
	Scene sahne;
	
	private Texture texSaha, texOyuncu1, texOyuncu2;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	private Body bodyOyuncu1, bodyOyuncu2;
    
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
		// Texture nesneleri oluşturuluyor
		texSaha = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		texOyuncu1 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texOyuncu2 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		// TextureRegion nesneleri olu�turuluyor
		texRegSaha = TextureRegionFactory.createFromAsset(texSaha, this, "gfx/Arkaplan.jpg", 0, 0);
		texRegOyuncu1 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu2 = TextureRegionFactory.createFromAsset(texOyuncu2, this, "gfx/kol2.png", 0, 0);
		
		
		Texture [] textures = {texSaha, texOyuncu1, texOyuncu2};
		
		// Texture nesneleri y�kleniyor.
		mEngine.getTextureManager().loadTextures(textures);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		this.engine.registerUpdateHandler(new FPSLogger());
		sahne = new Scene();
		// Sprite nesnesi olu�turuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		
		sahne.setOnSceneTouchListener(new IOnSceneTouchListener() {
			
			@Override
			public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
			if(pSceneTouchEvent.isActionDown())
			{
				bodyOyuncu1.setLinearVelocity((pSceneTouchEvent.getX() - spriteOyuncu1.getX() + spriteOyuncu1.getWidth()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
						(pSceneTouchEvent.getY() - spriteOyuncu1.getY() + spriteOyuncu1.getHeight()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			}
			if(pSceneTouchEvent.isActionMove())
			{
				bodyOyuncu1.setLinearVelocity(pSceneTouchEvent.getX()/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT - (spriteOyuncu1.getX() + spriteOyuncu1.getWidth()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 
						pSceneTouchEvent.getY()/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT - (spriteOyuncu1.getY() + spriteOyuncu1.getHeight()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT);
			}
				return false;
			}
		});
		// SpriteOyuncu1 olu�turuluyor. 
		spriteOyuncu1 = new Sprite(600, CAMERA_HEIGHT/2 - 128, texRegOyuncu1)
		{
			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
//				if(pSceneTouchEvent.isActionDown())
//				{
//					bodyOyuncu1.setTransform((pSceneTouchEvent.getX() - spriteOyuncu1.getWidth()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT ,
//							(pSceneTouchEvent.getY() - spriteOyuncu1.getHeight()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 0);
//				}
//				if(pSceneTouchEvent.isActionMove())
//				{
//					bodyOyuncu1.setTransform((pSceneTouchEvent.getX() - spriteOyuncu1.getWidth()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT ,
//							(pSceneTouchEvent.getY() - spriteOyuncu1.getHeight()/2)/PhysicsConstants.PIXEL_TO_METER_RATIO_DEFAULT, 0);
//				}
				return super.onAreaTouched(pSceneTouchEvent, pTouchAreaLocalX, pTouchAreaLocalY);
			}
		};
		
		
		// SpriteOyuncu2 olu�turuluyor.
		spriteOyuncu2 = new Sprite(50, CAMERA_HEIGHT/2, texRegOyuncu2);
		
		// Body nesneleri olu�turuluyor
		bodyOyuncu1 = PhysicsFactory.createCircleBody(physicsWorld, spriteOyuncu1, BodyType.DynamicBody, fixDef);
		bodyOyuncu2 = PhysicsFactory.createCircleBody(physicsWorld, spriteOyuncu2, BodyType.DynamicBody, fixDef);
		
		// Body ve Sprite nesneleri birbirlerine ba�lan�yor
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu1, bodyOyuncu1, true, true));
		this.physicsWorld.registerPhysicsConnector(new PhysicsConnector(spriteOyuncu2, bodyOyuncu2, true, true));
		
		// H�z verme i�lemleri yap�l�yor
//		bodyOyuncu1.setLinearVelocity(-10, 0);
//		bodyOyuncu2.setLinearVelocity(new Vector2(10, 0));
		
		// Sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		
		// Sprite nesnelerinin dokunma alanlar� tan�mlan�yor.
		this.sahne.registerTouchArea(spriteOyuncu1);
		this.sahne.registerTouchArea(spriteOyuncu2);
		
		this.sahne.registerUpdateHandler(physicsWorld);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		System.out.print(true);
	}   
}
