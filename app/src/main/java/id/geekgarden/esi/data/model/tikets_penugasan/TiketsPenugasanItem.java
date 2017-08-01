package id.geekgarden.esi.data.model.tikets_penugasan;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TiketsPenugasanItem{

	@SerializedName("date")
	private String date;

	@SerializedName("nama_customer")
	private String namaCustomer;

	@SerializedName("sn_alat")
	private String snAlat;

	@SerializedName("urgency_level")
	private String urgencyLevel;

	@SerializedName("descripsiton")
	private String descripsiton;

	@SerializedName("id")
	private int id;

	@SerializedName("type_alat")
	private String typeAlat;

	@SerializedName("status")
	private String status;

	public void setDate(String date){
		this.date = date;
	}

	public String getDate(){
		return date;
	}

	public void setNamaCustomer(String namaCustomer){
		this.namaCustomer = namaCustomer;
	}

	public String getNamaCustomer(){
		return namaCustomer;
	}

	public void setSnAlat(String snAlat){
		this.snAlat = snAlat;
	}

	public String getSnAlat(){
		return snAlat;
	}

	public void setUrgencyLevel(String urgencyLevel){
		this.urgencyLevel = urgencyLevel;
	}

	public String getUrgencyLevel(){
		return urgencyLevel;
	}

	public void setDescripsiton(String descripsiton){
		this.descripsiton = descripsiton;
	}

	public String getDescripsiton(){
		return descripsiton;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public void setTypeAlat(String typeAlat){
		this.typeAlat = typeAlat;
	}

	public String getTypeAlat(){
		return typeAlat;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"TiketsPenugasanItem{" + 
			"date = '" + date + '\'' + 
			",nama_customer = '" + namaCustomer + '\'' + 
			",sn_alat = '" + snAlat + '\'' + 
			",urgency_level = '" + urgencyLevel + '\'' + 
			",descripsiton = '" + descripsiton + '\'' + 
			",id = '" + id + '\'' + 
			",type_alat = '" + typeAlat + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}