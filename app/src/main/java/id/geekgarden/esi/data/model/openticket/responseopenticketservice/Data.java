
package id.geekgarden.esi.data.model.openticket.responseopenticketservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

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
    private Object status;
    @SerializedName("status_text")
    @Expose
    private Object statusText;
    @SerializedName("is_read")
    @Expose
    private Object isRead;
    @SerializedName("is_closed")
    @Expose
    private Object isClosed;
    @SerializedName("travel_time")
    @Expose
    private String travelTime;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getStatusText() {
        return statusText;
    }

    public void setStatusText(Object statusText) {
        this.statusText = statusText;
    }

    public Object getIsRead() {
        return isRead;
    }

    public void setIsRead(Object isRead) {
        this.isRead = isRead;
    }

    public Object getIsClosed() {
        return isClosed;
    }

    public void setIsClosed(Object isClosed) {
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
