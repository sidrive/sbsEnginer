
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChecklistGroup {

    @SerializedName("id")
    @Expose
    private Integer id;
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
