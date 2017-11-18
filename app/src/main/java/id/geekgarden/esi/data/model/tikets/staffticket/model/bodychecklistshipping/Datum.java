
package id.geekgarden.esi.data.model.tikets.staffticket.model.bodychecklistshipping;

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
    @SerializedName("part_no")
    @Expose
    private String partNo;
    @SerializedName("quantity")
    @Expose
    private String quantity;

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

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

}
