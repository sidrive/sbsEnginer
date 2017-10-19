
package id.geekgarden.esi.data.model.tikets.spinnerpminstrument;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InstrumentType {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
