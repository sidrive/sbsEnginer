
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistItemVisit {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("checklist_item_id")
    @Expose
    private String checklistItemId;
    @SerializedName("cheklist_group_id")
    @Expose
    private String cheklistGroupId;
    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("note")
    @Expose
    private String note;

    public String getChecklistItemId() {
        return checklistItemId;
    }

    public void setChecklistItemId(String checklistItemId) {
        this.checklistItemId = checklistItemId;
    }

    public String getCheklistGroupId() {
        return cheklistGroupId;
    }

    public void setCheklistGroupId(String cheklistGroupId) {
        this.cheklistGroupId = cheklistGroupId;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChecklistItemVisit{" +
            "name='" + name + '\'' +
            ", checklistItemId='" + checklistItemId + '\'' +
            ", cheklistGroupId='" + cheklistGroupId + '\'' +
            ", value=" + value +
            ", note='" + note + '\'' +
            '}';
    }
}
