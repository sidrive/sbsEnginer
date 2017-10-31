package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.staffticket.SQLiteSparepart;
import id.geekgarden.esi.data.model.tikets.staffticket.adapter.AdapterChecklist.Holder;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ChecklistItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterChecklist extends Adapter<Holder>{

  private List<ChecklistGroup> mTikets;
  private Context mContext;
  private List<ChecklistItem> mItem;
  private onCheckboxchecked onCheckboxchecked;
  Boolean is_checked = null;
  Integer id = null;
  String id_checklist_group = null;
  public AdapterChecklist(ArrayList<ChecklistItem> items, Context context, onCheckboxchecked onCheckboxchecked) {
    this.mContext = context;
    this.onCheckboxchecked = onCheckboxchecked;
    /*this.mTikets = serviceitem;*/
    this.mItem = items;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.item_list_checklist, parent, false);
    Holder holder = new Holder(view, this.onCheckboxchecked);
    return holder;
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    ChecklistItem checklistItem = getData(position);
    /*holder.tvGroup.setText(checklistGroup.getName());*/
    holder.tvname.setText(checklistItem.getName());
    holder.chkother.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        is_checked = b;
        Log.e("onCheckedChanged", "AdapterChecklist" + b);
        onCheckboxchecked.onCheckboxcheckedlistener(checklistItem.getId(),checklistItem.getChecklist_group_id(),is_checked);
      }
    });

  }

  @Override
  public int getItemCount() {
    return mItem.size();
  }

  public class Holder extends ViewHolder implements OnClickListener {

    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.chkother)
    CheckBox chkother;
    onCheckboxchecked onCheckboxchecked;

    public Holder(View itemView, onCheckboxchecked onCheckboxchecked) {
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

      if (chkother.isChecked()) {
        chkother.setChecked(false);
        is_checked = false;
      }
      else {
        chkother.setChecked(true);
        is_checked = true;
      }
      id = checklistItem.getId();
      id_checklist_group = checklistItem.getChecklist_group_id();
      //onCheckboxchecked.onCheckboxcheckedlistener(checklistItem.getId(), checklistItem.getChecklist_group_id(),is_checked);
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
    void onCheckboxcheckedlistener(int id, String id_checklis_group,Boolean is_checked);
  }
}
