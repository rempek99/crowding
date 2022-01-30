package pl.remplewicz.api;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import pl.remplewicz.util.AuthTokenStore;
import pl.remplewicz.util.CrowdingConstants;
import pl.remplewicz.util.CustomObjectMapper;
import pl.remplewicz.util.InformationBar;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInstance {

    private static ICrowdingApi api;

    public static ICrowdingApi getApi() {
        if (api == null) {
            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(chain -> {
                String token = AuthTokenStore.getInstance().getToken();
                if(token != null) {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", "Bearer " +
                                    token)
                            .build();
                    return chain.proceed(newRequest);
                }
                Response proceed = chain.proceed(chain.request());
                if(proceed.code() == 500) {
                    InformationBar.showInfo("ERROR");
                }
                return proceed;
            });

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CrowdingConstants.BACKEND_URL)
                    .addConverterFactory(JacksonConverterFactory.create(new CustomObjectMapper()))
                    .client(httpClient.build())
                    .build();
            api = retrofit.create(ICrowdingApi.class);
        }
        return api;
    }
}
