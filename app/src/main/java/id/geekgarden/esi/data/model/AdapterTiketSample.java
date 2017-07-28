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

public class AdapterTiketSample extends RecyclerView.Adapter<AdapterTiketSample.Holder> {
  private List<TiketsItem> mItem;
  private Context mContext;
  private PostItemListener mItemListener;

  public AdapterTiketSample(Context mContext,List<TiketsItem> mItem, PostItemListener mItemListener) {
    this.mItem = mItem;
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
    TiketsItem tiketsItem = mItem.get(position);
    TextView tv1 = holder.tv1;
    TextView tv2 = holder.tv2;

  }

  @Override
  public int getItemCount() {
    return mItem.size();
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
      TiketsItem tiketsItem = getItem(getAdapterPosition());

      this.postItemListener.onPostClickLsitener(tiketsItem.getId());
      notifyDataSetChanged();
    }
  }

  public interface PostItemListener {
      void onPostClickLsitener(long id);
  }
  public void UpdateData(List<TiketsItem> tiketsItems){
    mItem = tiketsItems;
    notifyDataSetChanged();
  }

  private TiketsItem getItem(int adapterPosition){
    return mItem.get(adapterPosition);
  }
}
