
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("checklist_group_id")
    @Expose
    private Integer checklistGroupId;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("value")
    @Expose
    private Boolean value;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("part_no")
    @Expose
    private String partNo;
    @SerializedName("qty")
    @Expose
    private String qty;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChecklistGroupId() {
        return checklistGroupId;
    }

    public void setChecklistGroupId(Integer checklistGroupId) {
        this.checklistGroupId = checklistGroupId;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Boolean getValue() {
        return value;
    }

    public void setValue(Boolean value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

}
