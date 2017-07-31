package id.geekgarden.esi.data.model.projects;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class ProjectsItem{

	@SerializedName("nama_customer")
	private String namaCustomer;

	@SerializedName("list_kegiatan")
	private List<ListKegiatanItem> listKegiatan;

	@SerializedName("id")
	private int id;

	@SerializedName("nama_project")
	private String namaProject;

	public void setNamaCustomer(String namaCustomer){
		this.namaCustomer = namaCustomer;
	}

	public String getNamaCustomer(){
		return namaCustomer;
	}

	public void setListKegiatan(List<ListKegiatanItem> listKegiatan){
		this.listKegiatan = listKegiatan;
	}

	public List<ListKegiatanItem> getListKegiatan(){
		return listKegiatan;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setNamaProject(String namaProject){
		this.namaProject = namaProject;
	}

	public String getNamaProject(){
		return namaProject;
	}

	@Override
 	public String toString(){
		return 
			"ProjectsItem{" + 
			"nama_customer = '" + namaCustomer + '\'' + 
			",list_kegiatan = '" + listKegiatan + '\'' + 
			",id = '" + id + '\'' + 
			",nama_project = '" + namaProject + '\'' + 
			"}";
		}
}