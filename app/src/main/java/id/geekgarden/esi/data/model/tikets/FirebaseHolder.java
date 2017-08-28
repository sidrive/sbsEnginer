package id.geekgarden.esi.data.model.tikets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;

/**
 * Created by komuri on 28/08/2017.
 */

public class FirebaseHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.tvNamaCustomer)
    TextView tvNamaCustomer;
    public FirebaseHolder(View itemView) {
        super(itemView);  ButterKnife.bind(this,itemView);
    }
}
