package id.geekgarden.esi.data.model.tikets.updateonprocessticket.hold;

/**
 * Created by raka on 9/22/17.
 */
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

}