package catalogos;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataService {
    @GET("/json/glide.json")
    Call<List<DataModel>> getAllData();
}