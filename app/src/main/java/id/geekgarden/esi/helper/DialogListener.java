package id.geekgarden.esi.helper;

import android.content.DialogInterface;

/**
 * Created by raka on 10/13/17.
 */

public interface DialogListener {
  void dialogPositif(DialogInterface dialogInterface,String [] permission);
  void dialogNegative(DialogInterface dialogInterface);
  void dialogSetting(DialogInterface dialogInterface);

}
