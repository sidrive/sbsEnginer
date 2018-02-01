
package id.geekgarden.esi.data.model.smom.detailsmom;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("filename")
    @Expose
    private String filename;
    @SerializedName("path")
    @Expose
    private String path;
    @SerializedName("mimes")
    @Expose
    private String mimes;
    @SerializedName("uri")
    @Expose
    private String uri;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMimes() {
        return mimes;
    }

    public void setMimes(String mimes) {
        this.mimes = mimes;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
