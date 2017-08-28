package id.geekgarden.esi.data.model;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

public class AdapterSabaFirebase extends FirebaseRecyclerAdapter<SabaItem,SabaFirebaseHolder>{

    public AdapterSabaFirebase(Class<SabaItem> modelClass, int modelLayout, Class<SabaFirebaseHolder> viewHolderClass, DatabaseReference query) {
        super(modelClass, modelLayout, viewHolderClass, query);
    }
    protected void populateViewHolder(SabaFirebaseHolder viewHolder, SabaItem model, int position) {
        viewHolder.tvActivity.setText(model.getActivity());

    }
}
