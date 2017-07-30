package id.geekgarden.esi.data.model.kode_kegiatan;

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

public class AdapterSpinnerKegiatan extends ArrayAdapter<KodeKegiatanItem> {
    private LayoutInflater inflater;
    private List<KodeKegiatanItem> mItem;

    public AdapterSpinnerKegiatan(@NonNull Context context, int resource, List<KodeKegiatanItem> mItem) {
        super(context, resource);
        this.inflater = LayoutInflater.from(context);
        this.mItem = mItem;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = v.findViewById(R.id.tvSpinner);
        tvSpinner.setText(mItem.get(position).getKegiatan());
        return v;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = inflater.inflate(R.layout.item_spinner,parent,false);
        TextView tvSpinner = v.findViewById(R.id.tvSpinner);
        tvSpinner.setText(mItem.get(position).getKegiatan());
        return v;
    }

    @Nullable
    @Override
    public KodeKegiatanItem getItem(int position) {
        return mItem.get(position);
    }

    @Override
    public int getCount() {
        return mItem.size();
    }

}
