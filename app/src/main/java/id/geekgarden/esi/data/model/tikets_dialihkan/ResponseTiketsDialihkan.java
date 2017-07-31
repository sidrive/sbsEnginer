package id.geekgarden.esi.data.model.tikets_dialihkan;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseTiketsDialihkan{

	@SerializedName("tikets_penugasan")
	private List<TiketsPenugasanItem> tiketsPenugasan;

	public void setTiketsPenugasan(List<TiketsPenugasanItem> tiketsPenugasan){
		this.tiketsPenugasan = tiketsPenugasan;
	}

	public List<TiketsPenugasanItem> getTiketsPenugasan(){
		return tiketsPenugasan;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTiketsDialihkan{" + 
			"tikets_penugasan = '" + tiketsPenugasan + '\'' + 
			"}";
		}
}