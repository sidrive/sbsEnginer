package id.geekgarden.esi.data.model.tikets;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import id.geekgarden.esi.R;

/**
 * Created by anddev on 27/08/2017.
 */

public class AdapterTiket extends RecyclerView.Adapter<AdapterTiket.Holder>{

    private List<ResponseTikets> mTikets;
    private Context mContext;
    AdapterTiket.PostItemListener postItemListener;

    public AdapterTiket(Context context, ArrayList<ResponseTikets> tiketsItems, PostItemListener postItemListener){
        this.mContext = context;
        this.mTikets = tiketsItems;
        this.postItemListener = postItemListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_list_tiket,parent,false);
        Holder holder = new Holder(view,this.postItemListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
    ResponseTikets tiketsItem = getItem(position);
    TextView tv01 = holder.tv01;
    TextView tv02 = holder.tv02;
    TextView tv03 = holder.tv03;

    tv01.setText();

    }

    @Override
    public int getItemCount()  {
        return mTikets.size();
    }



    public class Holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        PostItemListener postItemListener;
        public Holder(View itemView, PostItemListener postItemListener) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            this.postItemListener = postItemListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ResponseTikets tiketsItem = getItem(getAdapterPosition());
            this.postItemListener.onPostClickListener(tiketsItem.getData().get(getAdapterPosition()).getId(),
                    tiketsItem.getData().get(getAdapterPosition()).getStatus());
            notifyDataSetChanged();
        }
    }
    public interface PostItemListener {
        void onPostClickListener(long id, String status);

    }

    private ResponseTikets getItem(int adapterPosition) {
        return mTikets.get(adapterPosition);
    }


}
