package lab.mapara.com.intuittest;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import lab.mapara.com.intuittest.io.ApiClient;
import lab.mapara.com.intuittest.models.Album;

public class MainActivity extends AppCompatActivity implements ListItemAdapter.ICallback {
    ListViewModel mListViewModel;
    RecyclerView mRecyclerView;
    ListItemAdapter mListItemAdapter;
    private ProgressBar mPrgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrgressBar = findViewById(R.id.progress_bar);

        mListViewModel = ViewModelProviders.of(this).get(ListViewModel.class);

        mListViewModel.getListOfAlbum().observe(this, new Observer<List<Album>>() {
            @Override
            public void onChanged(@Nullable List<Album> albums) {
                if (albums != null && albums.size() > 0)
                    mListViewModel.increaseOffset();
                mListItemAdapter.setData(albums);
                //mRecyclerView.scrollToPosition(mListViewModel.getCurrentScrollPosition());
                mPrgressBar.setVisibility(View.GONE);
            }
        });

        mRecyclerView = findViewById(R.id.products_list);
        mListItemAdapter = new ListItemAdapter(this, ListItemAdapter.TYPE_ALBUM);

        mRecyclerView.setHasFixedSize(true);
        final LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mListViewModel.setCurrentScrollPosition(mRecyclerView.getVerticalScrollbarPosition());
                int lastPosition = mLayoutManager.findLastCompletelyVisibleItemPosition();
                if (lastPosition == mListItemAdapter.getItemCount() - 1) {
                    mListViewModel.fetchMore();
                }
            }
        });

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mListItemAdapter);
    }

    @Override
    public void onCallBack(Album album) {
        // Start new Activty
        Intent detailActivity = new Intent(this, DetailActivity.class);
        detailActivity.putExtra("key_albumid", Integer.parseInt(album.id));
        startActivity(detailActivity);
    }
}
