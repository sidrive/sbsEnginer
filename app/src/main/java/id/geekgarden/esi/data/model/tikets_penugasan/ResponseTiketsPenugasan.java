package id.geekgarden.esi.data.model.tikets_penugasan;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseTiketsPenugasan{

	@SerializedName("tikets_dialihkan")
	private List<TiketsDialihkanItem> tiketsDialihkan;

	public void setTiketsDialihkan(List<TiketsDialihkanItem> tiketsDialihkan){
		this.tiketsDialihkan = tiketsDialihkan;
	}

	public List<TiketsDialihkanItem> getTiketsDialihkan(){
		return tiketsDialihkan;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTiketsPenugasan{" + 
			"tikets_dialihkan = '" + tiketsDialihkan + '\'' + 
			"}";
		}
}