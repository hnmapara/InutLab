package lab.mapara.com.intuittest.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AlbumResponseModel {
/* Sample Response
    { "photos": { "page": 2, "pages": 10, "perpage": 100, "total": "1000",
            "photo": [
                      { "id": "30142020893", "owner": "62544885@N04", "secret": "8f00297228", "server": "5739", "farm": 6, "title": "細蓉. #canton #cantonesefood #food", "ispublic": 1, "isfriend": 0, "isfamily": 0 },
                       {..},
                       {..}
                     ]
    }


    @SerializedName("page")
    private int mPage;

    @SerializedName("pages")
    private int mPages;

    @SerializedName("photos")
    private PhotosBlock photos;

    public List<PhotoModel> getPhotos() {
        return photos.getPhotos();
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getPages() {
        return mPages;
    }

    public void setPages(int mPages) {
        this.mPages = mPages;
    }

    static class AlbumBlock {
        @SerializedName("photo")
        public List<Album> albums;

        public List<Album> getAlbums() {
            return photo;
        }
    }
    */
}
