
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("checklist_group_id")
    @Expose
    private String checklist_group_id;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("name")
    @Expose
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getChecklist_group_id() {
        return checklist_group_id;
    }

    public void setChecklist_group_id(String checklist_group_id) {
        this.checklist_group_id = checklist_group_id;
    }
}
