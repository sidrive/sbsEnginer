
package id.geekgarden.esi.data.model.tikets.spinnerpminstrument;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("serial_number")
    @Expose
    private String serialNumber;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("contract_type")
    @Expose
    private String contractType;
    @SerializedName("instrumentType")
    @Expose
    private InstrumentType instrumentType;
    @SerializedName("instrumentCategory")
    @Expose
    private InstrumentCategory instrumentCategory;

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

    public InstrumentType getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(InstrumentType instrumentType) {
        this.instrumentType = instrumentType;
    }

    public InstrumentCategory getInstrumentCategory() {
        return instrumentCategory;
    }

    public void setInstrumentCategory(InstrumentCategory instrumentCategory) {
        this.instrumentCategory = instrumentCategory;
    }

}
