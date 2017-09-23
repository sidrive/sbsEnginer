package id.geekgarden.esi.data.model.tikets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SQLiteSparepart {

    @SerializedName("partnumber")
    @Expose
    private String partnumber;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("qty")
    @Expose
    private String qty;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("keterangan")
    @Expose
    private String keterangan;


    public String getPartnumber(){
        return this.partnumber;
    }

    public void setPartnumber(String partnumber){
        this.partnumber = partnumber;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getQty(){
        return this.qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan(){
        return this.keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
