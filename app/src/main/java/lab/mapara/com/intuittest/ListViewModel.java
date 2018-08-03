package lab.mapara.com.intuittest;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lab.mapara.com.intuittest.io.ApiClient;
import lab.mapara.com.intuittest.models.Album;

public class ListViewModel extends AndroidViewModel {
    static final int LIMIT = 10;

    int currentScrollPosition;
    int currentOffset;
    List<Album> albumList;
    MutableLiveData<List<Album>> mListOfAlbumLiveData;

    public ListViewModel(Application application) {
        super(application);
        mListOfAlbumLiveData = ApiClient.getInstance().getmAlbumLiveData();
        albumList = new ArrayList<>();
        currentOffset = 0;
        fetchMore();
    }

    public int getCurrentOffset() {
        return currentOffset;
    }

    public void increaseOffset() {
        currentOffset = currentOffset + LIMIT;
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

    public LiveData<List<Album>> getListOfAlbum() {
        return mListOfAlbumLiveData;
    }

    public void setListOfAlbum(List<Album> listOfAlbum) {
        albumList = listOfAlbum;
    }

    public void fetchMore() {
        ApiClient.getInstance().fetchAsyncAlbum(getApplication().getApplicationContext(), currentOffset, LIMIT);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        ApiClient.getInstance().mAlbumList.clear();
        ApiClient.getInstance().getmAlbumLiveData().setValue(Collections.<Album>emptyList());
    }
}
