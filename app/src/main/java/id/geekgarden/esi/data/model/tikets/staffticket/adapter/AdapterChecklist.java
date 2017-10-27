package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import com.choiintack.recursiverecyclerview.RecursiveRecyclerAdapter;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklist.ViewHolder;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ChecklistGroup;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterChecklist extends RecursiveRecyclerAdapter<ViewHolder> {

  private List<ChecklistGroup> mData;

  void setData(List<ChecklistGroup> data) {
    setItems(data);
    mData = data;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.item_list_checklist, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    super.onBindViewHolder(holder, position);
    holder.tvGroup.setText(getItem(position).toString());
    holder.tvname.setText(getItem(position).toString());
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvGroup)
    TextView tvGroup;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.chkother)
    CheckBox chkother;
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}


