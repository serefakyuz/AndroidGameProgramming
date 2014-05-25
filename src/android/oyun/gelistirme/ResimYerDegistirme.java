package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class ResimYerDegistirme extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
	Scene sahne;
	
	private Texture texSaha, texOyuncu1, texOyuncu2 ;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	
	private TimerHandler timerBeklet;
    
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
		
		// Texture nesneleri olu�turuluyor
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
		this.sahne = new Scene();
		
		// Sprite nesnesi olu�turuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		spriteOyuncu1 = new Sprite(600, CAMERA_HEIGHT/2 - 64, texRegOyuncu1);
		spriteOyuncu2 = new Sprite(50, CAMERA_HEIGHT/2 - 64, texRegOyuncu2);
		
		mEngine.registerUpdateHandler(timerBeklet = new TimerHandler(3, false, new ITimerCallback() 
		{
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{
				spriteOyuncu1.setPosition(50, CAMERA_HEIGHT/2 - 64);
				spriteOyuncu2.setPosition(600, CAMERA_HEIGHT/2 - 64);				
			}
		}));
			
		// Sprite nesneleri ekrana(sahne) �izdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}   
}
