package id.geekgarden.esi.data.model.tikets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;


/**
 * Created by setia on 30/07/2017.
 */

public class AdapterTiket extends RecyclerView.Adapter<AdapterTiket.Holder> {
    private List<TiketsItem> mTikets;
    private Context mContext;
    PostItemListener postItemListener;

    public AdapterTiket(Context context, ArrayList<TiketsItem> tiketsItems, PostItemListener postItemListener) {
        this.mContext = context;
        this.mTikets = tiketsItems;
        this.postItemListener = postItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_tiket,parent,false);
        Holder holder = new Holder(view,this.postItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        TiketsItem tiketsItem = getItem(position);
        TextView tvNamaCustomer = holder.tvNamaCustomer;
        TextView tvTipeAlat = holder.tvTipeAlat;
        TextView tvTime = holder.tvTime;
        TextView tvDescTiket = holder.tvDescTiket;
        TextView tvLevel = holder.tvLevel;
        TextView tvStatus = holder.tvStatus;
        TextView tvSnAlat = holder.tvSnAlat;

        tvNamaCustomer.setText(tiketsItem.getNamaCustomer());
        tvTipeAlat.setText(tiketsItem.getTypeAlat());
        tvTime.setText(tiketsItem.getDate());
        tvDescTiket.setText(tiketsItem.getDescripsiton());
        tvLevel.setText(tiketsItem.getUrgencyLevel());
        tvStatus.setText(tiketsItem.getStatus());
        tvSnAlat.setText(tiketsItem.getSnAlat());

    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PostItemListener postItemListener;
        @BindView(R.id.tvNamaCustomer)TextView tvNamaCustomer;
        @BindView(R.id.tvTipeAlat)TextView tvTipeAlat;
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
            TiketsItem tiketsItem = getItem(getAdapterPosition());
            this.postItemListener.onPostClickLsitener(tiketsItem.getId(),tiketsItem.getStatus());
            notifyDataSetChanged();
        }
    }
    public interface PostItemListener {
        void onPostClickLsitener(long id, String status);
    }
    private TiketsItem getItem(int adptPosition){
        return mTikets.get(adptPosition);
    }
    public void UpdateTikets(List<TiketsItem> tiketsItems){
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}
