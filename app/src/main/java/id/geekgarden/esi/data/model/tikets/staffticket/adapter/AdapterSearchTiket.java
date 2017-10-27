package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

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
import id.geekgarden.esi.data.model.tikets.staffticket.model.searchtiket.Datum;


public class AdapterSearchTiket extends RecyclerView.Adapter<AdapterSearchTiket.Holder> {
    private List<Datum> mTikets;
    private Context mContext;
    OnTiketPostItemListener ontiketpostItemListener;

    public AdapterSearchTiket(ArrayList<Datum> tiketsItems, Context context, OnTiketPostItemListener ontiketpostItemListener) {
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

        holder.tvNamaCustomer.setText(tiketsItem.getCustomerName());
        holder.tvSnAlat.setText(tiketsItem.getInstrument().getData().getSerialNumber());
        holder.tvNumber.setText(tiketsItem.getNumber());
        holder.tvTime.setText(tiketsItem.getCreatedAt().getDate());
        holder.tvDescTiket.setText(tiketsItem.getDescription());
        holder.tvStatus.setText(tiketsItem.getPriority());
        holder.tvStatus.setText(tiketsItem.getInstrument().getData().getType());
        holder.tvTipeAlat.setText(tiketsItem.getStatusText());
        holder.tvtickettype.setText(tiketsItem.getTicketType().getData().getName());
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
        @BindView(R.id.tvstatustiket)
        TextView tvstatustiket;
        @BindView(R.id.tvtickettype)
        TextView tvtickettype;
        public Holder(View itemView, OnTiketPostItemListener ontiketpostItemListener) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onTiketPostItemListener = ontiketpostItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datum datum = getData(getAdapterPosition());
            this.onTiketPostItemListener.onPostClickListener(datum.getId(), datum.getStatus(), datum.getCustomer().getData().getId(), datum.getTicketType().getData().getId());
            notifyDataSetChanged();
        }
    }

    public interface OnTiketPostItemListener {
        void onPostClickListener(int id, String status, int id_customer, int ticket_type);
    }

    private Datum getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> tiketsItems) {
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}
