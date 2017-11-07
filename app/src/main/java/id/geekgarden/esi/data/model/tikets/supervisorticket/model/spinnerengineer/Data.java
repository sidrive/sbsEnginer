
package id.geekgarden.esi.data.model.tikets.supervisorticket.model.spinnerengineer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("customer")
    @Expose
    private String customer;
    @SerializedName("original staff")
    @Expose
    private String originalStaff;
    @SerializedName("available staff")
    @Expose
    private AvailableStaff availableStaff;

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

    public AvailableStaff getAvailableStaff() {
        return availableStaff;
    }

    public void setAvailableStaff(AvailableStaff availableStaff) {
        this.availableStaff = availableStaff;
    }

}
