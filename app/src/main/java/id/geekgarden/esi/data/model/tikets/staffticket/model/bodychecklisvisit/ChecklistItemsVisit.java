
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklisvisit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistItemsVisit {

    @SerializedName("checklist_item_id")
    @Expose
    private String checklistItemId;
    @SerializedName("cheklist_group_id")
    @Expose
    private String cheklistGroupId;
    @SerializedName("value")
    @Expose
    private Boolean value;

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

}
