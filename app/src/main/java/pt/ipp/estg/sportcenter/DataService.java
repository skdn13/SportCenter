package pt.ipp.estg.sportcenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by pmms8 on 1/7/2018.
 */

public interface DataService {
    @GET("/json/glide.json")
    Call<List<DataModel>> getAllData();
}