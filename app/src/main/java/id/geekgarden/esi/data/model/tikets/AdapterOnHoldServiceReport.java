package id.geekgarden.esi.data.model.tikets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterOnHoldServiceReport extends RecyclerView.Adapter<AdapterOnHoldServiceReport.Holder> {
    private List<Datum> mTikets;
    private Context mContext;
    OnTiketPostItemListener ontiketpostItemListener;

    public AdapterOnHoldServiceReport(ArrayList<Datum> serviceitem, Context context, OnTiketPostItemListener ontiketpostItemListener) {
        this.mContext = context;
        this.mTikets = serviceitem;
        this.ontiketpostItemListener = ontiketpostItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_servicereport, parent, false);
        Holder holder = new Holder(view, this.ontiketpostItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datum tiketsItem = getData(position);
        TextView tv01 = holder.tvactivity;
        TextView tv02 = holder.tvproblem;
        TextView tv03 = holder.tvfault;
        TextView tv04 = holder.tvactionsolution;

        tv01.setText(tiketsItem.getName());
        tv02.setText(tiketsItem.getProblem());
        tv03.setText(tiketsItem.getFaultDescription());
        tv04.setText(tiketsItem.getSolution());
    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnTiketPostItemListener onTiketPostItemListener;
        @BindView(R.id.tvactivity)
        TextView tvactivity;
        @BindView(R.id.tvproblem)
        TextView tvproblem;
        @BindView(R.id.tvfault)
        TextView tvfault;
        @BindView(R.id.tvactionsolution)
        TextView tvactionsolution;
        public Holder(View itemView, OnTiketPostItemListener ontiketpostItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.onTiketPostItemListener = ontiketpostItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datum datum = getData(getAdapterPosition());
            Log.e("", "onClick: "+datum.getId ());
            this.onTiketPostItemListener.onPostClickListener(datum.getId());
        }
    }

    public interface OnTiketPostItemListener {
        void onPostClickListener(int id);
    }

    private Datum getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> listarray1) {
        mTikets = listarray1;
        notifyDataSetChanged();
    }
}
