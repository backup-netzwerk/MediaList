package com.bawp.contactroom.data;

import android.app.Application;

import com.bawp.contactroom.model.MediaModel;
import com.bawp.contactroom.util.ContactRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<MediaModel>> allContacts;

    public ContactRepository(Application application) {
        ContactRoomDatabase db = ContactRoomDatabase.getDatabase(application);
        contactDao = db.contactDao();

        allContacts = contactDao.getAllItems();

    }
    public LiveData<List<MediaModel>> getAllData() { return allContacts; }
    public void insert(MediaModel mediaModel) {
         ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.insert(mediaModel));
    }
    public LiveData<MediaModel> get(int id) {
         return contactDao.get(id);
    }
    public void update(MediaModel mediaModel) {
        ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.update(mediaModel));
    }
    public void delete(MediaModel mediaModel) {
         ContactRoomDatabase.databaseWriteExecutor.execute(() -> contactDao.delete(mediaModel));
    }

}
