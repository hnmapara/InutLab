package lab.mapara.com.intuittest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import lab.mapara.com.intuittest.io.ApiClient;
import lab.mapara.com.intuittest.models.Album;
import lab.mapara.com.intuittest.models.Song;

public class DetailViewModel extends ViewModel {
    int currentScrollPosition;
    int currentOffset;
    List<Song> albumList;
    int mAlbumId;
    MutableLiveData<List<Song>> mListOfSongsLiveData;

    public DetailViewModel() {
        mListOfSongsLiveData = ApiClient.getInstance().getmSongLiveData();
        albumList = new ArrayList<>();
        currentOffset = 0;
    }

    public void setAlbumId(int albumId) {
        mAlbumId = albumId;
        ApiClient.getInstance().fetchAsyncSongs(albumId);
    }

    public int getCurrentOffset() {
        return currentOffset;
    }

    public void setCurrentOffset(int currentOffset) {
        this.currentOffset = currentOffset;
    }

    public int getCurrentScrollPosition() {
        return currentScrollPosition;
    }

    public void setCurrentScrollPosition(int currentScrollPosition) {
        this.currentScrollPosition = currentScrollPosition;
    }

    public LiveData<List<Song>> getListOfSongs() {
        return mListOfSongsLiveData;
    }

    public void setListOfAlbum(List<Song> listOfAlbum) {
        albumList = listOfAlbum;
    }
}
