
package id.geekgarden.esi.data.model.tikets.staffticket.model.loadchecklist;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ticket_id")
    @Expose
    private Integer ticketId;
    @SerializedName("checklist_id")
    @Expose
    private Integer checklistId;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("notes")
    @Expose
    private String notes;

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getChecklistId() {
        return checklistId;
    }

    public void setChecklistId(Integer checklistId) {
        this.checklistId = checklistId;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
