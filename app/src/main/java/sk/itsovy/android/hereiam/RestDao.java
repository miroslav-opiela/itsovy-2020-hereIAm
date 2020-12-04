package sk.itsovy.android.hereiam;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestDao {

    String URL = "https://ics.upjs.sk/~opiela/rest/index.php/";

    Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RestDao API = RETROFIT.create(RestDao.class);

    @POST("available-users/{login}")
    Call<Void> sendLogin(@Path("login") String login);

    @GET("available-users")
    Call<List<User>> getUsers();


}
