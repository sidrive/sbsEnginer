package id.geekgarden.esi.data.model.tikets.detailonholdnested;

import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import butterknife.BindView;
import id.geekgarden.esi.R;

/**
 * Created by raka on 9/29/17.
 */

public class PartViewHolder extends ChildViewHolder {
    private TextView TipeAlat;
    private TextView Quantity;
    private TextView Desc;
    private TextView Remarks;
    private TextView Status;
    public PartViewHolder(View itemView) {
        super(itemView);
        TipeAlat = itemView.findViewById(R.id.tvTipeAlat);
        Quantity = itemView.findViewById(R.id.tvQuantity);
        Desc = itemView.findViewById(R.id.tvDesc);
        Remarks = itemView.findViewById(R.id.tvremarks);
        Status = itemView.findViewById(R.id.tvstatus);
    }

    public void setTipeAlat(String tipeAlat) {
        TipeAlat.setText(tipeAlat);
    }

    public void setQuantity(String quantity) {
        Quantity.setText(quantity);
    }

    public void setDesc(String desc) {
        Desc.setText(desc);
    }

    public void setRemarks(String remarks) {
        Remarks.setText(remarks);
    }

    public void setStatus(String status) {
        Status.setText(status);
    }
}