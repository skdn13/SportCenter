package pt.ipp.estg.sportcenter;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pmms8 on 1/7/2018.
 */

public class RestManager {
    private DataService dataService;
    public static final String BASE_URL = "http://api.androidhive.info";
    public DataService getFlowerService() {
        if(dataService == null){
            //Retrofit setup
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            //Service setup
            dataService = retrofit.create(DataService.class);
        }
        return dataService;
    }
}
