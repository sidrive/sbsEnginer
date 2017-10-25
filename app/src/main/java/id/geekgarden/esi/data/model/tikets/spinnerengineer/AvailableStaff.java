
package id.geekgarden.esi.data.model.tikets.spinnerengineer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AvailableStaff {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
