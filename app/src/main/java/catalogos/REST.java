package catalogos;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class REST {
    private Request request;
    private static final String BASE_URL = "http://api.androidhive.info";
    public Request getFlowerService() {
        if(request == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            request = retrofit.create(Request.class);
        }
        return request;
    }
}
