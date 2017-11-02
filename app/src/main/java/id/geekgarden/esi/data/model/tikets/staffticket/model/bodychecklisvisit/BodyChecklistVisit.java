
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class BodyChecklistVisit {

    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("data")
    @Expose
    private List<ChecklistItemsVisit> data = null;

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

    public List<ChecklistItemsVisit> getData() {
        return data;
    }

    public void setData(List<ChecklistItemsVisit> data) {
        this.data = data;
    }

}
