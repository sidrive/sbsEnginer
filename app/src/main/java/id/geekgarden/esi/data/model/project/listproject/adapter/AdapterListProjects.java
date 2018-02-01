package id.geekgarden.esi.data.model.project.listproject.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.geekgarden.esi.data.model.project.listproject.Datum;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.geekgarden.esi.R;


/**
 * Created by setia on 30/07/2017.
 */

public class AdapterListProjects extends RecyclerView.Adapter<AdapterListProjects.Holder> {
    private List<Datum> mTikets;
    private Context mContext;
    PostItemListener postItemListener;
    public AdapterListProjects(Context context, ArrayList<Datum> tiketsItems, PostItemListener postItemListener) {
        this.mContext = context;
        this.mTikets = tiketsItems;
        this.postItemListener = postItemListener;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_project,parent,false);
        Holder holder = new Holder(view,this.postItemListener);
        return holder;
    }
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Datum tiketsItem = getItem(position);
        TextView tv03 = holder.tv03;
        tv03.setText(tiketsItem.getName());
    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PostItemListener postItemListener;
        @BindView(R.id.tv03)TextView tv03;
        public Holder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Datum tiketsItem = getItem(getAdapterPosition());
            this.postItemListener.onPostClickListener(tiketsItem.getId());
            notifyDataSetChanged();
        }
    }
    public interface PostItemListener {
        void onPostClickListener(int id);
    }
    private Datum getItem(int adptPosition){

        return mTikets.get(adptPosition);
    }

    public void UpdateTikets(List<Datum> tiketsItems){
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}
