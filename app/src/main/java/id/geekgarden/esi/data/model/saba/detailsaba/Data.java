
package id.geekgarden.esi.data.model.saba.detailsaba;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("ended_at")
    @Expose
    private Object endedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public Object getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(Object endedAt) {
        this.endedAt = endedAt;
    }

}
