
package id.geekgarden.esi.data.model.openticket.responseopenticketservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketType {

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