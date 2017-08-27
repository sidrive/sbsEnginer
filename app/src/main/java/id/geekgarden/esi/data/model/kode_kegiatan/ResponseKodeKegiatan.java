package id.geekgarden.esi.data.model.kode_kegiatan;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseKodeKegiatan{

	@SerializedName("kode_kegiatan")
	private List<KodeKegiatanItem> kodeKegiatan;

	public void setKodeKegiatan(List<KodeKegiatanItem> kodeKegiatan){
		this.kodeKegiatan = kodeKegiatan;
	}

	public List<KodeKegiatanItem> getKodeKegiatan(){
		return kodeKegiatan;
	}

	@Override
 	public String toString(){
		return 
			"ResponseKodeKegiatan{" + 
			"kode_kegiatan = '" + kodeKegiatan + '\'' + 
			"}";
		}
}