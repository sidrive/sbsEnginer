package id.geekgarden.esi.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

public class ModelSabaActivity {
    public ModelSabaActivity(){}
    private String Activity;

    public ModelSabaActivity(String activity) {
        Activity = activity;
    }

    public String getActivity() {
        return Activity;
    }

    public void setActivity(String activity) {
        Activity = activity;
    }
}
