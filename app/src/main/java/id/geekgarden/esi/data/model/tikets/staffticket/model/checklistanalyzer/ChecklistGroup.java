
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklistanalyzer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ChecklistGroup {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("checklistItem")
    @Expose
    private List<ChecklistItem> checklistItem = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
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

    public List<ChecklistItem> getChecklistItem() {
        return checklistItem;
    }

    public void setChecklistItem(List<ChecklistItem> checklistItem) {
        this.checklistItem = checklistItem;
    }

}
