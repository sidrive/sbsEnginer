package id.geekgarden.esi.data.model.tikets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum;
import id.geekgarden.esi.data.model.tikets.servicereport.Datum_;
import id.geekgarden.esi.listtiket.activitymyticket.DetailOnHold;


/**
 * Created by komuri on 06/09/2017.
 */


public class AdapterTiketDetailServiceReport extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    final int VIEW_TYPE_SERVICEREPORT = 0;
    final int VIEW_TYPE_PART = 1;

    Context context;
    List<Datum> servicereport;
    List<Datum_> partlist;

    public AdapterTiketDetailServiceReport(Context context, List<Datum_> listarray, List<Datum> listarray1) {
        this.context = context;
        this.servicereport = listarray1;
        this.partlist = listarray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        return
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
