
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum_ {

    @SerializedName("checklist_item_id")
    @Expose
    private String checklistItemId;
    @SerializedName("value")
    @Expose
    private Boolean value;

    public String getChecklistItemId() {
        return checklistItemId;
    }

    public void setChecklistItemId(String checklistItemId) {
        this.checklistItemId = checklistItemId;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

}
