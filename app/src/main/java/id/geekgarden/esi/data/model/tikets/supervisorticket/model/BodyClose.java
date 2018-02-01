
package id.geekgarden.esi.data.model.tikets.supervisorticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyClose {

    @SerializedName("rating")
    @Expose
    private Float rating;
    @SerializedName("comment")
    @Expose
    private String comment;

    public Float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
