package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.IUpdateHandler;
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
public class ResimTemasAniYakalama extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
	Scene sahne;
	
	private Texture texSaha, texOyuncu1, texOyuncu2 ;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
	
	private int ilkKonumOyuncu1X = 100;
	private int ilkKonumOyuncu2X = 600;
	private int konumY = 200;
	
	private TimerHandler timer;
    
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
		
		// Texture nesneleri oluşturuluyor
		texSaha = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);	
		texOyuncu1 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texOyuncu2 = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		// TextureRegion nesneleri oluşturuluyor
		texRegSaha = TextureRegionFactory.createFromAsset(texSaha, this, "gfx/Arkaplan.jpg", 0, 0);
		texRegOyuncu1 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu2 = TextureRegionFactory.createFromAsset(texOyuncu2, this, "gfx/kol2.png", 0, 0);
		
		Texture [] textures = {texSaha, texOyuncu1, texOyuncu2};
		
		// Texture nesneleri yükleniyor.
		mEngine.getTextureManager().loadTextures(textures);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		
		this.sahne.registerUpdateHandler(new IUpdateHandler() {
			@Override
			public void reset() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				
				//setPosition metodu ile timer saniyelerini baz alarak 
				//yer değiştirme işlemi gerçekleştiriliyor
				spriteOyuncu1.setPosition(ilkKonumOyuncu1X + timer.getTimerSecondsElapsed()*200, konumY);
				spriteOyuncu2.setPosition(ilkKonumOyuncu2X - timer.getTimerSecondsElapsed()*200, konumY);
				// Temas anını kontrol eden collidesWith metodu
				if(spriteOyuncu1.collidesWith(spriteOyuncu2))
				{
					// Eğer sprite nesneleri temas ettiler ise başlangıç pozisyonuna gönder.
					spriteOyuncu1.setPosition(ilkKonumOyuncu1X, konumY);
					spriteOyuncu2.setPosition(ilkKonumOyuncu2X, konumY);
					// timerBeklet işlevini durdur.
					mEngine.unregisterUpdateHandler(timer);
				}
			}
		});
		
		// Sprite nesnesi oluşturuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		spriteOyuncu1 = new Sprite(ilkKonumOyuncu1X, konumY, texRegOyuncu1);
		spriteOyuncu2 = new Sprite(ilkKonumOyuncu2X, konumY, texRegOyuncu2);
		
		mEngine.registerUpdateHandler(timer = new TimerHandler(10, false, new ITimerCallback() 
		{
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) 
			{	
				// Burada timer süresi dolduğunda yapılacak işlemler yer alır.
			}
		}));
			
		// Sprite nesneleri ekrana(sahne) çizdiriliyor.
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
