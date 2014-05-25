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

import android.view.KeyEvent;
/**
 * 
 * @author SEREFAKYUZ
 *
 */

public class MenuOlusturmaveFizikselTusKullanimi extends BaseGameActivity
{
	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;
    private Camera camera;
    private Engine engine;
	
	// Texture nesneleri tan�mlan�yor
	private Texture texSaha, texOyuncu1, texOyuncu2, texMenuArka, texMenuOyna,
					texMenuOynaHover, texMenuCikis, texMenuCikisHover;
	private Texture texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
					texSahaSolAlt, texSahaSolUst;
	
	// TextureRegion nesneleri tan�mlan�yor
	private TextureRegion texRegSaha, texRegOyuncu1, texRegOyuncu2, texRegMenuArka,
					texRegMenuOyna, texRegMenuOynaHover, texRegMenuCikis, texRegMenuCikisHover;
	private TextureRegion texRegSahaUst, texRegSahaAlt, texRegSahaSagAlt, 
					texRegSahaSagUst, texRegSahaSolAlt, texRegSahaSolUst;
	
	// Sprite nesneleri tan�mlan�yor
	private Sprite spriteSaha, spriteOyuncu1, spriteOyuncu2, spriteMenuArka, 
					spriteMenuOyna, spriteMenuOynaHover, spriteMenuCikis, spriteMenuCikisHover;
	private Sprite spriteSahaUst, spriteSahaAlt, spriteSahaSagAlt, 
					spriteSahaSagUst, spriteSahaSolAlt, spriteSahaSolUst;
	
	
	@Override
	public Engine onLoadEngine() 
	{
		// TODO Auto-generated method stub
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), camera).setNeedsMusic(true);
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
		
		// ************************************* //
		// sahneMenu nesnesine eklenecek resimler
		// i�in Texture nesneleri olu�turuluyor
		texMenuArka = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texMenuOyna = new Texture(64, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texMenuOynaHover = new Texture(64, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texMenuCikis = new Texture(64, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		texMenuCikisHover = new Texture(64, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
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
		
		// ************************************* //
		// sahneMenu nesnesine eklenecek resimler
		// i�in TextureRegion nesneleri olu�turuluyor
		texRegMenuArka = TextureRegionFactory.createFromAsset(texMenuArka, this, "gfx/back.jpg", 0, 0);	
		texRegMenuOyna = TextureRegionFactory.createFromAsset(texMenuOyna, this, "gfx/bt_play.png", 0, 0);
		texRegMenuOynaHover = TextureRegionFactory.createFromAsset(texMenuOynaHover, this, "gfx/bt_play_hover.png", 0, 0);		
		texRegMenuCikis = TextureRegionFactory.createFromAsset(texMenuCikis, this, "gfx/bt_quit.png", 0, 0);		
		texRegMenuCikisHover = TextureRegionFactory.createFromAsset(texMenuCikisHover, this, "gfx/bt_quit_hover.png", 0, 0);

		// Sahay� kaplayan textureRegion nesneleri olu�turuluyor
		texRegSahaAlt = TextureRegionFactory.createFromAsset(texSahaAlt, this, "gfx/duvar.png", 0, 0);
		texRegSahaUst = TextureRegionFactory.createFromAsset(texSahaUst, this, "gfx/duvar.png", 0, 0);
		texRegSahaSagAlt = TextureRegionFactory.createFromAsset(texSahaSagAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolAlt = TextureRegionFactory.createFromAsset(texSahaSolAlt, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSagUst = TextureRegionFactory.createFromAsset(texSahaSagUst, this, "gfx/kaleduvari.png", 0, 0);
		texRegSahaSolUst = TextureRegionFactory.createFromAsset(texSahaSolUst, this, "gfx/kaleduvari.png", 0, 0);
		
		// ************************************* //
		// Yeni texture nesneleri de diziye eklendi
		Texture [] textures = {texMenuArka, texMenuOyna, texMenuOynaHover, texMenuCikis, texMenuCikisHover,
				texSaha, texOyuncu1, texOyuncu2, texSahaUst, texSahaAlt, texSahaSagAlt, texSahaSagUst, 
				texSahaSolAlt, texSahaSolUst};
		
		// Texture nesneleri y�kleniyor.
		mEngine.getTextureManager().loadTextures(textures);	
	}
	
	Scene sahneOyun, sahneMenu;
	
	@Override
	public Scene onLoadScene() 
	{
		// TODO Auto-generated method stub		
		this.engine.registerUpdateHandler(new FPSLogger());
		this.sahneOyun = new Scene();
		
		// ***********************************//
		//sahneMenu nesnesi olu�turuluyor
		this.sahneMenu = new Scene();
		
		menuNesneleriniOlustur();
		oyunNesneleriniOlustur();
		
		return this.sahneMenu;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}

	// sprite nesnelerinin olu�turulmas� ve 
	// sahneMenu nesnesi �zerine
	// �izdirilme i�lemleri bu metotta yap�lmaktad�r
	private void menuNesneleriniOlustur()
	{
		spriteMenuArka = new Sprite(0,0, texRegMenuArka);
		
		//Hover ve As�l nesne ayn� koordinatlarda olu�turuluyor
		spriteMenuOynaHover = new Sprite(312, 107, texRegMenuOynaHover);
		spriteMenuOyna = new Sprite(312, 107, texRegMenuOyna)
		{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
            		float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				spriteMenuOyna.setVisible(false);
    				spriteMenuOynaHover.setVisible(true);
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				engine.setScene(sahneOyun);
    			}
                return true;
            }
		};
		
		//Hover ve as�l nesne ayn� koordinatlarda olu�turuluyor
		spriteMenuCikisHover = new Sprite(685, 60, texRegMenuCikisHover);
		spriteMenuCikis = new Sprite(685, 60, texRegMenuCikis)
		{
			@Override
            public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
                            float pTouchAreaLocalX, float pTouchAreaLocalY) 
			{
    			if(pSceneTouchEvent.isActionDown())
    			{
    				spriteMenuCikis.setVisible(false);
    				spriteMenuCikisHover.setVisible(true);
    			}
    			if(pSceneTouchEvent.isActionUp())
    			{   
    				finish();
    	            System.exit(0);
    			}
                return true;
            }
		};
		
		// Hover nesnelerinin g�r�n�rl�k �zelli�i false yap�l�yor
		spriteMenuOynaHover.setVisible(false);
		spriteMenuCikisHover.setVisible(false);
		
		// sahneMenu nesneleri sahneye �izdiriliyor
		this.sahneMenu.attachChild(spriteMenuArka);
		this.sahneMenu.attachChild(spriteMenuOyna);
		this.sahneMenu.attachChild(spriteMenuOynaHover);
		this.sahneMenu.attachChild(spriteMenuCikis);
		this.sahneMenu.attachChild(spriteMenuCikisHover);
		
		// sahneMenu �zerindeki butonlar�n RegisterArea
		// �zellikleri tan�mlan�yor 
		// (Hover nesnelerin dokunma  �zellikleri hari�)
		this.sahneMenu.registerTouchArea(spriteMenuOyna);
		this.sahneMenu.registerTouchArea(spriteMenuCikis);
	}
	
	// sprite nesnelerinin olu�turulmas� ve 
	// sahneOyun nesnesi �zerine
	// �izdirilme i�lemleri bu metotta yap�lmaktad�r
	private void oyunNesneleriniOlustur()
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
		spriteOyuncu1 = new Sprite(600, CAMERA_HEIGHT/2 - 64, texRegOyuncu1);
		
		// SpriteOyuncu2 olu�turuluyor.
		spriteOyuncu2 = new Sprite(50, CAMERA_HEIGHT/2 - 64, texRegOyuncu2);
		
		// Sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahneOyun.attachChild(spriteSaha);
		this.sahneOyun.attachChild(spriteOyuncu1);
		this.sahneOyun.attachChild(spriteOyuncu2);
		
		// Saha kenarlar�n�n sprite nesneleri ekrana(sahneye) �izdiriliyor.
		this.sahneOyun.attachChild(spriteSahaUst);
		this.sahneOyun.attachChild(spriteSahaAlt);
		this.sahneOyun.attachChild(spriteSahaSagUst);
		this.sahneOyun.attachChild(spriteSahaSagAlt);
		this.sahneOyun.attachChild(spriteSahaSolUst);
		this.sahneOyun.attachChild(spriteSahaSolAlt);
		
		// Sprite nesnelerinin dokunma alanlar� tan�mlan�yor.
		this.sahneOyun.registerTouchArea(spriteOyuncu1);
		this.sahneOyun.registerTouchArea(spriteOyuncu2);
	}
	
	// Fiziksel tu�lar�n kullan�m�na olanak veren onKeyDown Metodu
	@Override
	public boolean onKeyDown(final int pKeyCode, final KeyEvent pEvent)
	{
		// Geri Tu�una bas�ld���nda yap�lacaklar
		if(pKeyCode == KeyEvent.KEYCODE_BACK && pEvent.getAction() == KeyEvent.ACTION_DOWN) 
		{
            System.exit(0);
            
			return true;
		}
		// Menu tu�una bas�ld���nda yap�lacaklar
		else if(pKeyCode == KeyEvent.KEYCODE_MENU && pEvent.getAction() == KeyEvent.ACTION_DOWN) 
		{		
			spriteMenuOyna.setVisible(true);
			spriteMenuOynaHover.setVisible(false);
			spriteMenuCikis.setVisible(true);
			spriteMenuCikisHover.setVisible(false);
			engine.setScene(sahneMenu);
			return true;
		}
		else 
		{
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}

}
