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
import id.geekgarden.esi.ServiceReportListener;
import id.geekgarden.esi.data.model.tikets.detailticket.ServiceReport;

/**
 * Created by komuri on 06/09/2017.
 */


public class AdapterTiketDetailServiceReport extends RecyclerView.Adapter<AdapterTiketDetailServiceReport.Holder> {
    private List<ServiceReport> mTikets;
    private Context mContext;
    private ServiceReportListener listener;
    public AdapterTiketDetailServiceReport(List<ServiceReport> tiketsItems, Context context, ServiceReportListener listener) {
        this.mContext = context;
        this.mTikets = tiketsItems;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_servicereport, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        ServiceReport tiketsItem = getData(position);
        TextView tv01 = holder.tvactivity;
        TextView tv02 = holder.tvproblem;
        TextView tv03 = holder.tvfault;
        TextView tv04 = holder.tvactionsolution;

        tv01.setText(tiketsItem.getData().getName());
        tv02.setText(tiketsItem.getData().getProblem());
        tv03.setText(tiketsItem.getData().getFaultDescription());
        tv04.setText(tiketsItem.getData().getSolution());
        if (tiketsItem.getData().getPart().size() != 0) {
            listener.loadpart(tiketsItem.getData().getId());
        }
    }

    @Override
    public int getItemCount() {

        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvactivity)
        TextView tvactivity;
        @BindView(R.id.tvproblem)
        TextView tvproblem;
        @BindView(R.id.tvfault)
        TextView tvfault;
        @BindView(R.id.tvactionsolution)
        TextView tvactionsolution;
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


        public Holder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    private ServiceReport getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<ServiceReport> tiketsItems) {
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}
