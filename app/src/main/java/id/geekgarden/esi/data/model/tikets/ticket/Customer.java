
package id.geekgarden.esi.data.model.tikets.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("data")
    @Expose
    private Data___ data;

    public Data___ getData() {
        return data;
    }

    public void setData(Data___ data) {
        this.data = data;
    }

}
