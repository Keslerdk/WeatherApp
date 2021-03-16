package com.example.weatherapp.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.weatherapp.data.db.entity.favourites.Favourites;

import java.util.List;

@Dao
public interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favourites favourites);

    @Update
    void update(Favourites favourites);

    @Delete
    void delete(Favourites favourites);

    @Query("select * from favourites_table")
    LiveData<List<Favourites>> getFavourites();

    @Query("select * from favourites_table where id = :cityId")
    LiveData<Favourites> getFavItem(int cityId);

}
