package id.geekgarden.esi.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by raka on 25/12/17.
 */

public class ResponseError {
  @SerializedName("code")
  private int code;

  @SerializedName("message")
  private String message;

  @SerializedName("status")
  private boolean status;
  @SerializedName("data")
  private String data;

  public ResponseError(int code, String message, boolean status, String data) {
    this.code = code;
    this.message = message;
    this.status = status;
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }

  @Override
  public String toString(){
    return
        "ResponseErrors{" +
            "code = '" + code + '\'' +
            ",message = '" + message + '\'' +
            ",status = '" + status + '\'' +
            "}";
  }
}
