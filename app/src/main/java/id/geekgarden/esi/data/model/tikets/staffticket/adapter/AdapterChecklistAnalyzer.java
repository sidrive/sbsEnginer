package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistanalyzer.ChecklistItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterChecklistAnalyzer extends Adapter<AdapterChecklistAnalyzer.ViewHolder> {

  private Context mContext;
  private List<ChecklistItem> mItem;
  private onCheckboxchecked onCheckboxchecked;
  Boolean is_checked = null;
  Integer id = null;
  int id_checklist_group = 0;

  public AdapterChecklistAnalyzer(ArrayList<ChecklistItem> items, Context context,
      onCheckboxchecked onCheckboxchecked) {
    this.mContext = context;
    this.onCheckboxchecked = onCheckboxchecked;
    this.mItem = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.item_list_checklist_visit, parent, false);
    ViewHolder holder = new ViewHolder(view, this.onCheckboxchecked);
    return holder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ChecklistItem checklistItem = getItem(position);
    holder.setIsRecyclable(false);
    holder.tvdescription.setTextColor(Color.BLACK);
    holder.tvname.setText(checklistItem.getName());
//    holder.chkother.setChecked(checklistItem.getIschecked());
    if (checklistItem.getIschecked() != null) {
      Log.e("AdapterChecklistHclab", "onBindViewHolder: " + checklistItem.getNote());
      holder.chkother.setChecked(checklistItem.getIschecked());
    }
    if(checklistItem.getNote() != null){
      holder.tvdescription.setText(checklistItem.getNote());
    }
//    holder.tvdescription.setText(checklistItem.getNote());
    holder.chkother.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        is_checked = b;
        holder.chkother.setChecked(is_checked);
        String description = holder.tvdescription.getText().toString();
        if (TextUtils.isEmpty(holder.tvdescription.getText().toString())) {
          holder.chkother.setClickable(false);
          holder.chkother.setChecked(false);
          holder.tvdescription.setError("this");
        } else {
          holder.chkother.setClickable(true);
        }
        Log.e("onCheckedChanged", "AdapterChecklistPM" + b);
        onCheckboxchecked
            .onCheckboxcheckedlistener(checklistItem.getId(), checklistItem.getChecklistGroupId(), checklistItem.getName(), position,
                is_checked, description);
      }
    });
  }

  @Override
  public int getItemCount() {
    return mItem.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.chkother)
    CheckBox chkother;
    @BindView(R.id.tvdescription)
    EditText tvdescription;
    onCheckboxchecked onCheckboxchecked;

    public ViewHolder(View itemView, onCheckboxchecked onCheckboxchecked) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.onCheckboxchecked = onCheckboxchecked;
      this.setIsRecyclable(false);
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      ChecklistItem checklistItem = getData(getAdapterPosition());
      int adapterPosition = getAdapterPosition();
      if (TextUtils.isEmpty(tvdescription.getText().toString())) {
        chkother.setChecked(false);
        is_checked = false;
        tvdescription.setError("This");
      } else {
        if (chkother.isChecked()) {
          chkother.setChecked(false);
          is_checked = false;
        } else {
          chkother.setChecked(true);
          is_checked = true;
        }
      }
      id = checklistItem.getId();
      id_checklist_group = checklistItem.getChecklistGroupId();
    }
  }

  private ChecklistItem getData(int adptPosition) {
    return mItem.get(adptPosition);
  }

  @Nullable
  public ChecklistItem getItem(int position) {
    return mItem.get(position);
  }

  public void UpdateTikets(List<ChecklistItem> listarray) {
    mItem = listarray;
    notifyDataSetChanged();
  }

  public interface onCheckboxchecked {

    void onCheckboxcheckedlistener(int id, int id_checklist_group, String name,
        int position, Boolean is_checked,
        String description);
  }
}