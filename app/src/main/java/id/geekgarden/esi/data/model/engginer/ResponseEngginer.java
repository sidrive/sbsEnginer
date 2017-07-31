package id.geekgarden.esi.data.model.engginer;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseEngginer{

	@SerializedName("engginer")
	private List<EngginerItem> engginer;

	public void setEngginer(List<EngginerItem> engginer){
		this.engginer = engginer;
	}

	public List<EngginerItem> getEngginer(){
		return engginer;
	}

	@Override
 	public String toString(){
		return 
			"ResponseEngginer{" + 
			"engginer = '" + engginer + '\'' + 
			"}";
		}
}