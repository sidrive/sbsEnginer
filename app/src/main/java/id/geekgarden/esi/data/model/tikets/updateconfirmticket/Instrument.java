
package id.geekgarden.esi.data.model.tikets.updateconfirmticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Instrument {

    @SerializedName("data")
    @Expose
    private Data__ data;

    public Data__ getData() {
        return data;
    }

    public void setData(Data__ data) {
        this.data = data;
    }

}
