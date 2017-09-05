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

public class AdapterTiketNew extends RecyclerView.Adapter<AdapterTiketNew.Holder> {
    private List<Datum> mTikets;
    private Context mContext;
    OnTiketPostItemListener ontiketpostItemListener;

    public AdapterTiketNew(ArrayList<Datum> tiketsItems, Context context, OnTiketPostItemListener ontiketpostItemListener) {
        this.mContext = context;
        this.mTikets = tiketsItems;
        this.ontiketpostItemListener = ontiketpostItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_tiket, parent, false);
        Holder holder = new Holder(view, this.ontiketpostItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datum tiketsItem = getData(position);
        TextView tv01 = holder.tvNamaCustomer;
        TextView tv02 = holder.tvSnAlat;
        TextView tv03 = holder.tvNumber;
        TextView tv04 = holder.tvTime;
        TextView tv05 = holder.tvDescTiket;
        TextView tv06 = holder.tvStatus;
        TextView tv07 = holder.tvTipeAlat;


        tv01.setText(tiketsItem.getStaffName());
        tv02.setText(tiketsItem.getInstrument().getData().getSerialNumber());
        tv03.setText(tiketsItem.getNumber());
        tv04.setText(tiketsItem.getCreatedAt().getDate());
        tv05.setText(tiketsItem.getDescription());
        tv06.setText(tiketsItem.getPriority());
        tv07.setText(tiketsItem.getInstrument().getData().getType());
        /*ResponseTikets tiketsItem = getItem(position);
        TextView tv01 = holder.tv01;
        TextView tv02 = holder.tv02;
        TextView tv03 = holder.tv03;


        tv01.setText(tiketsItem.get());
        tv02.setText(tiketsItem.getNamaCustomer());
        tv03.setText(tiketsItem.getDescripsiton());*/


    }

    @Override
    public int getItemCount() {

        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTiketPostItemListener onTiketPostItemListener;
        @BindView(R.id.tvNamaCustomer)
        TextView tvNamaCustomer;
        @BindView(R.id.tvTipeAlat)
        TextView tvTipeAlat;
        @BindView(R.id.tvNumber)
        TextView tvNumber;
        @BindView(R.id.tvTime)
        TextView tvTime;
        @BindView(R.id.tvDescTiket)
        TextView tvDescTiket;
        @BindView(R.id.tvSnAlat)
        TextView tvSnAlat;
        @BindView(R.id.tvStatus)
        TextView tvStatus;

        public Holder(View itemView, OnTiketPostItemListener ontiketpostItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onTiketPostItemListener = ontiketpostItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           /* TiketsItem tiketsItem = getItem(getAdapterPosition());
            this.postItemListener.onPostClickLsitener(tiketsItem.getId(),tiketsItem.getStatus());*/
            notifyDataSetChanged();
        }
    }

    public interface OnTiketPostItemListener {
        void onPostClickListener(long id, String status);
    }

    private Datum getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> tiketsItems) {
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}
