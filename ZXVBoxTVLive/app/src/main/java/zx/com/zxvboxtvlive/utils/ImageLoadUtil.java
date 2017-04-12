package zx.com.zxvboxtvlive.utils;

import android.content.Context;
import android.net.Uri;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


public class ImageLoadUtil {
    private static final String TAG = "ImageLoadUtil";
    private static Picasso picasso = null;

    public static void loadImageInto(Context context, String url, int width, int height, Target target) {
        initPicasso(context);

        picasso.load(url)
                .resize(width, height)
                .centerInside()
                .into(target);
    }

    public static void loadImageInto(Context context, String url ,Target target) {

        initPicasso(context);

        picasso.load(url)
                .into(target);
    }

    private static void initPicasso(Context context) {
        if (picasso == null) {
            picasso = new Picasso.Builder(context)
                    .downloader(new OkHttpDownloader(new OkHttpClient()))
                    .listener(new Picasso.Listener() {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                            Logger.getLogger().e(TAG, exception.getMessage());
                        }
                    })
                    .build();
        }
    }

}
