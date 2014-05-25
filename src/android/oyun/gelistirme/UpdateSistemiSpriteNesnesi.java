package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
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
public class UpdateSistemiSpriteNesnesi extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
	Scene sahne;
	
	private Texture texSaha, texOyuncu1, texOyuncu2;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2;
    
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
	
	private float parmakKoordinatiX = 0;
	private float parmakKoordinatiY = 0;
	private boolean maviHareketEder = false;
	@Override
	public Scene onLoadScene() 
	{
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		
		// Sprite nesnesi olu�turuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		
		// SpriteOyuncu1 olu�turuluyor. 
		spriteOyuncu1 = new Sprite(600, CAMERA_HEIGHT/2 - 64, texRegOyuncu1)
		{
			protected void onManagedUpdate(float pSecondsElapsed) 
			{
				if(!maviHareketEder)
				{
					spriteOyuncu2.setPosition(parmakKoordinatiX, parmakKoordinatiY);
				}
				else
				{
					spriteOyuncu1.setPosition(parmakKoordinatiX, parmakKoordinatiY);
				}
			}
		};
//			
//			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
//			{
//				switch(pSceneTouchEvent.getAction())
//				{
//					case TouchEvent.ACTION_DOWN:
//						if(pSceneTouchEvent.getX() < CAMERA_WIDTH/2 - 64)
//						{
//							maviHareketEder = false;
//							parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
//							parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
//						}
//						else if(pSceneTouchEvent.getX() > CAMERA_WIDTH/2 + 64)
//						{
//							maviHareketEder = true;
//							parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
//							parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
//						}
//					break;
//					case TouchEvent.ACTION_MOVE:
//						if(pSceneTouchEvent.getX() < CAMERA_WIDTH/2 - 64)
//						{
//							maviHareketEder = false;
//							parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
//							parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
//						}
//						else if(pSceneTouchEvent.getX() > CAMERA_WIDTH/2 + 64)
//						{
//							maviHareketEder = true;
//							parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
//							parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
//						}
//					break;
//				}
//				return true;
//			}
//		};
		
		// SpriteOyuncu2 olu�turuluyor. ��eri�inde dokunma durumunu yakalayan kod blo�u bulunmakta.
		spriteOyuncu2 = new Sprite(50, CAMERA_HEIGHT/2 - 64, texRegOyuncu2)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				switch(pSceneTouchEvent.getAction())
				{
				case TouchEvent.ACTION_DOWN:
					if(pSceneTouchEvent.getX() < CAMERA_WIDTH/2 - 64)
					{
						maviHareketEder = false;
						parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
						parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
					}
					else if(pSceneTouchEvent.getX() > CAMERA_WIDTH/2 + 64)
					{
						maviHareketEder = true;
						parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
						parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
					}
					break;
				case TouchEvent.ACTION_MOVE:
					if(pSceneTouchEvent.getX() < CAMERA_WIDTH/2 - 64)
					{
						maviHareketEder = false;
						parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
						parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
					}
					else if(pSceneTouchEvent.getX() > CAMERA_WIDTH/2 + 64)
					{
						maviHareketEder = true;
						parmakKoordinatiX = pSceneTouchEvent.getX() - 64;
						parmakKoordinatiY = pSceneTouchEvent.getY() - 64;
					}
					break;
				}
				return true;
			}
		};
		// Sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		
		// Sprite nesnelerinin dokunma alanlar� tan�mlan�yor.
		this.sahne.registerTouchArea(spriteOyuncu1);
		this.sahne.registerTouchArea(spriteOyuncu2);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}   
}
