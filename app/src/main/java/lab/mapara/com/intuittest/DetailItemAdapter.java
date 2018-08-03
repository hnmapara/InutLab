package lab.mapara.com.intuittest;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import lab.mapara.com.intuittest.models.Song;

public class DetailItemAdapter extends RecyclerView.Adapter<DetailItemAdapter.MyViewHolder> {

    private static int COUNT = 1000;
    private static final String URL = "http://placehold.it/120x120&text=image";

    public static final int TYPE_ALBUM = 0;
    public static final int TYPE_SONG = 1;

    private List<Song> mData;
    private int mType;

    private ICallback mICallback;
    public interface ICallback {
        public void onCallBack(int position);
    }

    public void setData(List<Song> data) {
        mData = data;
    }

    public DetailItemAdapter(@NonNull ICallback callback, int type) {
        mICallback = callback;
        mType = type;
        mData = new ArrayList<Song>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final MyViewHolder viewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, null));
//        viewHolder.mDeleteView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int adpterPos = viewHolder.getAdapterPosition();
//                mICallback.onCallBack(adpterPos);
//            }
//        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.itemView.setBackgroundColor(holder.getAdapterPosition()%2==0 ? Color.WHITE : Color.GRAY);
        holder.mNamveView.setText(mData.get(holder.getAdapterPosition()).name);
//        Glide.with(holder.itemView.getContext()).load(URL +  mData.get(holder.getAdapterPosition()))
//                .apply(new RequestOptions())
//                .into(holder.mImage);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mNamveView;
        ImageView mImage;
        Button mDeleteView;

        public MyViewHolder(View itemView) {
            super(itemView);

            mNamveView = itemView.findViewById(R.id.name);
//            mImage = itemView.findViewById(R.id.image);
//            mDeleteView = itemView.findViewById(R.id.delete_button);
        }
    }
}

