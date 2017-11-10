
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyChecklistVisit {

    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("instrument_id")
    @Expose
    private Integer instrumentId;
    @SerializedName("data")
    @Expose
    private List<ChecklistItemVisit> data = null;

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

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public List<ChecklistItemVisit> getData() {
        return data;
    }

    public void setData(List<ChecklistItemVisit> data) {
        this.data = data;
    }

}
