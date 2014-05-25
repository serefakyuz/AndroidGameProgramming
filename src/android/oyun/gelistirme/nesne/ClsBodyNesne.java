package android.oyun.gelistirme.nesne;

import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;

import android.content.Context;

import com.badlogic.gdx.physics.box2d.Body;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class ClsBodyNesne extends ClsNesne {

	public Body oBody;
	
	public ClsBodyNesne()
	{
		super();
	}
	
	public ClsBodyNesne(Texture oTexture, Context oContext,String oAssetPath, int oTexturePositionX, int oTexturePositionY) 
	{
		super(oTexture, oContext, oAssetPath,oTexturePositionX, oTexturePositionY);
	}
}
