package pl.remplewicz.api;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

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
                    .addConverterFactory(GsonConverterFactory
                            .create(new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                                @Override
                                public LocalDateTime deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                                    return ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalDateTime();
                                }
                            }).create()))
                    .client(httpClient.build())
                    .build();
            api = retrofit.create(ICrowdingApi.class);
        }
        return api;
    }
}
