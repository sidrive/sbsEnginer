
package id.geekgarden.esi.data.model.tikets.staffticket.model.updateconfirmticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyConfirmTicket {

    @SerializedName("comment")
    @Expose
    private String comment;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
