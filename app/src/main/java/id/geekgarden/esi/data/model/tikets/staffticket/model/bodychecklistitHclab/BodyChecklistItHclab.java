
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistitHclab;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyChecklistItHclab {

    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public BodyChecklistItHclab withChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
        return this;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public BodyChecklistItHclab withData(List<Datum> data) {
        this.data = data;
        return this;
    }

}
