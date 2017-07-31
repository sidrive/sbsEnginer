package id.geekgarden.esi.data.model.projects;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ListKegiatanItem{

	@SerializedName("kegiatan")
	private String kegiatan;

	@SerializedName("id")
	private int id;

	public void setKegiatan(String kegiatan){
		this.kegiatan = kegiatan;
	}

	public String getKegiatan(){
		return kegiatan;
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
			"ListKegiatanItem{" + 
			"kegiatan = '" + kegiatan + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}