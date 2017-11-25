
package id.geekgarden.esi.data.model.tikets.staffticket.model.detailticketother;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Request {

    @SerializedName("data")
    @Expose
    private Data_____ data;

    public Data_____ getData() {
        return data;
    }

    public void setData(Data_____ data) {
        this.data = data;
    }

}
