package android.oyun.yapayzeka;

import android.oyun.gelistirme.YapayZekaUygulamasi;
import android.oyun.gelistirme.nesne.Kutucuk;
/**
 * 
 * @author SEREFAKYUZ
 *
 */
public class YapayZeka {
	Kutucuk [][] kutucuklar;
	public YapayZeka(Kutucuk [][] kutucuklar)
	{
		this.kutucuklar = kutucuklar;
	}
	
	// En iyi hamleyi seçmek için algoritmayı uygulamaya başla
	public  int [] enIyiHamleyiSec()
	{
		int skor;
		int enIyiSkor = -2;
		int enIyiSatir = -1;
		int enIyiSutun = -1;
		 for (int i = 0; i < 3; i++) {  
		      for (int j = 0; j < 3; j++) {  
		        if (kutucuklar[i][j].hamleSahibi == YapayZekaUygulamasi.BERABERLIK) {  
		        	kutucuklar[i][j].hamleSahibi = YapayZekaUygulamasi.YAPAY_ZEKA;  
		        	skor = miniMaxInsan();  
		          if (skor > enIyiSkor) {  
		        	  enIyiSkor = skor;  
		        	  enIyiSatir = i;  
		        	  enIyiSutun = j;  
		          }  
		          kutucuklar[i][j].hamleSahibi = YapayZekaUygulamasi.BERABERLIK;  
		        }  
		      }  
		    }  
		 int [] enIyiHamle = {enIyiSatir, enIyiSutun};
		 return enIyiHamle;
	}
	
	// Insan için hamleler oluşturuluyor
	private int miniMaxInsan() {
		int skor = YapayZekaUygulamasi.oyunBittiM(kutucuklar);
		if (Kutucuk.bosKutucukVarMi(kutucuklar)) {
			return skor;
		}
		int enIyiSkor = 2;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (kutucuklar[i][j].hamleSahibi == YapayZekaUygulamasi.BERABERLIK) {
					kutucuklar[i][j].hamleSahibi = YapayZekaUygulamasi.INSAN;
					skor = miniMaxYapayZeka();
					if (skor < enIyiSkor) {
						enIyiSkor = skor;
					}
					kutucuklar[i][j].hamleSahibi = YapayZekaUygulamasi.BERABERLIK;
				}
			}
		}
		return enIyiSkor;
	}
	
	// Yapay zeka için hamleler oluşturuluyor
	private int miniMaxYapayZeka() {
		int skor = YapayZekaUygulamasi.oyunBittiM(kutucuklar);
		if (Kutucuk.bosKutucukVarMi(kutucuklar)) {
			return skor;
		}
		int enIyiSkor = -2;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (kutucuklar[i][j].hamleSahibi == YapayZekaUygulamasi.BERABERLIK) {
					kutucuklar[i][j].hamleSahibi = YapayZekaUygulamasi.YAPAY_ZEKA;
					skor = miniMaxInsan();
					if (skor > enIyiSkor) {
						enIyiSkor = skor;
					}
					kutucuklar[i][j].hamleSahibi = YapayZekaUygulamasi.BERABERLIK;
				}
			}
		}
		return enIyiSkor;
	}

}
