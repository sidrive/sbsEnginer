package id.geekgarden.esi.data.model.tikets.staffticket.model.getnotifications;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ikun on 24/01/18.
 */

public class Datumnotif {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Datumnotif{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
