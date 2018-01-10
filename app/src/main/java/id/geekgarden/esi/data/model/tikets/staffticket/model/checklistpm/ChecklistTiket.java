package id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ikun on 09/01/18.
 */

public class ChecklistTiket {

    @SerializedName("checklist_item_id")
    @Expose
    private Integer idti;

    @SerializedName("cheklist_group_id")
    @Expose
    private Integer checklistGroupIdti;

    @SerializedName("value")
    @Expose
    private Boolean valueti;

    @SerializedName("part_no")
    @Expose
    private String partNoti;

    @SerializedName("quantity")
    @Expose
    private String qtyti;

    @SerializedName("name")
    @Expose
    private String name;

    public Integer getIdti() {
        return idti;
    }

    public void setIdti(Integer idti) {
        this.idti = idti;
    }

    public Integer getChecklistGroupIdti() {
        return checklistGroupIdti;
    }

    public void setChecklistGroupIdti(Integer checklistGroupIdti) {
        this.checklistGroupIdti = checklistGroupIdti;
    }


    public Boolean getValueti() {
        return valueti;
    }

    public void setValueti(Boolean valueti) {
        this.valueti = valueti;
    }

    public String getPartNoti() {
        return partNoti;
    }

    public void setPartNoti(String partNoti) {
        this.partNoti = partNoti;
    }

    public String getQtyti() {
        return qtyti;
    }

    public void setQtyti(String qtyti) {
        this.qtyti = qtyti;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChecklistTiket{" +
                "idti=" + idti +
                ", checklistGroupIdti=" + checklistGroupIdti +
                ", valueti=" + valueti +
                ", partNoti='" + partNoti + '\'' +
                ", qtyti='" + qtyti + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
