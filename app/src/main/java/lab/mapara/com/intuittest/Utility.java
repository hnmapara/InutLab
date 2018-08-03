package lab.mapara.com.intuittest;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Utility {
    public static Executor mNetworkIo = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() + 1);
    public static Executor mMainThread = new MainThreadExecutor();

    static class MainThreadExecutor implements Executor {
        private Handler mainThreadHandler = new Handler(Looper.getMainLooper());
        @Override
        public void execute(@NonNull Runnable command) {
            mainThreadHandler.post(command);
        }
    }


    public static JSONArray getAlbumsJSONFromAsset(Context context) {
        JSONArray jsonArray = new JSONArray();
        try {
            InputStream iStream = context.getAssets().open("temp.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(iStream, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            String st = null;
            while((st=bufferedReader.readLine()) != null) {
                sb.append(st);
            }
            jsonArray = new JSONArray(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static JSONArray getAlbumsJSON(int start, int limit) {
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i=start; i<start+limit; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", "album"+i);
                jsonObject.put("name", "Album-"+i);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException jex) {}

        return jsonArray;
    }

    public static JSONArray getSongsJSON(int start, int limit) {
        JSONArray jsonArray = new JSONArray();
        try {
            for(int i=start; i<start+limit; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", "song"+i);
                jsonObject.put("name", "song-"+i);
                jsonArray.put(jsonObject);
            }
        } catch (JSONException jex) {}

        return jsonArray;
    }
}
