package id.geekgarden.esi.data.model.tikets_dialihkan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;


/**
 * Created by setia on 30/07/2017.
 */

public class AdapterTiketsDialihkan extends RecyclerView.Adapter<AdapterTiketsDialihkan.Holder> {
    private Context mContext;
    private List<TiketsDialihkanItem> mItem;
    private PostItemListener postItemListener;

    public AdapterTiketsDialihkan(Context mContext, List<TiketsDialihkanItem> mItems, PostItemListener postItemListener) {
        this.mContext = mContext;
        this.mItem = mItems;
        this.postItemListener = postItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_list_tiket,parent,false);
        Holder holder = new Holder(v,this.postItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        TiketsDialihkanItem item = getItem(position);
        TextView tvNamaCustomer = holder.tvNamaCustomer;
        TextView tvTipeAlat = holder.tvTipeAlat;
        TextView tvTime = holder.tvTime;
        TextView tvDescTiket = holder.tvDescTiket;
        TextView tvLevel = holder.tvLevel;
        TextView tvStatus = holder.tvStatus;
        TextView tvSnAlat = holder.tvSnAlat;
        tvNamaCustomer.setText(item.getNamaCustomer());
        tvTipeAlat.setText(item.getTypeAlat());
        tvTime.setText(item.getDate());
        tvDescTiket.setText(item.getDescripsiton());
        tvLevel.setText(item.getUrgencyLevel());
        tvStatus.setText(item.getStatus());
        tvSnAlat.setText(item.getSnAlat());

    }

    @Override
    public int getItemCount() {
        return mItem.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PostItemListener postItemListener;
        @BindView(R.id.tvNamaCustomer)TextView tvNamaCustomer;
        @BindView(R.id.tvNoTelp)TextView tvTipeAlat;
        @BindView(R.id.tvTime)TextView tvTime;
        @BindView(R.id.tvDescTiket)TextView tvDescTiket;
        @BindView(R.id.tvLevel)TextView tvLevel;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.tvSnAlat)TextView tvSnAlat;
        public Holder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TiketsDialihkanItem mItem = getItem(getAdapterPosition());
            this.postItemListener.OnPostClickItemListener(mItem.getId(),mItem.getStatus());
            notifyDataSetChanged();
        }
    }
    public interface PostItemListener {
        void OnPostClickItemListener(long id, String s);
    }
    private TiketsDialihkanItem getItem(int pos){
        return mItem.get(pos);
    }
    public void UpdateItem(List<TiketsDialihkanItem> items){
        this.mItem = items;
        notifyDataSetChanged();
    }

}
