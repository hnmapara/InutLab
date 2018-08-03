package lab.mapara.com.intuittest;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import lab.mapara.com.intuittest.models.Song;

public class DetailActivity extends AppCompatActivity implements DetailItemAdapter.ICallback{
    RecyclerView mRecyclerView;
    DetailItemAdapter mListItemAdapter;
    DetailViewModel mDetailViewModel;
    List<Song> mSongs;
    ProgressBar mPrgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mPrgressBar = findViewById(R.id.progress_bar);

        int albumId = getIntent().getExtras().getInt("key_albumid", -1);

        mDetailViewModel = ViewModelProviders.of(this).get(DetailViewModel.class);
        mDetailViewModel.getListOfSongs().observe(this, new Observer<List<Song>>() {
            @Override
            public void onChanged(@Nullable List<Song> songs) {
                mSongs = songs;
                mListItemAdapter.setData(songs);
                mPrgressBar.setVisibility(View.GONE);
                mListItemAdapter.notifyDataSetChanged();
            }
        });

        RecyclerView recyclerView = findViewById(R.id.products_details_list);
        mListItemAdapter = new DetailItemAdapter(this, ListItemAdapter.TYPE_SONG);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                mLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
//                int lastPosition = layoutManager.findLastCompletelyVisibleItemPosition();
//                if (lastPosition == mProductAdapter.getItemCount() - 1) {
//                    mListViewModel.fetchMore();
//                }
            }
        });

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mListItemAdapter);

        mDetailViewModel.setAlbumId(albumId);

    }

    @Override
    public void onCallBack(int position) {
        //Do nothing
    }
}
