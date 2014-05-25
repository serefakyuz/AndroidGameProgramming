package android.oyun.gelistirme.nesne;

import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;

import android.content.Context;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class ClsNesne{
	
	public int oTextureWidth, oTextureHeight;
	public TextureRegion oTextureRegion;
	public Sprite oSprite;
	
	public ClsNesne(Texture oTexture, Context oContext, String oAssetPath, int oTexturePositionX, int oTexturePositionY)
	{
		oTextureWidth = oTexture.getWidth();
		oTextureHeight = oTexture.getHeight();
		oTextureRegion = TextureRegionFactory.createFromAsset(oTexture, oContext, oAssetPath, oTexturePositionX, oTexturePositionY);
	}

	public ClsNesne() {
		
	}
}
