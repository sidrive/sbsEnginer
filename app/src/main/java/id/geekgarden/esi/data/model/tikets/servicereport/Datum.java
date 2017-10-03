
package id.geekgarden.esi.data.model.tikets.servicereport;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ticket_activity_id")
    @Expose
    private Integer ticketActivityId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("problem")
    @Expose
    private String problem;
    @SerializedName("fault_description")
    @Expose
    private String faultDescription;
    @SerializedName("solution")
    @Expose
    private String solution;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTicketActivityId() {
        return ticketActivityId;
    }

    public void setTicketActivityId(Integer ticketActivityId) {
        this.ticketActivityId = ticketActivityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
