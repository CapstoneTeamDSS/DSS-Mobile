package com.example.administrator.dssproject.DataBase;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {
        MediaSrc.class,
        Playlist.class,
        PlaylistItem.class,
        Scenario.class,
        ScenarioItem.class,
        Box.class
        }, version = 1,exportSchema  = false)
public abstract class AppDatabase extends RoomDatabase{
    public abstract MediaSrcDAO mediaSrcDAO();
    public abstract PlaylistItemDAO playlistItemDAO();
    public abstract PlaylistDAO playlistDAO();
    public abstract ScenarioItemDAO scenarioItemDAO();
    public abstract ScenarioDAO scenarioDAO();
    public abstract BoxDAO boxDAO();

}
