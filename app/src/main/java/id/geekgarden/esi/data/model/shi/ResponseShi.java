package id.geekgarden.esi.data.model.shi;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseShi{

	@SerializedName("shi")
	private List<ShiItem> shi;

	public void setShi(List<ShiItem> shi){
		this.shi = shi;
	}

	public List<ShiItem> getShi(){
		return shi;
	}

	@Override
 	public String toString(){
		return 
			"ResponseShi{" + 
			"shi = '" + shi + '\'' + 
			"}";
		}
}