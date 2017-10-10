package id.geekgarden.esi.data.model.tikets;

import android.widget.LinearLayout;

/**
 * Created by raka on 10/9/17.
 */

public interface Selectable {

    void ChangeBackground (LinearLayout ll, int position, int selected_position);
    void ChangeBackgroundTransparent (LinearLayout ll, int position, int selected_position);



}
