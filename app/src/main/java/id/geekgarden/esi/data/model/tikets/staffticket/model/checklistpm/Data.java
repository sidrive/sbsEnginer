
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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


    @SerializedName("ticket_id")
    @Expose
    private Integer idti;

    @SerializedName("checklist_id")
    @Expose
    private Integer checlistId;

    @SerializedName("data")
    @Expose
    private List<ChecklistTiket> checklistTikets = null;

    public Integer getIdti() {
        return idti;
    }

    public void setIdti(Integer idti) {
        this.idti = idti;
    }

    public Integer getCheclistId() {
        return checlistId;
    }

    public void setCheclistId(Integer checlistId) {
        this.checlistId = checlistId;
    }

    public List<ChecklistTiket> getChecklistTikets() {
        return checklistTikets;
    }

    public void setChecklistTikets(List<ChecklistTiket> checklistTikets) {
        this.checklistTikets = checklistTikets;
    }

}
