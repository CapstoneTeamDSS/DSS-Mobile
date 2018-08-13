package com.example.administrator.dssproject.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.administrator.dssproject.DataBase.MediaSrc;
import com.example.administrator.dssproject.DataBase.PlaylistItem;
import com.example.administrator.dssproject.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

public class MediaView extends FrameLayout {

    private ImageView mImageView;
    private VideoView mVideoView;
    private TextView mTextView;

    @NonNull
    private List<PlaylistItem> mPlaylistItems;
    @NonNull
    private List<MediaSrc> mSources;
    private int mCurIndex = 0;

    public MediaView(Context context) {
        super(context);
        init(null, 0);
    }

    public MediaView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MediaView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mTextView = new TextView(context);
        mTextView.setTextColor(Color.WHITE);
        mTextView.setTextSize(20);
        mTextView.setSingleLine();
        mTextView.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        mTextView.setMarqueeRepeatLimit(-1);
        mTextView.setSelected(true);
        mTextView.setLayoutParams(layoutParams);



        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                showNextMedia();
            }
        });

        addView(mImageView);
        addView(mVideoView);
        addView(mTextView);
    }

    public void setMediaSources(@NonNull List<PlaylistItem> playlistItems, @NonNull List<MediaSrc> sources, boolean isMute) {
        mPlaylistItems = playlistItems;
        mSources = sources;
        if(isMute){
            mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.setVolume(0f, 0f);
                }
            });
        }
    }

    private void showNextMedia() {
        if(mCurIndex == mSources.size()){
            mCurIndex = 0;
        }
        MediaSrc source = mSources.get(mCurIndex);
        List<MediaSrc> mediaSrcList = mSources;
        switch (source.getTypeID()) {
            case 1:
                // image
                mImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
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
            case 3:
                // video
                mVideoView.setVideoPath(source.getUrlLocal());
                mVideoView.requestFocus();
                mVideoView.start();
                break;
            case 4:
                new ShowTextTask().execute();
                break;
            default:
                throw new IllegalArgumentException("Unknown type ID");
        }
        updateVisibility(source.getTypeID());
        mCurIndex++;
    }

    private void showPlaceholder() {
        updateVisibility(1);
        mImageView.setImageResource(R.drawable.loading);
    }

    private void updateVisibility(int mediaType) {
        mImageView.setVisibility(mediaType == 1 ? VISIBLE : INVISIBLE);
        mVideoView.setVisibility(mediaType == 2 || mediaType == 3 ? VISIBLE : INVISIBLE);
        mTextView.setVisibility(mediaType == 4 ? VISIBLE : INVISIBLE);
    }

    public void stopMedia() {
        mVideoView.stopPlayback();
        showPlaceholder();

    }

    public void startMedia() {
        showNextMedia();
    }

    private class ShowTextTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
            StringBuilder builder = new StringBuilder();
            for (MediaSrc source : mSources){
                String path = source.getUrlLocal();
                String filename = Supporter.getNameFile(path);
                String folder = Supporter.getPathFolder(path);
                String text = readTextFromFile(folder, filename);
                builder.append(text);
            }
            return builder.toString();
        }

        private String readTextFromFile(String folder, String filename) {
            File file = new File(folder, filename);
            StringBuilder builder = new StringBuilder();
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append(line);
                }
                br.close();
            }
            catch (IOException e) {
                Log.e("ReadText:", e.toString());
            }
            return builder.toString();
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);

            mTextView.setText(content);
        }
    }

}
