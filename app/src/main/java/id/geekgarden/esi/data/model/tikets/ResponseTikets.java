package id.geekgarden.esi.data.model.tikets;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseTikets{

	@SerializedName("tikets")
	private List<TiketsItem> tikets;

	public void setTikets(List<TiketsItem> tikets){
		this.tikets = tikets;
	}

	public List<TiketsItem> getTikets(){
		return tikets;
	}

	@Override
 	public String toString(){
		return 
			"ResponseTikets{" + 
			"tikets = '" + tikets + '\'' + 
			"}";
		}
}