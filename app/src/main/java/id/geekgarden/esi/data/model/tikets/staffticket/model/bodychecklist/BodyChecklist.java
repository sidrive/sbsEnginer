
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BodyChecklist {

    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("travel_time")
    @Expose
    private String travel_time;
    @SerializedName("instrument_id")
    @Expose
    private Integer instrument_id;
    @SerializedName("data")
    @Expose
    private List<ChecklistItems> data = null;

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

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public Integer getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(Integer instrument_id) {
        this.instrument_id = instrument_id;
    }

    public List<ChecklistItems> getData() {
        return data;
    }

    public void setData(List<ChecklistItems> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BodyChecklist{" +
            "checklistId=" + checklistId +
            ", notes='" + notes + '\'' +
            ", travel_time='" + travel_time + '\'' +
            ", instrument_id=" + instrument_id +
            ", data=" + data +
            '}';
    }
}
