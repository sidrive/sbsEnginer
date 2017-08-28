package id.geekgarden.esi.data.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;

/**
 * Created by SENTINEL on 2017/08/28.
 */

public class SabaFirebaseHolder extends RecyclerView.ViewHolder{
    @BindView(R.id.tvActivity)
    TextView tvActivity;
    public SabaFirebaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
}
