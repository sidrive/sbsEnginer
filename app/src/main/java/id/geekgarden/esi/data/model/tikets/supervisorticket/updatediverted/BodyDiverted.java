
package id.geekgarden.esi.data.model.tikets.supervisorticket.updatediverted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyDiverted {

    @SerializedName("staff_id")
    @Expose
    private Integer staffId;

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

}
