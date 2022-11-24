package com.bawp.contactroom.model;


import android.app.Application;

import com.bawp.contactroom.data.ContactRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MediaViewModel extends AndroidViewModel {

    public static ContactRepository repository;
    public final LiveData<List<Contact>> allMedias;


    public MediaViewModel(@NonNull Application application) {
        super(application);
        repository = new ContactRepository(application);
        allMedias = repository.getAllData();
    }

    public LiveData<List<Contact>> getAllItems() { return allMedias; }
    public static void insert(Contact contact) { repository.insert(contact); }

    public LiveData<Contact> get(int id) { return repository.get(id);}
    public static void update(Contact contact) { repository.update(contact);}
    public static void delete(Contact contact) { repository.delete(contact);}
}
