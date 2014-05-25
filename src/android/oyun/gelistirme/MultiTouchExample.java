package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.widget.Toast;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class MultiTouchExample extends BaseGameActivity 
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
	Scene sahne;
	
	private Texture texSaha, texOyuncu1, texOyuncu2;
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2;
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2, spriteOyuncu3,
	spriteOyuncu4, spriteOyuncu5;
	
	private TimerHandler timerBeklet;
	private TextureRegion texRegOyuncu3;
	private TextureRegion texRegOyuncu4;
	private TextureRegion texRegOyuncu5;
    
	@Override
	public Engine onLoadEngine() 
	{
		// TODO Auto-generated method stub
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), camera);
        engineOptions.getTouchOptions().setRunOnUpdateThread(true);
        engine = new Engine(engineOptions);
        
        try
	    {
	    	if(MultiTouch.isSupported(this)) 
	    	{
	    		// Multi-Touch deste�i
	    		engine.setTouchController(new MultiTouchController());
	    	} 
	    	else 
	    	{
	    		Toast.makeText(this, "Android versiyonunuz Multi-Touch �zelli�ini desteklememektedir.", Toast.LENGTH_LONG).show();
	    	}
	    }
	    catch(final MultiTouchException e)
	    {
	    	Toast.makeText(this, "Android versiyonunuz Multi-Touch �zelli�ini desteklememektedir.", Toast.LENGTH_LONG).show();
	    }
        
		
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
		texRegOyuncu3 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu4 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		texRegOyuncu5 = TextureRegionFactory.createFromAsset(texOyuncu1, this, "gfx/kol1.png", 0, 0);
		
		
		Texture [] textures = {texSaha, texOyuncu1, texOyuncu2};
		
		// Texture nesneleri y�kleniyor.
		mEngine.getTextureManager().loadTextures(textures);
	}
	
	@Override
	public Scene onLoadScene() 
	{
		//this.engine.registerUpdateHandler(new FPSLogger());
		this.sahne = new Scene();
		
		// Sprite nesnesi olu�turuluyor
		spriteSaha = new Sprite(0,0,texRegSaha);
		
		
		
		// SpriteOyuncu1 olu�turuluyor. 
		spriteOyuncu1 = new Sprite(600, CAMERA_HEIGHT/2 - 64, texRegOyuncu1)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionMove())
				{
					spriteOyuncu1.setPosition(pSceneTouchEvent.getX() - spriteOyuncu1.getWidth()/2, pSceneTouchEvent.getY()- spriteOyuncu1.getHeight()/2);
				}
				return true;
			}
		};
		
		// SpriteOyuncu2 olu�turuluyor. ��eri�inde dokunma durumunu yakalayan kod blo�u bulunmakta.
		spriteOyuncu2 = new Sprite(50, CAMERA_HEIGHT/2 - 64, texRegOyuncu2)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionMove())
				{
					spriteOyuncu2.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY()- this.getHeight()/2);
				}
				return true;
			}
		};
		
		spriteOyuncu3 = new Sprite(180, CAMERA_HEIGHT/2 - 64, texRegOyuncu3)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionMove())
				{
					spriteOyuncu3.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY()- this.getHeight()/2);
				}
				return true;
			}
		};
		
		spriteOyuncu4 = new Sprite(300, CAMERA_HEIGHT/2 - 64, texRegOyuncu4)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionMove())
				{
					spriteOyuncu4.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY()- this.getHeight()/2);
				}
				return true;
			}
		};
		
		spriteOyuncu5 = new Sprite(450, CAMERA_HEIGHT/2 - 64, texRegOyuncu5)
		{
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY)
			{
				if(pSceneTouchEvent.isActionMove())
				{
					spriteOyuncu5.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, pSceneTouchEvent.getY()- this.getHeight()/2);
				}
				return true;
			}
		};
		
		// Sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahne.attachChild(spriteSaha);
		this.sahne.attachChild(spriteOyuncu1);
		this.sahne.attachChild(spriteOyuncu2);
		this.sahne.attachChild(spriteOyuncu3);
		this.sahne.attachChild(spriteOyuncu4);
		this.sahne.attachChild(spriteOyuncu5);
		
		// Nesnelerin dokunma alanlar� tan�mlan�yor
		this.sahne.registerTouchArea(spriteOyuncu1);
		this.sahne.registerTouchArea(spriteOyuncu2);
		this.sahne.registerTouchArea(spriteOyuncu3);
		this.sahne.registerTouchArea(spriteOyuncu4);
		this.sahne.registerTouchArea(spriteOyuncu5);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}   
}