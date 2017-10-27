package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.AdapterListProjects.PostItemListener;
import id.geekgarden.esi.data.model.tikets.staffticket.Selectable;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.ChecklistGroup;
import id.geekgarden.esi.data.model.tikets.staffticket.model.checklist.Datum;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterChecklist extends RecyclerView.Adapter<AdapterChecklist.Holder> {
  private List<Datum> mTikets;
  private Context mContext;
  private int selected_position;
  OnTiketPostItemListener ontiketpostItemListener;
  private Selectable mSelectable;
  private boolean isselected = false;

  public AdapterChecklist(ArrayList<Datum> tiketsItems, Context context,
      OnTiketPostItemListener ontiketpostItemListener) {
    this.mContext = context;
    this.mTikets = tiketsItems;
    this.ontiketpostItemListener = ontiketpostItemListener;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.item_list_checklist, parent, false);
    Holder holder = new Holder(view, this.ontiketpostItemListener);
    return holder;
  }

  @Override
  public void onBindViewHolder(final Holder holder, final int position) {
    final Datum datum = getData(position);
    TextView tv01 = holder.tvGroup;
    TextView tv02 = holder.tvname;

    tv01.setText(datum.getChecklistGroup().get(position).getName());
    tv02.setText(datum.getChecklistGroup().get(position).getChecklistItem().get(position).getName());

        /*holder.itemView.setBackgroundColor(selected_position == position ? Color.GREEN : Color.TRANSPARENT);*/
        /*if (selected_position == position && isselected == true){
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        }else{
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }*/
    holder.chkother.setOnClickListener(new OnClickListener() {
      public PostItemListener onTiketPostItemListener;

      @Override
      public void onClick(View view) {
        isselected = true;
        selected_position = position;
        datum.getId();
        notifyDataSetChanged();
      }
    });
  }

  @Override
  public int getItemCount() {
    return mTikets.size();
  }

  public class Holder extends ViewHolder implements OnClickListener {
    @BindView(R.id.tvGroup)
    TextView tvGroup;
    @BindView(R.id.tvname)
    TextView tvname;
    @BindView(R.id.chkother)
    CheckBox chkother;

    OnTiketPostItemListener onTiketPostItemListener;

    public Holder(View itemView, OnTiketPostItemListener ontiketpostItemListener) {
      super(itemView);
      ButterKnife.bind(this, itemView);
      this.onTiketPostItemListener = ontiketpostItemListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
            /*Log.e(TAG, "onClick: "+view.getId() );
            Log.e(TAG, "onClick: "+selected_position );
            selected_position = getAdapterPosition();
            notifyItemChanged(selected_position);*/
      Datum datum = getData(getAdapterPosition());
      this.onTiketPostItemListener.onPostClickListener(datum.getId());
      notifyDataSetChanged();
    }
  }

  public interface OnTiketPostItemListener {

    void onPostClickListener(int id);
  }

  private Datum getData(int adptPosition) {
    return mTikets.get(adptPosition);
  }

  public void UpdateTikets(List<Datum> tiketsItems) {
    mTikets = tiketsItems;
    notifyDataSetChanged();
  }
}
