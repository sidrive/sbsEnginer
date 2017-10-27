
package id.geekgarden.esi.data.model.tikets.supervisorticket.updatediverted;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("original staff")
    @Expose
    private String originalStaff;
    @SerializedName("updated staff ")
    @Expose
    private String updatedStaff;

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getOriginalStaff() {
        return originalStaff;
    }

    public void setOriginalStaff(String originalStaff) {
        this.originalStaff = originalStaff;
    }

    public String getUpdatedStaff() {
        return updatedStaff;
    }

    public void setUpdatedStaff(String updatedStaff) {
        this.updatedStaff = updatedStaff;
    }

}
