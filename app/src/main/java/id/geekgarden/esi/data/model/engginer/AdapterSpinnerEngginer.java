package id.geekgarden.esi.data.model.engginer;

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

public class AdapterSpinnerEngginer extends ArrayAdapter<EngginerItem> {

    private LayoutInflater inflater;
    private List<EngginerItem> mItem;

    public AdapterSpinnerEngginer(@NonNull Context context, int resource, List<EngginerItem> mItem) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.mItem = mItem;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = view.findViewById(R.id.tvSpinner);
        tvSpinner.setText(mItem.get(position).getName());
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = view.findViewById(R.id.tvSpinner);
        tvSpinner.setText(mItem.get(position).getName());
        return view;
    }

    @Override
    public long getItemId(int position) {
        return mItem.get(position).getId();
    }

    @Nullable
    @Override
    public EngginerItem getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

}
