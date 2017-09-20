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
    private List<AddSparepart> mTikets;
    private Context mContext;
    OnPostItemListener onpostItemListener;

    public AdapterSparepart(ArrayList<AddSparepart> sparepartItem, Context context, OnPostItemListener onpostItemListener) {
        this.mContext = context;
        this.mTikets = sparepartItem;
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
        AddSparepart sparepartItem = getData(position);
        TextView tv01 = holder.tvpart;
        TextView tv02 = holder.tvdescription;
        TextView tv03 = holder.tvqty;
        TextView tv04 = holder.tvstatus;
        TextView tv05 = holder.tvketerangan;

        tv01.setText(sparepartItem.getPartnumber());
        tv02.setText(sparepartItem.getDescription());
        tv03.setText(sparepartItem.getQty());
        tv04.setText(sparepartItem.getStatus());
        tv05.setText(sparepartItem.getKeterangan());
    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnPostItemListener onPostItemListener;
        @BindView(R.id.tvpart)
        TextView tvpart;
        @BindView(R.id.tvdescription)
        TextView tvdescription;
        @BindView(R.id.tvqty)
        TextView tvqty;
        @BindView(R.id.tvstatus)
        TextView tvstatus;
        @BindView(R.id.tvketerangan)
        TextView tvketerangan;

        public Holder(View itemView, OnPostItemListener onpostItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onPostItemListener = onpostItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            AddSparepart addSparepart = getData(getAdapterPosition());
            this.onPostItemListener.onPostClickListener(addSparepart.getPartnumber());
        }
    }

    public interface OnPostItemListener {
        void onPostClickListener(String partnumber);
    }

    private AddSparepart getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<AddSparepart> sparepartItem) {
        mTikets = sparepartItem;
        notifyDataSetChanged();
    }
}
