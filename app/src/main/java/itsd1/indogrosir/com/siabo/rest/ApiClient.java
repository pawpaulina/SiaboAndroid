package itsd1.indogrosir.com.siabo.rest;

import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.Request;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina on 1/18/2017.
 */
public class ApiClient
{
//    public static final String BASE_URL = "http:/192.168.1.109/Siabo/public/"; //wifi SD 1
    public static final String BASE_URL = "http:/192.168.43.192/Siabo/public/"; //wifi hp
//    public static final String BASE_URL = "http:/192.168.0.9/Siabo/public/"; //wifi kost felix

    public static String token = "";
    private static Retrofit retrofit = null;

    public String getToken()
    {
        return token;
    }
    public void setToken(String token)
    {
        this.token = token;
    }

    public static Retrofit getClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor) //  todo for debug
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }
    private class MyInterceptor implements Interceptor {

        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            Request.Builder reqBuilder = original.newBuilder();

            Request request = reqBuilder.build();
            okhttp3.Response response = chain.proceed(request);

            String rawJson = response.body().string();

            return response.newBuilder().body(ResponseBody.create(response.body().contentType(), rawJson)).build();
        }
    }

//    public static Retrofit getClient()
//    {
//        getClient(null);
//    }
//    public static Retrofit getClient(String url)
//    {
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//
//        if (url==null)
//        {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }else{
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(url)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .build();
//        }
//        return retrofit;
//    }
}
