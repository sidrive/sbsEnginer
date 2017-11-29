
package id.geekgarden.esi.data.model.tikets.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("serial_number")
    @Expose
    private String serialNumber;
    @SerializedName("instrument type id")
    @Expose
    private Integer instrumentTypeId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("contract_type")
    @Expose
    private String contractType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Integer getInstrumentTypeId() {
        return instrumentTypeId;
    }

    public void setInstrumentTypeId(Integer instrumentTypeId) {
        this.instrumentTypeId = instrumentTypeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

}
