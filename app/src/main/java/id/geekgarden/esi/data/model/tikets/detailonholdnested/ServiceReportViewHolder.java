package id.geekgarden.esi.data.model.tikets.detailonholdnested;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import butterknife.BindView;
import id.geekgarden.esi.R;

/**
 * Created by raka on 9/29/17.
 */

public class ServiceReportViewHolder extends GroupViewHolder {
    TextView Activity;
    TextView Problem;
    TextView Fault;
    TextView Actionsolution;
    public ServiceReportViewHolder(View itemView) {
        super(itemView);
        Activity = itemView.findViewById(R.id.tvactivity);
        Problem = itemView.findViewById(R.id.tvproblem);
        Fault = itemView.findViewById(R.id.tvfault);
        Actionsolution = itemView.findViewById(R.id.tvactionsolution);
    }

    public void setActivity(String activity) {
        this.Activity.setText(activity);
    }

    public void setProblem(String problem) {
        this.Problem.setText(problem);
    }

    public void setFault(String fault) {
        this.Fault.setText(fault);
    }

    public void setActionsolution(String actionsolution) {
        this.Actionsolution.setText(actionsolution);
    }
}
