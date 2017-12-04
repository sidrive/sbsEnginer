
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklistanalyzer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("checklistGroup")
    @Expose
    private List<ChecklistGroup> checklistGroup = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChecklistGroup> getChecklistGroup() {
        return checklistGroup;
    }

    public void setChecklistGroup(List<ChecklistGroup> checklistGroup) {
        this.checklistGroup = checklistGroup;
    }

}
