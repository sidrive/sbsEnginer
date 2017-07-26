package id.geekgarden.esi.data.apis;

/**
 * Created by GeekGarden on 24/07/2017.
 */

public class ApiService {
  public final static String BASE_URL = "https://reqres.in/api/";
  public static Api getervice(){
    return ApiClient.getClient(BASE_URL).create(Api.class);
  }
}
