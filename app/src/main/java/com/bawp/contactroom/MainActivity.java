package com.bawp.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bawp.contactroom.adapter.RecyclerViewAdapter;
import com.bawp.contactroom.model.Contact;
import com.bawp.contactroom.model.MediaViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener {

    private static final int NEW_MEDIA_ACTIVITY_REQUEST_CODE = 1;
    private static final String TAG = "Clicked";
    public static final String MEDIA_ID = "media_id";
    private MediaViewModel mediaViewModel;
    private TextView textView;
    private RecyclerView listVw;
    private RecyclerViewAdapter listVwAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listVw = findViewById(R.id.recycler_view);

        listVw.setHasFixedSize(true);
        listVw.setLayoutManager(new LinearLayoutManager(this));


        mediaViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this
                .getApplication())
                .create(MediaViewModel.class);

        mediaViewModel.getAllItems().observe(this, allMedias -> {
            listVwAdapter = new RecyclerViewAdapter(allMedias, MainActivity.this, this);
            listVw.setAdapter(listVwAdapter);
        });



        FloatingActionButton fab = findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivityForResult(intent, NEW_MEDIA_ACTIVITY_REQUEST_CODE);
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_MEDIA_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(NewContact.NAME_REPLY);
            String occupation = data.getStringExtra(NewContact.OCCUPATION);

            assert name != null;
            Contact contact = new Contact(name, occupation);

            MediaViewModel.insert(contact);
        }
    }

    @Override
    public void onContactClick(int position) {
        Contact contact = Objects.requireNonNull(mediaViewModel.allMedias.getValue()).get(position);
        Log.d(TAG, "onContactClick: " + contact.getId());

        Intent intent = new Intent(MainActivity.this, NewContact.class);
        intent.putExtra(MEDIA_ID, contact.getId());
        startActivity(intent);
    }
}