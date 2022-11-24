package com.bawp.contactroom.data;

import com.bawp.contactroom.model.MediaModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ContactDao {
    // CRUD
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(MediaModel mediaModel);

    @Query("DELETE FROM MediaModel")
    void deleteAll();

    @Query("SELECT * FROM MediaModel ORDER BY name ASC")
    LiveData<List<MediaModel>> getAllItems();

    @Query("SELECT * FROM MediaModel WHERE MediaModel.id == :id")
    LiveData<MediaModel> get(int id);

    @Update
    void update(MediaModel mediaModel);

    @Delete
    void delete(MediaModel mediaModel);
}
