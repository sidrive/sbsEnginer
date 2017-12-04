
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklisthclab;

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
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("is_checked")
    @Expose
    private Boolean ischecked;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIschecked() {
        return ischecked;
    }

    public void setIschecked(Boolean ischecked) {
        this.ischecked = ischecked;
    }
}
