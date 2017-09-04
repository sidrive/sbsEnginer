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


/**
 * Created by setia on 30/07/2017.
 */

public class AdapterListProjects extends RecyclerView.Adapter<AdapterListProjects.Holder> {
    private List<ResponseTikets> mTikets;
    private Context mContext;
    PostItemListener postItemListener;

    public AdapterListProjects(Context context, ArrayList<ResponseTikets> tiketsItems, PostItemListener postItemListener) {
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
        TiketsItem tiketsItem = getItem(position);
        TextView tv01 = holder.tv01;
        TextView tv02 = holder.tv02;
        TextView tv03 = holder.tv03;


        tv01.setText(tiketsItem.get());
        tv02.setText(tiketsItem.getNamaCustomer());
        tv03.setText(tiketsItem.getDescripsiton());


    }

    @Override
    public int getItemCount() {
        return mTikets.size();
    }

    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        PostItemListener postItemListener;
        @BindView(R.id.tv01)TextView tv01;
        @BindView(R.id.tv02)TextView tv02;
        @BindView(R.id.tv03)TextView tv03;

        public Holder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TiketsItem tiketsItem = getItem(getAdapterPosition());
            this.postItemListener.onPostClickLsitener(tiketsItem.getId(),tiketsItem.getStatus());
            notifyDataSetChanged();
        }
    }
    public interface PostItemListener {
        void onPostClickLsitener(long id, String status);
    }
    private TiketsItem getItem(int adptPosition){
        return mTikets.get(adptPosition);
    }
    public void UpdateTikets(List<TiketsItem> tiketsItems){
        mTikets = tiketsItems;
        notifyDataSetChanged();
    }
}
