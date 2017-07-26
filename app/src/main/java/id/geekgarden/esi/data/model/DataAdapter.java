package id.geekgarden.esi.data.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import java.util.List;

/**
 * Created by GeekGarden on 25/07/2017.
 */

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {
  private List<DataItem> mData;
  private Context mContext;
  private PostItemListener mItemListener;

  public DataAdapter(Context mContext,List<DataItem> mData, PostItemListener mItemListener) {
    this.mData = mData;
    this.mContext = mContext;
    this.mItemListener = mItemListener;
  }

  @Override
  public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    Context context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    View view = inflater.inflate(R.layout.item_list_tiket,parent,false);
    Holder holder = new Holder(view,this.mItemListener);
    return holder;
  }

  @Override
  public void onBindViewHolder(Holder holder, int position) {
    DataItem dataItem = mData.get(position);
    TextView tv1 = holder.tv1;
    TextView tv2 = holder.tv2;
    tv1.setText(dataItem.getFirstName());
    tv2.setText(dataItem.getLastName());
  }

  @Override
  public int getItemCount() {
    return mData.size();
  }

  public class Holder extends RecyclerView.ViewHolder implements OnClickListener {
    PostItemListener postItemListener;
    @BindView(R.id.tvNamaCustomer)TextView tv1;
    @BindView(R.id.tvTipeAlat)TextView tv2;

    public Holder(View itemView, PostItemListener postItemListener) {
      super(itemView);
      ButterKnife.bind(this,itemView);
      this.postItemListener = mItemListener;
      itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
      DataItem dataItem = getItem(getAdapterPosition());
      this.postItemListener.onPostClickLsitener(dataItem.getId());
      notifyDataSetChanged();
    }
  }

  public interface PostItemListener {
      void onPostClickLsitener(long id);
  }
  public void UpdateData(List<DataItem> dataItems){
    mData = dataItems;
    notifyDataSetChanged();
  }

  private DataItem getItem(int adapterPosition){
    return mData.get(adapterPosition);
  }
}
