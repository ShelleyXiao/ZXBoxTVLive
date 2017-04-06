package zx.com.zxvboxtvlive.presenter;

import android.content.Context;
import android.net.Uri;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;
import zx.com.zxvboxtvlive.R;
import zx.com.zxvboxtvlive.ijkplayer.media.IjkVideoView;
import zx.com.zxvboxtvlive.utils.Logger;
import zx.com.zxvboxtvlive.view.IMainView;

/**
 * User: ShaudXiao
 * Date: 2017-03-31
 * Time: 16:27
 * Company: zx
 * Description:
 * FIXME
 */


public class VideoViewPresenter extends Presenter {

    private IjkVideoView mIjkVideoView;

    private IMainView mIMainView;

    private String mVideoUrl;

    private Context mContext;

    private int mRetryTimes = 0;
    private static final int CONNECTION_TIMES = 5;

    public VideoViewPresenter(Context context , IMainView view, IjkVideoView videoView) {
        mIjkVideoView = videoView;
        mIMainView = view;
        mContext = context;

        init();
    }

    @Override
    protected void init() {
        // init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");
//        mIjkVideoView.setVideoURI(Uri.parse(mVideoUrl));
        mIjkVideoView.setOnPreparedListener(new IMediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(IMediaPlayer mp) {
                mIjkVideoView.start();
                mIMainView.hideLoadingView();

                Logger.getLogger().i("video prepared, video start");

            }
        });

        mIjkVideoView.setOnInfoListener(new IMediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(IMediaPlayer mp, int what, int extra) {
                switch (what) {
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_START:
                        mIMainView.showLoadingView(R.string.show_load_progress_promate);
                        Logger.getLogger().i("MEDIA_INFO_BUFFERING_START");
                        break;
                    case IjkMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
                    case IjkMediaPlayer.MEDIA_INFO_BUFFERING_END:
                        mIMainView.hideLoadingView();
                        Logger.getLogger().i("MEDIA_INFO_BUFFERING_END" + what);
                        break;
                }
                return false;
            }
        });

        mIjkVideoView.setOnCompletionListener(new IMediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(IMediaPlayer mp) {
                mIMainView.showLoadingView(R.string.show_load_progress_promate);
                mIjkVideoView.stopPlayback();
                mIjkVideoView.release(true);
                mIjkVideoView.setVideoURI(Uri.parse(mVideoUrl));
            }
        });

        mIjkVideoView.setOnErrorListener(new IMediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(IMediaPlayer mp, int what, int extra) {
                if (mRetryTimes > CONNECTION_TIMES) {
//                    new AlertDialog.Builder(LiveActivity.this)
//                            .setMessage()
//                            .setPositiveButton(R.string.VideoView_error_button,
//                                    new DialogInterface.OnClickListener() {
//                                        public void onClick(DialogInterface dialog, int whichButton) {
//                                            LiveActivity.this.finish();
//                                        }
//                                    })
//                            .setCancelable(false)
//                            .show();
                } else {
                    mIjkVideoView.stopPlayback();
                    mIjkVideoView.release(true);
                    mIjkVideoView.setVideoURI(Uri.parse(mVideoUrl));

                    Logger.getLogger().e("Video play error " + what);
                }
                return false;
            }
        });
    }

    @Override
    protected void resume() {
    }

    @Override
    public void stop() {
        if (!mIjkVideoView.isBackgroundPlayEnabled()) {
            mIjkVideoView.stopPlayback();
            mIjkVideoView.release(true);
            mIjkVideoView.stopBackgroundPlay();
        }
        IjkMediaPlayer.native_profileEnd();
    }

    @Override
    protected void destroy() {
    }

    public void playVideo(String url) {
        Logger.getLogger().i("url " + url);
        mVideoUrl = url;
//        mVideoUrl = "http://ivi.bupt.edu.cn//hls/hunanhd.m3u8";
        mIjkVideoView.stopPlayback();
        mIjkVideoView.release(true);

        mIjkVideoView.setBackgroundResource(R.drawable.bg);

        mIMainView.showLoadingView(R.string.show_load_progress_promate);

        mIjkVideoView.setVideoURI(Uri.parse(mVideoUrl));
        mIjkVideoView.setRender(IjkVideoView.RENDER_TEXTURE_VIEW);
    }


}
