package android.oyun.gelistirme.nesne;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;
import android.oyun.gelistirme.YapayZekaUygulamasi;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class Kutucuk{
	
	public int oTextureWidth, oTextureHeight;
	public TextureRegion oTextureRegion;
	public Sprite oSprite;
	// hamleSahibi değişkeni her bir kutuda bulunmalı ve
	// Her bir kutunun durumunu tutmalı
	public int hamleSahibi = YapayZekaUygulamasi.BERABERLIK; // Varsayılan olarak kutucuklar boş olmalı

	public Kutucuk() {
		
	}
	public Kutucuk(Texture oTexture, Context oContext, String oAssetPath, int oTexturePositionX, int oTexturePositionY)
	{
		oTextureWidth = oTexture.getWidth();
		oTextureHeight = oTexture.getHeight();
		oTextureRegion = TextureRegionFactory.createFromAsset(oTexture, oContext, oAssetPath, oTexturePositionX, oTexturePositionY);
	}
	
	//Kutucuklarda boş yer kalıp kalmadığını döndüren metot 
	public static boolean bosKutucukVarMi(Kutucuk [][] kutucuk)
	{
		if(YapayZekaUygulamasi.oyunBittiM(kutucuk) != 0)
			return true;
		
		for(int i = 0; i < 3; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				if(kutucuk[i][j].hamleSahibi == YapayZekaUygulamasi.BERABERLIK)
					return false;
			}
		}
		return true;
	}
}
