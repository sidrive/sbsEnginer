
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
    private Object travelTime;
    @SerializedName("customer_name")
    @Expose
    private String customerName;
    @SerializedName("request")
    @Expose
    private String request;
    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("assigneeId")
    @Expose
    private Integer assigneeId;
    @SerializedName("assigneeName")
    @Expose
    private String assigneeName;
    @SerializedName("invoice")
    @Expose
    private String invoice;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("software")
    @Expose
    private Software software;
    @SerializedName("hardware")
    @Expose
    private Hardware hardware;
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
    private ClosedAt closedAt;
    @SerializedName("created_at")
    @Expose
    private CreatedAt createdAt;
    @SerializedName("ticket_activity_id")
    @Expose
    private String ticketActivityId;
    @SerializedName("activity_id")
    @Expose
    private Integer activityId;
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

    public Object getTravelTime() {
        return travelTime;
    }

    public void setTravelTime(Object travelTime) {
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
    }

    public String getAssigneeName() {
        return assigneeName;
    }

    public void setAssigneeName(String assigneeName) {
        this.assigneeName = assigneeName;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public void setHardware(Hardware hardware) {
        this.hardware = hardware;
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

    public ClosedAt getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(ClosedAt closedAt) {
        this.closedAt = closedAt;
    }

    public CreatedAt getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(CreatedAt createdAt) {
        this.createdAt = createdAt;
    }

    public String getTicketActivityId() {
        return ticketActivityId;
    }

    public void setTicketActivityId(String ticketActivityId) {
        this.ticketActivityId = ticketActivityId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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
