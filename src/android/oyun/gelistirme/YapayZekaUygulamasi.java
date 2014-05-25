package android.oyun.gelistirme;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.ChangeableText;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.font.FontFactory;
import org.anddev.andengine.opengl.texture.BuildableTexture;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.graphics.Color;
import android.oyun.gelistirme.nesne.ClsNesne;
import android.oyun.gelistirme.nesne.Kutucuk;
import android.oyun.yapayzeka.YapayZeka;

/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class YapayZekaUygulamasi extends BaseGameActivity{

	private static final int CAMERA_WIDTH = 800;
    private static final int CAMERA_HEIGHT = 480;

    public static final int BERABERLIK = 0;
    public static final int INSAN = -1;
    public static final int YAPAY_ZEKA = 1;
    
    private Camera camera;
    private Engine engine;
	private Scene sahne;
	
	private ClsNesne arkaplan, xInsan, oYapayZeka;
	private Kutucuk [][]kutucuklar = new Kutucuk[3][3];
	
	public int siradaki = INSAN; // -1: insan 1: Yapay zeka
	
	Font font;
	BuildableTexture bTex;
	ChangeableText cTexKazanan;
	
    
	@Override
	public Engine onLoadEngine() 
	{
		// TODO Auto-generated method stub
		this.camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
        final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE, new FillResolutionPolicy(), camera);
        engineOptions.getTouchOptions().setRunOnUpdateThread(true);
        this.engine = new Engine(engineOptions);
		
		return this.engine;
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		Texture textureArkaPlan, textureOyuncuX, textureOyuncuO, textureKutucuk;
		textureArkaPlan = new Texture(1024, 512, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureOyuncuX = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureOyuncuO = new Texture(64, 64, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		textureKutucuk = new Texture(128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		
		this.arkaplan = new ClsNesne(textureArkaPlan, this, "gfx/arkaplanxox.jpg", 0, 0);
		this.xInsan = new ClsNesne(textureOyuncuX, this, "gfx/x.png", 0, 0);
		this.oYapayZeka = new ClsNesne(textureOyuncuO, this, "gfx/o.png", 0, 0);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				this.kutucuklar[i][j] = new Kutucuk(textureKutucuk, this, "gfx/kutucuk.png", 0, 0);
			}
		}

		if (this.bTex == null) {
			this.bTex = new BuildableTexture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		} else {
			this.bTex = null;
			this.bTex = new BuildableTexture(256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		}
		
		this.engine.getTextureManager().loadTextures(textureArkaPlan, textureOyuncuX, textureOyuncuO, textureKutucuk, this.bTex);
		
		this.font = FontFactory.createFromAsset(this.bTex, this, "gfx/CONTACT.ttf", 40, true, Color.BLACK);
		
		this.engine.getFontManager().loadFont(this.font);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.sahne = new Scene();
		// x ekseninde ortalamak için kutucukların başlangıç noktası
		int kutucukBaslangicNoktasiX = (800 -kutucuklar[0][0].oTextureWidth*3)/2; 
		// Y ekseninde ortalamak için kutucukların başlangıç noktası
		int kutucukBaslangicNoktasiY = (480 -kutucuklar[0][0].oTextureHeight*3)/2;	
		
		this.arkaplan.oSprite = new Sprite(0, 0, this.arkaplan.oTextureRegion);
		this.sahne.attachChild(this.arkaplan.oSprite);
		
		// Kazananın bildirileceği cTexKazanan nesnesi oluşturuluyor
		this.cTexKazanan = new ChangeableText(kutucukBaslangicNoktasiX, 0, this.font, "", "Yapay Zeka Kazandı".length());
		
		this.sahne.attachChild(this.cTexKazanan);
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				final int satir = i;
				final int sutun = j; 
				// Kutucuklar ekrana 3x3 şeklinde yerleştiriliyor
				this.kutucuklar[i][j].oSprite = new Sprite(kutucukBaslangicNoktasiX + j*this.kutucuklar[i][j].oTextureWidth, 
						kutucukBaslangicNoktasiY + i*this.kutucuklar[i][j].oTextureWidth, this.kutucuklar[i][j].oTextureRegion)
				{
					@Override
					public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float pTouchAreaLocalX, float pTouchAreaLocalY) {
						// TODO Auto-generated method stub
						// Eğer sıra insanda ise hamleYap metoduna git
						if(pSceneTouchEvent.isActionUp() && siradaki == INSAN)
						{
							// kutucuklar nesnesinin içine X resmini çizdir.
							this.attachChild(xInsan.oSprite);
							hamleYap(satir, sutun, INSAN);
							sahne.unregisterTouchArea(kutucuklar[satir][sutun].oSprite);
						}
						return true;
					}
				};
				this.sahne.attachChild(this.kutucuklar[i][j].oSprite);
				this.sahne.registerTouchArea(this.kutucuklar[i][j].oSprite);
			}
		}
		
		// kutucuklar nesnelerinin içine çizdirilecek olan X ve O resimleri
		this.xInsan.oSprite = new Sprite(32, 32, this.xInsan.oTextureRegion);
		this.oYapayZeka.oSprite = new Sprite(32, 32, this.oYapayZeka.oTextureRegion);
		
		return this.sahne;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
		
	}
	
	// Sırayı kontrol eder, iki oyuncu da hamlesini yapmak için bu metodu çağırır
	// Yapay zekada ise sıra, yapay zeka algoritmasını çalıştıracak olan 
	// enIyiHamleyiSec metodu çağırılır
	public void hamleYap(int satir, int sutun, int hamleSahibi)
	{
		this.kutucuklar[satir][sutun].hamleSahibi = hamleSahibi;
		if(!Kutucuk.bosKutucukVarMi(this.kutucuklar))
		{
			if(hamleSahibi == INSAN)
			{
				siradaki = YAPAY_ZEKA;
				YapayZeka yz = new YapayZeka(this.kutucuklar);
				int [] enIyiHamle = yz.enIyiHamleyiSec();
				
				hamleYap(enIyiHamle[0], enIyiHamle[1], YAPAY_ZEKA);
				this.kutucuklar[enIyiHamle[0]][enIyiHamle[1]].oSprite.attachChild(oYapayZeka.oSprite);
				this.sahne.unregisterTouchArea(this.kutucuklar[enIyiHamle[0]][enIyiHamle[1]].oSprite);
			}
			else
				this.siradaki = INSAN;
		}
		else
		{
			int sonuc = oyunBittiM(this.kutucuklar);
			if(sonuc == YAPAY_ZEKA)
			{
				cTexKazanan.setText("YAPAY ZEKA KAZANDI!");
			}
			else if(sonuc == INSAN)
			{
				cTexKazanan.setText("KAZANDINIZ!");
			}
			else
			{
				cTexKazanan.setText("BERABERE!");
			}
		}
		
	}
	// Oyunun bitip bitmediğini kontrol eden metot.
	// Bittiyse kazananı, kazanan yoksa beraberlik kodunu döndürür
	public static int oyunBittiM(Kutucuk [][] kutucuklar)
	{
		int skor;

		for(int i = 0; i < 3; i++)
		{
			skor = kazananiBelirle(
					kutucuklar[0][i].hamleSahibi, 
					kutucuklar[1][i].hamleSahibi, 
					kutucuklar[2][i].hamleSahibi);
			if(skor != 0)
			{
				return skor;
			}
			
			skor = 	kazananiBelirle(
					kutucuklar[i][0].hamleSahibi,
					kutucuklar[i][1].hamleSahibi, 
					kutucuklar[i][2].hamleSahibi);
			if(skor != 0)
			{
				return skor;
			}
		}

		skor = 	kazananiBelirle(
				kutucuklar[0][0].hamleSahibi,
				kutucuklar[1][1].hamleSahibi,
				kutucuklar[2][2].hamleSahibi);
		if(skor != 0)
		{
			return skor;
		}
		return 	kazananiBelirle(
				kutucuklar[0][2].hamleSahibi,
				kutucuklar[1][1].hamleSahibi,
				kutucuklar[2][0].hamleSahibi);
	}
	
	public static int kazananiBelirle(int a, int b, int c)
	{
		if ((a == YAPAY_ZEKA) && (b == YAPAY_ZEKA) && (c == YAPAY_ZEKA)) { 
			return YAPAY_ZEKA; 
			}  
	    if ((a == INSAN) && (b == INSAN) && (c == INSAN)) { 
	    	return INSAN; 
	    	}  
	    return BERABERLIK; 
	}
}
