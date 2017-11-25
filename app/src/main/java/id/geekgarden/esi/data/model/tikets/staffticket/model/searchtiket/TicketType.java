
package id.geekgarden.esi.data.model.tikets.staffticket.model.searchtiket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TicketType {

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
