package id.geekgarden.esi.data.model.shi;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ShiItem{

	@SerializedName("shi")
	private String shi;

	@SerializedName("id")
	private int id;

	public void setShi(String shi){
		this.shi = shi;
	}

	public String getShi(){
		return shi;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"ShiItem{" + 
			"shi = '" + shi + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}