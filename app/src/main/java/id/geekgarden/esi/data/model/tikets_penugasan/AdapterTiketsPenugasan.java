package id.geekgarden.esi.data.model.tikets_penugasan;

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

public class AdapterTiketsPenugasan extends RecyclerView.Adapter<AdapterTiketsPenugasan.Holder> {
    private Context context;
    private List<TiketsPenugasanItem> mTikets;
    ClickItemListener clickItemListener;

    public AdapterTiketsPenugasan(Context context, List<TiketsPenugasanItem> mTikets, ClickItemListener clickItemListener) {
        this.context = context;
        this.mTikets = mTikets;
        this.clickItemListener = clickItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_tiket,parent,false);
        Holder holder = new Holder(view,clickItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        TiketsPenugasanItem item = getItem(position);
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
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ClickItemListener clickItemListener;
        @BindView(R.id.tvNamaCustomer)TextView tvNamaCustomer;
        @BindView(R.id.tvNoTelp)TextView tvTipeAlat;
        @BindView(R.id.tvTime)TextView tvTime;
        @BindView(R.id.tvDescTiket)TextView tvDescTiket;
        @BindView(R.id.tvLevel)TextView tvLevel;
        @BindView(R.id.tvStatus)TextView tvStatus;
        @BindView(R.id.tvSnAlat)TextView tvSnAlat;
        public Holder(View itemView, ClickItemListener clickItemListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.clickItemListener = clickItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            TiketsPenugasanItem item = getItem(getAdapterPosition());
            this.clickItemListener.OnClickItem(item.getId(),item.getStatus());
            notifyDataSetChanged();
        }
    }
    public interface ClickItemListener{
        void OnClickItem(long id, String s);
    }
    private TiketsPenugasanItem getItem(int pos){
        return mTikets.get(pos);
    }
    public void UpdateTiket(List<TiketsPenugasanItem> items){
        mTikets = items;
        notifyDataSetChanged();
    }
}
