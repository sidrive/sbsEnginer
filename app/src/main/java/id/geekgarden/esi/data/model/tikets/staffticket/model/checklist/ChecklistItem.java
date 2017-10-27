
package id.geekgarden.esi.data.model.tikets.staffticket.model.checklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.jar.Attributes.Name;

public class ChecklistItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("name")
    @Expose
    private String name;

    public ChecklistItem(String mname){
        name = mname;
    }

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

}
