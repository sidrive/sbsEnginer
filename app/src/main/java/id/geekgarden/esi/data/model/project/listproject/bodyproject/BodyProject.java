
package id.geekgarden.esi.data.model.project.listproject.bodyproject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyProject {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public BodyProject() {
    }

    public BodyProject(
        List<Datum> data) {
        this.data = data;
    }

}
