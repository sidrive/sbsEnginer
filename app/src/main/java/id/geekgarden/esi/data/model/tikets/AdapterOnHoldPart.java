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
import id.geekgarden.esi.data.model.tikets.part.Datum;

/**
 * Created by komuri on 06/09/2017.
 */

public class AdapterOnHoldPart extends RecyclerView.Adapter<AdapterOnHoldPart.Holder> {
    private List<Datum> mTikets;
    private Context mContext;

    public AdapterOnHoldPart(ArrayList<Datum> serviceitem, Context context) {
        this.mContext = context;
        this.mTikets = serviceitem;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_part, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datum tiketsItem = getData(position);
        TextView tv01 = holder.tvTipeAlat;
        TextView tv02 = holder.tvQuantity;
        TextView tv03 = holder.tvDesc;
        TextView tv04 = holder.tvremarks;
        TextView tv05 = holder.tvstatus;

        tv01.setText(tiketsItem.getPartNumber());
        tv02.setText(tiketsItem.getQuantity());
        tv03.setText(tiketsItem.getDescription());
        tv04.setText(tiketsItem.getRemarks());
        tv05.setText(tiketsItem.getStatus());
    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
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
        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> listarray1) {
        mTikets = listarray1;
        notifyDataSetChanged();
    }
}
