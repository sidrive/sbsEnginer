package id.geekgarden.esi.data.model.tikets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SQLiteSparepart {

    String _partnumber;
    String _description;
    String _qty;
    String _status;
    String _keterangan;

    public SQLiteSparepart(){


    }

    public SQLiteSparepart(String partnumber, String description, String qty, String status, String keterangan) {

        this._partnumber = partnumber;
        this._description = description;
        this._qty = qty;
        this._status = status;
        this._keterangan = keterangan;
    }

    public String getPartnumber(){
        return this._partnumber;
    }

    public void setPartnumber(String partnumber){
        this._partnumber = partnumber;
    }

    public String getDescription(){
        return this._description;
    }

    public void setDescription(String description){
        this._description = description;
    }

    public String getQty(){
        return this._qty;
    }

    public void setQty(String qty) {
        this._qty = qty;
    }

    public String getStatus(){
        return this._status;
    }

    public void setStatus(String status) {
        this._status = status;
    }

    public String getKeterangan(){
        return this._keterangan;
    }

    public void setKeterangan(String keterangan) {
        this._keterangan = keterangan;
    }
}
