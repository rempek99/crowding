package pl.remplewicz.api;

import okhttp3.OkHttpClient;
import pl.remplewicz.util.CrowdingConstants;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {

    private static ICrowdingApi api;

    public static ICrowdingApi getApi() {
        if (api == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CrowdingConstants.BACKEND_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
            api = retrofit.create(ICrowdingApi.class);
        }
        return api;
    }
}
