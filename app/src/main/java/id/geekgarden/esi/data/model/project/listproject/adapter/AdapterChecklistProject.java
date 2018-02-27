package id.geekgarden.esi.data.model.project.listproject.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.project.listproject.adapter.AdapterChecklistProject.ViewHolder;
import id.geekgarden.esi.data.model.project.listproject.detailproject.Datum_;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterChecklistProject extends Adapter<ViewHolder> {

  private Context mContext;
  private List<Datum_> mItem;
  private onCheckboxchecked onCheckboxchecked;
  Boolean is_checked = null;
  Integer id = null;

  public AdapterChecklistProject(ArrayList<Datum_> items, Context context,
      onCheckboxchecked onCheckboxchecked) {
    this.mContext = context;
    this.onCheckboxchecked = onCheckboxchecked;
    /*this.mTikets = serviceitem;*/
    this.mItem = items;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.item_list_checklist_project, parent, false);
    ViewHolder holder = new ViewHolder(view, this.onCheckboxchecked);
    return holder;
  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    Datum_ checklistItem = getData(position);
    holder.setIsRecyclable(false);
    /*holder.tvGroup.setText(checklistGroup.getName());*/
    holder.tvname.setText(checklistItem.getName());
    holder.createtv.setText(checklistItem.getStartDate());
    holder.endtv.setText(checklistItem.getEndDate());
    if (checklistItem.getValue() == 1) {
      holder.chkother.setChecked(true);
      holder.chkother.setEnabled(false);
    } else {
      holder.chkother.setChecked(false);
      holder.chkother.setEnabled(true);
    }
    holder.chkother.setOnCheckedChangeListener(new OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        holder.setIsRecyclable(false);
        is_checked = b;
        holder.chkother.setChecked(is_checked);
        Log.e("onCheckedChanged", "AdapterChecklistPM" + b);
        onCheckboxchecked.onCheckboxcheckedlistener(checklistItem.getId(), is_checked);
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
    @BindView(R.id.createtv)
    TextView createtv;
    @BindView(R.id.endtv)
    TextView endtv;
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
      Datum_ checklistItem = getData(getAdapterPosition());
      int adapterPosition = getAdapterPosition();
      if (chkother.isChecked()) {
        chkother.setChecked(false);
        is_checked = false;
      } else {
        chkother.setChecked(true);
        is_checked = true;
      }
      id = checklistItem.getId();
      /*onCheckboxchecked.onCheckboxcheckedlistener(checklistItem.getId(), checklistItem.getChecklist_group_id(),is_checked);*/
    }
  }

  private Datum_ getData(int adptPosition) {
    return mItem.get(adptPosition);
  }

  @Nullable
  public Datum_ getItem(int position) {
    return mItem.get(position);
  }

  public void UpdateTikets(List<Datum_> listarray) {
    mItem = listarray;
    notifyDataSetChanged();
  }

  public interface onCheckboxchecked {

    void onCheckboxcheckedlistener(int id, Boolean is_checked);
  }
}