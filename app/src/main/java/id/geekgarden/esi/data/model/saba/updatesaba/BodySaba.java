
package id.geekgarden.esi.data.model.saba.updatesaba;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodySaba {

    @SerializedName("description")
    @Expose
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
