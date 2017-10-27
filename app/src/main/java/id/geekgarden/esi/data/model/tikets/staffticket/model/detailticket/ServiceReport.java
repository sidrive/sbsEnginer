
package id.geekgarden.esi.data.model.tikets.staffticket.model.detailticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceReport {

    @SerializedName("data")
    @Expose
    private Data____ data;

    public Data____ getData() {
        return data;
    }

    public void setData(Data____ data) {
        this.data = data;
    }

}
