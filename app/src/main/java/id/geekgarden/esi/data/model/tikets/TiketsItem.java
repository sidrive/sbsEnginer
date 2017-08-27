package id.geekgarden.esi.data.model.tikets;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class TiketsItem{

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

	public TiketsItem() {
		this.date = date;
		this.namaCustomer = namaCustomer;
		this.snAlat = snAlat;
		this.urgencyLevel = urgencyLevel;
		this.descripsiton = descripsiton;
		this.id = id;
		this.typeAlat = typeAlat;
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getNamaCustomer() {
		return namaCustomer;
	}

	public void setNamaCustomer(String namaCustomer) {
		this.namaCustomer = namaCustomer;
	}

	public String getSnAlat() {
		return snAlat;
	}

	public void setSnAlat(String snAlat) {
		this.snAlat = snAlat;
	}

	public String getUrgencyLevel() {
		return urgencyLevel;
	}

	public void setUrgencyLevel(String urgencyLevel) {
		this.urgencyLevel = urgencyLevel;
	}

	public String getDescripsiton() {
		return descripsiton;
	}

	public void setDescripsiton(String descripsiton) {
		this.descripsiton = descripsiton;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeAlat() {
		return typeAlat;
	}

	public void setTypeAlat(String typeAlat) {
		this.typeAlat = typeAlat;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}