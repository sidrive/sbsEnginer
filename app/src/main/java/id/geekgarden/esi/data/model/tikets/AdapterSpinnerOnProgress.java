package id.geekgarden.esi.data.model.tikets;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.SpinnerOnProgress.Datum;

/**
 * Created by sentinel on 9/19/17.
 */

public class AdapterSpinnerOnProgress extends ArrayAdapter{
    private LayoutInflater inflater;
    private List<Datum> mName;

    public AdapterSpinnerOnProgress(@NonNull Context context, @LayoutRes int resource, List<Datum> name) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.mName = name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = view.findViewById(R.id.tvSpinner);
        tvSpinner.setText(mName.get(position).getName());
        return view;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = view.findViewById(R.id.tvSpinner);
        tvSpinner.setText(mName.get(position).getName());
        return view;
    }

    @Override
    public int getCount() {
        return mName.size();
    }

    @Nullable
    @Override
    public Datum getItem(int position) {
        return mName.get(position);
    }
    public void UpdateOption (List<Datum> options){
        mName.addAll(options);
        notifyDataSetChanged();

    }

}
