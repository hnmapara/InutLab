package lab.mapara.com.intuittest.io;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lab.mapara.com.intuittest.Utility;
import lab.mapara.com.intuittest.models.Album;
import lab.mapara.com.intuittest.models.Song;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {

    private static final String TAG = "ApiClient";

    private static ApiClient mInstance;
    private TestService mRetrofitService;
    public List<Album> mAlbumList;

    private static final String URL = "http://0b673584.ngrok.io";

//    private static final String ALBUM_URL = URL + "/albums?start=%s&limit=%s";
//    private static final String SONG_URL = URL + "/songs?id=%s";

    private MutableLiveData<List<Album>> mAlbumLiveData;
    private MutableLiveData<List<Song>> mSongLiveData;

    private ApiClient() {
        mAlbumLiveData = new MutableLiveData<>();
        mAlbumList = new ArrayList<>();
        mSongLiveData = new MutableLiveData<>();

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .build();

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        mRetrofitService = restAdapter.create(TestService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new ApiClient();
        }
        return mInstance;
    }

    public MutableLiveData<List<Album>> getmAlbumLiveData() {
        return mAlbumLiveData;
    }

    public MutableLiveData<List<Song>> getmSongLiveData() {
        return mSongLiveData;
    }

    public void fetchAsyncAlbum(final Context context, final int offset, final int limit) {
        Utility.mNetworkIo.execute(new Runnable() {
            @Override
            public void run() {
                //JSONArray albums = Utility.getAlbumsJSON(offset, limit);
                JSONArray albums = Utility.getAlbumsJSONFromAsset(context);
                Type listType = new TypeToken<List<Album>>() {}.getType();

                final List<Album> myModelList = new Gson().fromJson(albums.toString(), listType);
                mAlbumList.addAll(myModelList);
                Utility.mMainThread.execute(new Runnable() {
                    @Override
                    public void run() {
                        mAlbumLiveData.postValue(mAlbumList);
                    }
                });
            }
        });
    }
/*
    public void fetchAsyncAlbum(int offset, int limit) {
        Call<List<Album>> call = mRetrofitService.getAlbums(offset,limit);
        call.enqueue(new Callback<List<Album>>() {
            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    mAlbumLiveData.postValue(response.body());
                } else {
                    Log.e(TAG, "onResponse " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                mAlbumLiveData.postValue(Collections.<Album>emptyList());
                // Or set error on differnt errorLiveData
                Log.e(TAG, "onFailure", t);
            }
        });
    }
*/
    public void fetchAsyncSongs(int alubumId) {
        Call<List<Song>> call = mRetrofitService.getSongs( alubumId);
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful()) {
                    mSongLiveData.postValue(response.body());
                } else {
                    Log.e(TAG, "onResponse " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                mSongLiveData.postValue(Collections.<Song>emptyList());
                // Or set error on differnt errorLiveData
                Log.e(TAG, "onFailure", t);
            }
        });
    }

}
