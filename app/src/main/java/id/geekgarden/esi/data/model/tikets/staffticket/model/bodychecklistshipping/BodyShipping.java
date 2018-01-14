
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyShipping {

    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("travel_time")
    @Expose
    private String travel_time;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    @Override
    public String toString() {
        return "BodyShipping{" +
                "checklistId=" + checklistId +
                ", notes='" + notes + '\'' +
                ", data=" + data +
                '}';
    }
}
