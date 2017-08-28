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
import com.google.firebase.database.Query;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.helper.Const;
import id.geekgarden.esi.listtiket.activity.DetailConfirmedTiket;
import id.geekgarden.esi.listtiket.activity.DetailOnProgresvisitPmOther;

/**
 * Created by anddev on 27/08/2017.
 */

public class AdapterTiketFirebase extends FirebaseRecyclerAdapter<TiketsItem,FirebaseHolder>  {

    public AdapterTiketFirebase(Class<TiketsItem> modelClass, int modelLayout, Class<FirebaseHolder> viewHolderClass, DatabaseReference query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }

    @Override
    protected void populateViewHolder(FirebaseHolder viewHolder, TiketsItem model, int position) {
        viewHolder.tvNamaCustomer.setText(model.getNamaCustomer());

    }
}
