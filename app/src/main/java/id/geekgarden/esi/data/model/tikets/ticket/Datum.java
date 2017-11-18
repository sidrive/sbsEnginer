
package id.geekgarden.esi.data.model.tikets.ticket;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("priority")
    @Expose
    private String priority;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("status_text")
    @Expose
    private String statusText;
    @SerializedName("is_read")
    @Expose
    private Boolean isRead;
    @SerializedName("is_closed")
    @Expose
    private Boolean isClosed;
    @SerializedName("travel_time")
    @Expose
    private String travelTime;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("instrument")
    @Expose
    private Instrument instrument;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("staff_name")
    @Expose
    private String staffName;
    @SerializedName("staff_phone_number")
    @Expose
    private String staffPhoneNumber;
    @SerializedName("closed_at")
    @Expose
    private String closedAt;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("ticket_acticity_id")
    @Expose
    private String ticketActicityId;
    @SerializedName("acticity_id")
    @Expose
    private Integer acticityId;
    @SerializedName("customer")
    @Expose
    private Customer customer;
    @SerializedName("ticketType")
    @Expose
    private TicketType ticketType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public Boolean getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Boolean isClosed) {
        this.isClosed = isClosed;
    }

    public String getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(String travelTime) {
        this.travelTime = travelTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffPhoneNumber() {
        return staffPhoneNumber;
    }

    public void setStaffPhoneNumber(String staffPhoneNumber) {
        this.staffPhoneNumber = staffPhoneNumber;
    }

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public String getTicketActicityId() {
        return ticketActicityId;
    }

    public void setTicketActicityId(String ticketActicityId) {
        this.ticketActicityId = ticketActicityId;
    }

    public Integer getActicityId() {
        return acticityId;
    }

    public void setActicityId(Integer acticityId) {
        this.acticityId = acticityId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TicketType getTicketType() {
        return ticketType;
    }

    public void setTicketType(TicketType ticketType) {
        this.ticketType = ticketType;
    }

}