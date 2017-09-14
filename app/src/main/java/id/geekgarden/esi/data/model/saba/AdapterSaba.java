package id.geekgarden.esi.data.model.saba;

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
import id.geekgarden.esi.data.model.saba.getsaba.Datum;


public class AdapterSaba extends RecyclerView.Adapter<AdapterSaba.Holder> {

    private List<Datum> mSaba;
    private Context mContext;
    PostItemListener postItemListener;

    public AdapterSaba(ArrayList<Datum> SabaItem, Context context, PostItemListener postItemListener) {
        this.mContext = context;
        this.mSaba = SabaItem;
        this.postItemListener = postItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_saba, parent, false);
        Holder holder = new Holder(view, this.postItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datum SabaAct = getItem(position);
        TextView tv01 = holder.tvstarttime;
        TextView tv02 = holder.tvendtime;
        TextView tv03 = holder.tvdesc;
        if (SabaAct.getEndedAt().getDate().isEmpty()){
            tv02.setText("Belum End");
        }else{
            tv02.setText(SabaAct.getEndedAt().getDate());
        }
        tv01.setText(SabaAct.getCreatedAt().getDate());
        /*tv02.setText(SabaAct.getEndedAt().getDate());*/
        tv03.setText(SabaAct.getDescription());
    }

    @Override
    public int getItemCount() {
        return mSaba.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PostItemListener postItemListener;
        @BindView(R.id.tvstarttime)
        TextView tvstarttime;
        @BindView(R.id.tvendtime)
        TextView tvendtime;
        @BindView(R.id.tvdesc)
        TextView tvdesc;

        public Holder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datum datum = getItem(getAdapterPosition());
            this.postItemListener.onPostClickListener(datum.getId());
            notifyDataSetChanged();
        }
    }

    public interface PostItemListener {
        void onPostClickListener(long id);
    }

    private Datum getItem(int adptPosition) {

        return mSaba.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> SabaItem) {
        mSaba = SabaItem;
        notifyDataSetChanged();
    }
}
