package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklistShipping.ViewHolder;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklistpm.ChecklistItem;
import id.geekgarden.esi.helper.UiUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterChecklistShipping extends Adapter<ViewHolder> {

  private List<ChecklistGroup> mTikets;
  private Context mContext;
  private List<ChecklistItem> mItem;
  private onCheckboxchecked onCheckboxchecked;
  Boolean is_checked = null;
  Integer id = null;
  String id_checklist_group;

  public AdapterChecklistShipping(ArrayList<ChecklistItem> items, Context context,
      onCheckboxchecked onCheckboxchecked) {
    this.mContext = context;
    this.onCheckboxchecked = onCheckboxchecked;
    this.mItem = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.item_list_checklist_shipping, parent, false);
    ViewHolder holder = new ViewHolder(view, this.onCheckboxchecked);
    return holder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    ChecklistItem checklistItem = getItem(position);
    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
    params.height =85; //height recycleviewer
    holder.itemView.setLayoutParams(params);
    holder.setIsRecyclable(false);
    /*holder.tvGroup.setText(checklistGroup.getName());*/
    holder.tvname.setText(checklistItem.getName());
    holder.chkother.setClickable(false);
/*    holder.chkother.setChecked(checklistItem.getIschecked());*/
    holder.tvpartno.setText("");
    holder.tvqty.setText("");
    holder.chkother.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        is_checked = b;
        holder.chkother.setChecked(is_checked);
        String partno = holder.tvpartno.getText().toString();
        if (TextUtils.isEmpty(holder.tvqty.getText().toString())){
          holder.chkother.setClickable(false);
          holder.chkother.setChecked(false);
          holder.tvqty.setError("this");
          UiUtils.showToast(mContext,"Please Input Quantity");
        }else {
          holder.chkother.setClickable(true);
        }
        String qty = holder.tvqty.getText().toString();
        holder.chkother.setClickable(true);
        Log.e("onCheckedChanged", "AdapterChecklistPM" + b);
        onCheckboxchecked
            .onCheckboxcheckedlistener(checklistItem.getId(), checklistItem.getChecklist_group_id(),
                is_checked, partno, qty);
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
    @BindView(R.id.tvpartno)
    EditText tvpartno;
    @BindView(R.id.tvqty)
    EditText tvqty;
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
      if (TextUtils.isEmpty(tvqty.getText().toString())) {
        chkother.setChecked(false);
        is_checked = false;
        tvqty.setError("This");
      }else{
        if (chkother.isChecked()) {
          chkother.setChecked(false);
          is_checked = false;
        } else {
          chkother.setChecked(true);
          is_checked = true;
        }
      }
      id = checklistItem.getId();
      id_checklist_group = checklistItem.getChecklist_group_id();
      /*onCheckboxchecked.onCheckboxcheckedlistener(checklistItem.getId(), checklistItem.getChecklist_group_id(),is_checked);*/
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

    void onCheckboxcheckedlistener(int id, String id_checklist_group, Boolean is_checked,
        String partno, String qty);
  }
}