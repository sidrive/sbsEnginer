package id.geekgarden.esi.data.model.tikets;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.ObservableSnapshotArray;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.helper.Const;
import id.geekgarden.esi.listtiket.activity.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activity.DetailOnProgresvisitPmOther;

/**
 * Created by anddev on 27/08/2017.
 */

public class AdapterTiketFirebase extends FirebaseRecyclerAdapter<TiketsItem,AdapterTiketFirebase.Holder>  {
    String key;
    private Context mContext;

    public AdapterTiketFirebase(ObservableSnapshotArray<TiketsItem> dataSnapshots, int modelLayout, Class<Holder> viewHolderClass, Context context) {
        super(dataSnapshots, modelLayout, viewHolderClass);
        this.mContext = context;

    }

    @Override
    protected void populateViewHolder(Holder viewHolder, TiketsItem model, final int position) {
        DatabaseReference mRef = getRef(position);
        final String key = mRef.getKey();
        final String status  = model.getStatus();
        viewHolder.tvNamaCustomer.setText(model.getNamaCustomer());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (status.equals(Const.Status_Confirm)){
                    Intent i = new Intent(mContext,DetailConfirmedTiket.class);
                    i.putExtra(DetailConfirmedTiket.EXTRA_KEY,key);
                    mContext.startActivity(i);
                }if (status.equals(Const.Status_OnProgress)){
                    Intent i = new Intent(mContext,DetailOnProgresvisitPmOther.class);
                    i.putExtra(Const.EXTRA_KEY,key);
                    mContext.startActivity(i);
                }
            }
        });
    }



    public class Holder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvNamaCustomer)
        TextView tvNamaCustomer;
        public Holder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
