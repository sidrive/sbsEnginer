
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
    @SerializedName("travel_time")
    @Expose
    private String travel_time;
    @SerializedName("instrument_id")
    @Expose
    private Integer instrumentId;
    @SerializedName("category")
    @Expose
    private String category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<ChecklistItemVisit> getData() {
        return data;
    }

    public void setData(List<ChecklistItemVisit> data) {
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
        return "BodyChecklistVisit{" +
            "checklistId=" + checklistId +
            ", notes='" + notes + '\'' +
            ", travel_time='" + travel_time + '\'' +
            ", instrumentId=" + instrumentId +
            ", category='" + category + '\'' +
            ", data=" + data +
            '}';
    }
}
