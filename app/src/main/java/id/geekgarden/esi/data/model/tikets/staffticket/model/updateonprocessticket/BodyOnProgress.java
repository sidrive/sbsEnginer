
package id.geekgarden.esi.data.model.tikets.staffticket.model.updateonprocessticket;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BodyOnProgress {

    @SerializedName("ticket_activity_id")
    @Expose
    private String ticketActivityId;
    @SerializedName("problem")
    @Expose
    private String problem;
    @SerializedName("fault_description")
    @Expose
    private String faultDescription;
    @SerializedName("solution")
    @Expose
    private String solution;
    @SerializedName("travel_time")
    @Expose
    private String travel_time;
    @SerializedName("instrument_id")
    @Expose
    private Integer instrument_id;
    @SerializedName("parts")
    @Expose
    private List<Part> parts = null;

    public String getTicketActivityId() {
        return ticketActivityId;
    }

    public void setTicketActivityId(String ticketActivityId) {
        this.ticketActivityId = ticketActivityId;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getFaultDescription() {
        return faultDescription;
    }

    public void setFaultDescription(String faultDescription) {
        this.faultDescription = faultDescription;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public Integer getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(Integer instrument_id) {
        this.instrument_id = instrument_id;
    }

    public List<Part> getParts() {
        return parts;
    }

    public void setParts(List<Part> parts) {
        this.parts = parts;
    }

}
