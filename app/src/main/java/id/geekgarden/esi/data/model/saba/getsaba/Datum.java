
package id.geekgarden.esi.data.model.saba.getsaba;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("is_ended")
    @Expose
    private Boolean isEnded;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("ended_at")
    @Expose
    private EndedAt endedAt;

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

    public Boolean getIsEnded() {
        return isEnded;
    }

    public void setIsEnded(Boolean isEnded) {
        this.isEnded = isEnded;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public EndedAt getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(EndedAt endedAt) {
        this.endedAt = endedAt;
    }

}
