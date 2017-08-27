package id.geekgarden.esi.data.model.sn_alat;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class SnAlatItem{

	@SerializedName("id")
	private int id;

	@SerializedName("sn")
	private String sn;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setSn(String sn){
		this.sn = sn;
	}

	public String getSn(){
		return sn;
	}

	@Override
 	public String toString(){
		return 
			"SnAlatItem{" + 
			"id = '" + id + '\'' + 
			",sn = '" + sn + '\'' + 
			"}";
		}
}