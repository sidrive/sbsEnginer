
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistitHclab;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("checklist_item_id")
    @Expose
    private String checklistItemId;
    @SerializedName("cheklist_group_id")
    @Expose
    private String cheklistGroupId;
    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("remark")
    @Expose
    private String remark;

    public String getChecklistItemId() {
        return checklistItemId;
    }

    public void setChecklistItemId(String checklistItemId) {
        this.checklistItemId = checklistItemId;
    }

    public Datum withChecklistItemId(String checklistItemId) {
        this.checklistItemId = checklistItemId;
        return this;
    }

    public String getCheklistGroupId() {
        return cheklistGroupId;
    }

    public void setCheklistGroupId(String cheklistGroupId) {
        this.cheklistGroupId = cheklistGroupId;
    }

    public Datum withCheklistGroupId(String cheklistGroupId) {
        this.cheklistGroupId = cheklistGroupId;
        return this;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public Datum withValue(Boolean value) {
        this.value = value;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Datum withRemark(String remark) {
        this.remark = remark;
        return this;
    }

}
