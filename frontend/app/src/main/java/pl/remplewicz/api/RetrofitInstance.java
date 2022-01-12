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
// set your desired log level
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
// add your other interceptors …
// add logging as last interceptor
            httpClient.addInterceptor(logging);



            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CrowdingConstants.BACKEND_URL)
                    .addConverterFactory(JacksonConverterFactory.create(new CustomObjectMapper()))
//                            GsonConverterFactory
//                            .create(new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
//                                @Override
//                                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
//                                    return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
//                                }
//                            }).create()))
                    .client(httpClient.build())
                    .build();
            api = retrofit.create(ICrowdingApi.class);
        }
        return api;
    }
}
