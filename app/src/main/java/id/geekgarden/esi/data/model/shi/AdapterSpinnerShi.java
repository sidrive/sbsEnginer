package id.geekgarden.esi.data.model.shi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.geekgarden.esi.R;

/**
 * Created by setia on 30/07/2017.
 */

public class AdapterSpinnerShi extends ArrayAdapter<ShiItem> {
    private LayoutInflater inflater;
    private List<ShiItem> mItem;

    public AdapterSpinnerShi(@NonNull Context context, int resource, List<ShiItem> mItem) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.mItem = mItem;
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

    @Nullable
    @Override
    public ShiItem getItem(int position) {
        return mItem.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tv= v.findViewById(R.id.tvSpinner);
        tv.setText(mItem.get(position).getShi());
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tv= v.findViewById(R.id.tvSpinner);
        tv.setText(mItem.get(position).getShi());
        return v;
    }
}
