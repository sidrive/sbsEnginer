package id.geekgarden.esi.data.model.tikets;

import com.ahamed.multiviewadapter.SelectableAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import id.geekgarden.esi.data.model.reocurrence.Datum;

/**
 * Created by raka on 10/9/17.
 */

public class AdapterSelectable extends SelectableAdapter {

    private ArrayList<Datum> dataManager;

    public AdapterSelectable() {
        setSelectionMode(SELECTION_MODE_SINGLE);

        this.dataManager = new ArrayList<>();
        addDataManager(dataManager);

        registerBinder(new AdapterSelectable());
    }

    private void registerBinder(AdapterSelectable adapterSelectable) {

    }

    private void addDataManager(ArrayList<Datum> dataManager) {

    }

    public void addData(List<Datum> dataList) {
        dataManager.addAll(dataList);
    }
}
