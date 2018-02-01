package id.geekgarden.esi.data.model.smom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.smom.detailsmom.Datum;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakasn on 24/01/18.
 */

public class AdapterSmOmDetail extends RecyclerView.Adapter<AdapterSmOmDetail.Holder> {
private List<Datum> mTikets;
private Context mContext;
    OnTiketPostItemListener ontiketpostItemListener;

public AdapterSmOmDetail(ArrayList<Datum> tiketsItems, Context context,
    OnTiketPostItemListener ontiketpostItemListener) {
    this.mContext = context;
    this.mTikets = tiketsItems;
    this.ontiketpostItemListener = ontiketpostItemListener;
    }

@Override
public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(mContext);
    View view = inflater.inflate(R.layout.item_list_smom, parent, false);
    Holder holder = new Holder(view, this.ontiketpostItemListener);
    return holder;
    }

@Override
public void onBindViewHolder(Holder holder, int position) {
    Datum tiketsItem = getData(position);
    TextView tv01 = holder.tvTipeAlat;
    tv01.setText(tiketsItem.getFilename());
    }

@Override
public int getItemCount() {
    return mTikets.size();
    }

public class Holder extends ViewHolder implements OnClickListener {
  @BindView(R.id.tvTipeAlat)
  TextView tvTipeAlat;
  OnTiketPostItemListener onTiketPostItemListener;
  public Holder(View itemView, OnTiketPostItemListener ontiketpostItemListener) {
    super(itemView);
    ButterKnife.bind(this, itemView);
    this.onTiketPostItemListener = ontiketpostItemListener;
    itemView.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    Datum datum = getData(getAdapterPosition());
    this.onTiketPostItemListener.onPostClickListener(datum.getId());
    notifyDataSetChanged();
  }
}

public interface OnTiketPostItemListener {
  void onPostClickListener(int id);
}

  private Datum getData(int adptPosition) {
    return mTikets.get(adptPosition);
  }

  public void UpdateTikets(List<Datum> tiketsItems) {
    mTikets = tiketsItems;
    notifyDataSetChanged();
  }
}
