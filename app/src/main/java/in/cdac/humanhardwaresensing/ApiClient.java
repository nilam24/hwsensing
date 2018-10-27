package in.cdac.humanhardwaresensing;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.internal.connection.RouteException;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String BaseUrl="http://192.168.0.7/hwsensingphp/hwsensingphp/";
    //"http://192.168.0.7/bookstore/";  192.168.43.25  http://192.168.0.7/bookstore/docregister.php
    //"http://192.168.0.136/bookstore/";  http://10.0.2.2/
    public static Retrofit retrofit=null;

    public static Retrofit getApiClient() {

        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120,TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        client.retryOnConnectionFailure();
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        catch (RouteException e)
        {
            Log.e("",""+e.getMessage());
        }



        Log.e("","++++"+retrofit.toString());
        return retrofit;
    }
}
