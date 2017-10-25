package id.geekgarden.esi.data.model.tikets.supervisorticket;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.ticket.Datum;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterTiketOnProgressNewSpv extends RecyclerView.Adapter<AdapterTiketOnProgressNewSpv.Holder> {
    private List<Datum> mTikets;
    private Context mContext;
    AdapterTiketOnProgressNewSpv.OnTiketPostItemListener ontiketpostItemListener;

    public AdapterTiketOnProgressNewSpv(ArrayList<Datum> tiketsItems, Context context, AdapterTiketOnProgressNewSpv.OnTiketPostItemListener ontiketpostItemListener) {
        this.mContext = context;
        this.mTikets = tiketsItems;
        this.ontiketpostItemListener = ontiketpostItemListener;
    }

    @Override
    public AdapterTiketOnProgressNewSpv.Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_tiket, parent, false);
        AdapterTiketOnProgressNewSpv.Holder holder = new AdapterTiketOnProgressNewSpv.Holder(view, this.ontiketpostItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(AdapterTiketOnProgressNewSpv.Holder holder, int position) {
        Datum tiketsItem = getData(position);
        TextView tv01 = holder.tvNamaCustomer;
        TextView tv02 = holder.tvSnAlat;
        TextView tv03 = holder.tvNumber;
        TextView tv04 = holder.tvTime;
        TextView tv05 = holder.tvDescTiket;
        TextView tv06 = holder.tvStatus;
        TextView tv07 = holder.tvTipeAlat;
        TextView tv08 = holder.tvtickettype;


        tv01.setText(tiketsItem.getCustomerName());
        tv02.setText(tiketsItem.getInstrument().getData().getSerialNumber());
        tv03.setText(tiketsItem.getNumber());
        tv04.setText(tiketsItem.getCreatedAt().getDate());
        tv05.setText(tiketsItem.getDescription());
        tv06.setText(tiketsItem.getPriority());
        tv07.setText(tiketsItem.getInstrument().getData().getType());
        tv08.setText(tiketsItem.getTicketType().getData().getName());
    }

    @Override
    public int getItemCount() {

        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        AdapterTiketOnProgressNewSpv.OnTiketPostItemListener onTiketPostItemListener;
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
        @BindView(R.id.tvtickettype)
        TextView tvtickettype;

        public Holder(View itemView, AdapterTiketOnProgressNewSpv.OnTiketPostItemListener ontiketpostItemListener) {

            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onTiketPostItemListener = ontiketpostItemListener;
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            Datum datum = getData(getAdapterPosition());
            this.onTiketPostItemListener.onPostClickListener(datum.getId(),datum.getCustomer().getData().getId());
        }

    }

    public interface OnTiketPostItemListener {
        void onPostClickListener(int id, int id_customer);
    }

    private Datum getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> tiketsItems) {
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}