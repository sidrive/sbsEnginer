package id.geekgarden.esi.data.model.prioritys;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PrioritysItem{

	@SerializedName("id")
	private int id;

	@SerializedName("priority")
	private String priority;

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setPriority(String priority){
		this.priority = priority;
	}

	public String getPriority(){
		return priority;
	}

	@Override
 	public String toString(){
		return 
			"PrioritysItem{" + 
			"id = '" + id + '\'' + 
			",priority = '" + priority + '\'' + 
			"}";
		}
}