package id.geekgarden.esi.data.apis;

import retrofit2.Response;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public interface Api {
  @GET("posts/")
  Observable<Response> getPost();
}
