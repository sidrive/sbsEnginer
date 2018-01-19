
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistitanalyzer;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyChecklistItAnalyzer {

    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("genkey")
    @Expose
    private String genkey;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public BodyChecklistItAnalyzer withChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
        return this;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getGenkey() {
        return genkey;
    }

    public void setGenkey(String genkey) {
        this.genkey = genkey;
    }

    public BodyChecklistItAnalyzer withGenkey(String genkey) {
        this.genkey = genkey;
        return this;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public BodyChecklistItAnalyzer withData(List<Datum> data) {
        this.data = data;
        return this;
    }

}
