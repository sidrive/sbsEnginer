
package id.geekgarden.esi.data.model.openticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyResponseOpenOther {

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("division_id")
    @Expose
    private Integer divisionId;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("ticket_activity_id")
    @Expose
    private Integer ticketActivityId;
    @SerializedName("ticket_type_id")
    @Expose
    private Integer ticketTypeId;
    @SerializedName("staff_id")
    @Expose
    private Integer staffId;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Integer getTicketActivityId() {
        return ticketActivityId;
    }

    public void setTicketActivityId(Integer ticketActivityId) {
        this.ticketActivityId = ticketActivityId;
    }

    public Integer getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Integer ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

}
