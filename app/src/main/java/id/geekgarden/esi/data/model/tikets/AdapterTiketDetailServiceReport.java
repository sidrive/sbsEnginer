package id.geekgarden.esi.data.model.tikets;

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
import id.geekgarden.esi.data.model.tikets.servicereport.Datum;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum_;


/**
 * Created by komuri on 06/09/2017.
 */


public class AdapterTiketDetailServiceReport extends RecyclerView.Adapter<AdapterTiketDetailServiceReport.Holder> {
    final int VIEW_TYPE_SERVICEREPORT = 0;
    final int VIEW_TYPE_PART = 1;

    Context context;
    List<Datum> datumList;
    List<Datum_> partList;


    public AdapterTiketDetailServiceReport(Context context, List<Datum_> listarray, List<Datum> listarray1) {
        this.context = context;
        this.datumList = listarray1;
        this.partList = listarray;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_list_servicereport, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datum listarray1 = getData(position);
    }

    @Override
    public int getItemCount() {
        return 0;
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

    private Datum getData(int adptPosition) {
        return datumList.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> listarray1, List<Datum_> listarray) {
        datumList = listarray1;
        partList = listarray;
        notifyDataSetChanged();

    }
}

