
package id.geekgarden.esi.data.model.openticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyResponseOpenservice {

    @SerializedName("ticket_type_id")
    @Expose
    private Integer ticketTypeId;
    @SerializedName("division_id")
    @Expose
    private Integer divisionId;
    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("instrument_id")
    @Expose
    private Integer instrumentId;
    @SerializedName("device")
    @Expose
    private String device;
    @SerializedName("staff_id")
    @Expose
    private Integer staffId;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("description")
    @Expose
    private String description;

    public Integer getTicketTypeId() {
        return ticketTypeId;
    }

    public void setTicketTypeId(Integer ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    public Integer getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(Integer divisionId) {
        this.divisionId = divisionId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Integer instrumentId) {
        this.instrumentId = instrumentId;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getStaffId() {
        return staffId;
    }

    public void setStaffId(Integer staffId) {
        this.staffId = staffId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
