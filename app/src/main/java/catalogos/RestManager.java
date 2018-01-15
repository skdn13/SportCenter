package catalogos;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {
    private DataService dataService;
    private static final String BASE_URL = "http://api.androidhive.info";
    public DataService getFlowerService() {
        if(dataService == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            dataService = retrofit.create(DataService.class);
        }
        return dataService;
    }
}
