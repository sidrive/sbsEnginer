package id.geekgarden.esi.data.model.tikets.staffticket.model.part;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import id.geekgarden.esi.R;
import id.geekgarden.esi.data.model.tikets.staffticket.model.part.partstatus.Datum;
import java.util.List;

/**
 * Created by sentinel on 9/19/17.
 */

public class AdapterSpinnerPartStatus extends ArrayAdapter<id.geekgarden.esi.data.model.tikets.staffticket.model.part.partstatus.Datum> {
    private LayoutInflater inflater;
    private List<Datum> datumList;
    public AdapterSpinnerPartStatus(@NonNull Context context, @LayoutRes int resource, List<Datum> list) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.datumList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = view.findViewById(R.id.tvSpinner);
        tvSpinner.setText(datumList.get(position).getName());
        return view;

    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = view.findViewById(R.id.tvSpinner);
        tvSpinner.setText(datumList.get(position).getName());
        return view;
    }

    @Override
    public int getCount() {
        return datumList.size();
    }

    @Nullable
    @Override
    public Datum getItem(int position) {
        return datumList.get(position);
    }
    public void UpdateOption (List<Datum> options){
        datumList.addAll(options);
        notifyDataSetChanged();

    }
}
