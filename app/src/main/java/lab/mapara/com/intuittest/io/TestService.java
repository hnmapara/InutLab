package lab.mapara.com.intuittest.io;

import java.util.List;

import lab.mapara.com.intuittest.models.Album;
import lab.mapara.com.intuittest.models.Song;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Retrofit Api Service to parse json response
 */
public interface TestService {

//    @GET("/{method}?start={offset}&limit={limit}")
//    Call<List<Album>> getAlbums(@Query("method") String method, @Query("offset") int offset, @Query("limit") int limit);
//
//    @GET("/{method}?id={id}")
//    Call<List<Song>> getSongs(@Query("method") String method, @Query("id") int albumId);
//
    @GET("/albums")
    Call<List<Album>> getAlbums(@Query("start") int offset, @Query("limit") int limit);

    @GET("/songs")
    Call<List<Song>> getSongs(@Query("id") int albumId);
}
