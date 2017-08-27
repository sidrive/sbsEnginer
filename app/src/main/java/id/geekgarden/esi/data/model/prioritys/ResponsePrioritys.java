package id.geekgarden.esi.data.model.prioritys;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponsePrioritys{

	@SerializedName("prioritys")
	private List<PrioritysItem> prioritys;

	public void setPrioritys(List<PrioritysItem> prioritys){
		this.prioritys = prioritys;
	}

	public List<PrioritysItem> getPrioritys(){
		return prioritys;
	}

	@Override
 	public String toString(){
		return 
			"ResponsePrioritys{" + 
			"prioritys = '" + prioritys + '\'' + 
			"}";
		}
}