package id.geekgarden.esi.data.model;

/**
 * Created by anddev on 27/08/2017.
 */

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
