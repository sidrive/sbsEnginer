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
import id.geekgarden.esi.data.model.tikets.detailticket.Part;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterTiketDetailPart extends RecyclerView.Adapter<AdapterTiketDetailPart.Holder> {
    private List<Part> mTikets;
    private Context mContext;

    public AdapterTiketDetailPart(ArrayList<Part> tiketsItems, Context context) {
        this.mContext = context;
        this.mTikets = tiketsItems;
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
        Part tiketsItem = getData(position);
        TextView tv05 = holder.tvTipeAlat;
        TextView tv06 = holder.tvQuantity;
        TextView tv07 = holder.tvDesc;
        TextView tv08 = holder.tvremarks;
        TextView tv09 = holder.tvstatus;

        tv05.setText(tiketsItem.getPartNumber());
        tv06.setText(tiketsItem.getQuantity());
        tv07.setText(tiketsItem.getDescription());
        tv08.setText(tiketsItem.getRemarks());
        tv09.setText(tiketsItem.getStatus());
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

    private Part getData(int adptPosition) {
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Part> tiketsItems) {
//        mTikets = tiketsItems;
//
//        notifyDataSetChanged();
        for (int i = 0; i < tiketsItems.size(); i++) {

        }
    }
}
