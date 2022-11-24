package com.bawp.contactroom.model;


import android.app.Application;

import com.bawp.contactroom.data.ContactRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MediaViewModel extends AndroidViewModel {

    public static ContactRepository repository;
    public final LiveData<List<MediaModel>> allMedias;


    public MediaViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allMedias = repository.getAllData();
    }

    public LiveData<List<MediaModel>> getAllItems() { return allMedias; }
    public static void insert(MediaModel mediaModel) { repository.insert(mediaModel); }

    public LiveData<MediaModel> get(int id) { return repository.get(id);}
    public static void update(MediaModel mediaModel) { repository.update(mediaModel);}
    public static void delete(MediaModel mediaModel) { repository.delete(mediaModel);}
}
