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
import id.geekgarden.esi.listtiket.activity.AddSparepart;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterSparepart extends RecyclerView.Adapter<AdapterSparepart.Holder> {
    private List<SQLiteSparepart> msparepart;
    private Context mContext;
    OnPostItemListener onpostItemListener;

    public AdapterSparepart(ArrayList<SQLiteSparepart> sparepartItem, Context context, OnPostItemListener onpostItemListener) {
        this.mContext = context;
        this.msparepart = sparepartItem;
        this.onpostItemListener = onpostItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_sparepart, parent, false);
        Holder holder = new Holder(view, this.onpostItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        SQLiteSparepart sparepartItem = getData(position);
        TextView tv01 = holder.tvTipeAlat;
        TextView tv02 = holder.tvDesc;
        TextView tv03 = holder.tvQuantity;
        TextView tv04 = holder.tvstatus;
        TextView tv05 = holder.tvremarks;

        tv01.setText(sparepartItem.getPartnumber());
        tv02.setText(sparepartItem.getDescription());
        tv03.setText(sparepartItem.getQty());
        tv04.setText(sparepartItem.getStatus());
        tv05.setText(sparepartItem.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return msparepart.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnPostItemListener onPostItemListener;
        @BindView(R.id.tvTipeAlat)
        TextView tvTipeAlat;
        @BindView(R.id.tvQuantity)
        TextView tvQuantity;
        @BindView(R.id.tvDesc)
        TextView tvDesc;
        @BindView(R.id.tvremarks)
        TextView tvremarks;
        @BindView(R.id.tvstatus)
        TextView tvstatus;
        public Holder(View itemView, OnPostItemListener onpostItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onPostItemListener = onpostItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SQLiteSparepart addSparepart = getData(getAdapterPosition());
            this.onPostItemListener.onPostClickListener(addSparepart.getPartnumber());
        }
    }

    public interface OnPostItemListener {
        void onPostClickListener(String partnumber);
    }

    private SQLiteSparepart getData(int adptPosition) {
        return msparepart.get(adptPosition);
    }

    public void UpdateTikets(List<SQLiteSparepart> sparepartItem) {
        msparepart = sparepartItem;
        notifyDataSetChanged();
    }
}
