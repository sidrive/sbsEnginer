
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

    public List<ChecklistItems> getData() {
        return data;
    }

    public void setData(List<ChecklistItems> data) {
        this.data = data;
    }

}
