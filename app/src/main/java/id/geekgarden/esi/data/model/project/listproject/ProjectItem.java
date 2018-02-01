
package id.geekgarden.esi.data.model.project.listproject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectItem {

    @SerializedName("data")
    @Expose
    private List<Datum__> data = null;

    public List<Datum__> getData() {
        return data;
    }

    public void setData(List<Datum__> data) {
        this.data = data;
    }

}
