package id.geekgarden.esi.data.model.tikets.staffticket.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.staffticket.model.getnotifications.Datumnotif;

/**
 * Created by ikun on 24/01/18.
 */

public class AdapterNotifications extends RecyclerView.Adapter<AdapterNotifications.Holder> {

    private List<Datumnotif> mNotif;
    private Context mContext;
    PostItemListener postItemListener;

    public AdapterNotifications(ArrayList<Datumnotif> NotifItem, Context context, PostItemListener postItemListener) {
        this.mContext = context;
        this.mNotif = NotifItem;
        this.postItemListener = postItemListener;
    }

    @Override
    public AdapterNotifications.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_notifications, parent, false);
        AdapterNotifications.Holder holder = new AdapterNotifications.Holder(view, this.postItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datumnotif NotifAct = getItem(position);
        TextView tvtitle = holder.tvtitle;
        TextView tvmessage = holder.tvmessage;

        tvtitle.setText(NotifAct.getTitle());
        Log.e("AdapterNotifications", "onBindViewHolder: " + NotifAct.getTitle());
        tvmessage.setText(NotifAct.getMessage());
    }

    @Override
    public int getItemCount() {
        return mNotif.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PostItemListener postItemListener;
        @BindView(R.id.tvtitle)
        TextView tvtitle;
        @BindView(R.id.tvdescription)
        TextView tvmessage;

        public Holder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datumnotif datumnotif = getItem(getAdapterPosition());
//            this.postItemListener.onPostClickListener(datumnotif.getTitle());
            notifyDataSetChanged();
        }
    }

    public interface PostItemListener {
        void onPostClickListener(long id);
    }

    private Datumnotif getItem(int adptPosition) {

        return mNotif.get(adptPosition);
    }

    public void UpdateTikets(List<Datumnotif> notifItem) {
        mNotif = notifItem;
        notifyDataSetChanged();
    }

}
