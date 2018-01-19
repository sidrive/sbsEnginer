
package id.geekgarden.esi.data.model.tikets.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketType {

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
