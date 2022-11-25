package com.bawp.contactroom.data;

import com.bawp.contactroom.model.Contact;

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
    void insert(Contact contact);

    @Query("DELETE FROM contact_table")
    void deleteAll();

    // ordered by the latest updated and added items in the top to bottom
    @Query("SELECT * FROM contact_table ORDER BY deliveryDate DESC")
    LiveData<List<Contact>> getAllItems();

    @Query("SELECT * FROM contact_table WHERE contact_table.id == :id")
    LiveData<Contact> get(int id);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);
}
