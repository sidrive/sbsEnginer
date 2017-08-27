package id.geekgarden.esi.data.model.sn_alat;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ResponseSnAlat{

	@SerializedName("sn_alat")
	private List<SnAlatItem> snAlat;

	public void setSnAlat(List<SnAlatItem> snAlat){
		this.snAlat = snAlat;
	}

	public List<SnAlatItem> getSnAlat(){
		return snAlat;
	}

	@Override
 	public String toString(){
		return 
			"ResponseSnAlat{" + 
			"sn_alat = '" + snAlat + '\'' + 
			"}";
		}
}