package itsd1.indogrosir.com.siabo.rest;

import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Paulina on 1/18/2017.
 */
public class ApiClient
{
    public static final String BASE_URL = "http:/192.168.43.192/Siabo/public/";
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
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit==null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
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
