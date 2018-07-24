package com.example.administrator.dssproject.Utils;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.VideoView;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.R;

import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class ImageVideoView extends FrameLayout {

    private ImageView mImageView;
    private VideoView mVideoView;

    @NonNull
    private List<PlaylistItem> mPlaylistItems;
    @NonNull
    private List<MediaSrc> mSources;
    private int mCurIndex = 0;

    public ImageVideoView(Context context) {
        super(context);
        init(null, 0);
    }

    public ImageVideoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ImageVideoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        Context context = getContext();
        FrameLayout.LayoutParams layoutParams = new LayoutParams(MATCH_PARENT, MATCH_PARENT);

        mImageView = new ImageView(context);
        mImageView.setLayoutParams(layoutParams);

        mVideoView = new VideoView(context);
        mVideoView.setLayoutParams(layoutParams);
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                showNextMedia();
            }
        });

        addView(mImageView);
        addView(mVideoView);
    }

    public void setMediaSources(@NonNull List<PlaylistItem> playlistItems, @NonNull List<MediaSrc> sources) {
        mPlaylistItems = playlistItems;
        mSources = sources;

        showNextMedia();

    }

    private void showNextMedia() {
        if (mCurIndex >= mSources.size()){
            updateVisibility(1);
            mImageView.setImageResource(R.drawable.loading);
            return;
        }else{

        }
        MediaSrc source = mSources.get(mCurIndex);
        switch (source.getTypeID()) {
            case 1:
                // image
                mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
                mImageView.setImageURI(Uri.parse(source.getUrlLocal()));
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showNextMedia();
                    }
                }, mPlaylistItems.get(mCurIndex).getDuration() * 1000);
                break;
            case 2:
                // video
                mVideoView.setVideoPath(source.getUrlLocal());
                mVideoView.requestFocus();
                mVideoView.start();
                break;
            default:
                throw new IllegalArgumentException("Unknown type ID");
        }
        updateVisibility(source.getTypeID());
        mCurIndex++;
    }

    private void updateVisibility(int mediaType) {
        mImageView.setVisibility(mediaType == 1 ? VISIBLE : INVISIBLE);
        mVideoView.setVisibility(mediaType == 2 ? VISIBLE : INVISIBLE);
    }
}
