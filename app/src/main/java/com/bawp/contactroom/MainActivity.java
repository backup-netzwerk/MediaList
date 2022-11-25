package com.bawp.contactroom;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bawp.contactroom.adapter.RecyclerViewAdapter;
import com.bawp.contactroom.media_list.MediaList;
import com.bawp.contactroom.model.Contact;
import com.bawp.contactroom.model.MediaViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;


public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener {
    private static final String TAG = "TAG_MainActivity";
    public static final String MEDIA_ID = "media_id";

    private RecyclerView listVw;
    private RecyclerViewAdapter listVwAdapter;
    private MediaViewModel mediaViewModel;
    static int totalList = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void createMediaList() {
        listVw = findViewById(R.id.recycler_view);
        listVw.setHasFixedSize(true);
        listVw.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mediaViewModel = new ViewModelProvider.AndroidViewModelFactory(MainActivity.this.getApplication())
                .create(MediaViewModel.class);
    }

    ;

    public void watchMediaList() {
        // lifecycleowner (this first 'this' as observe param) requires extends AppCompatActivity
        mediaViewModel.getAllItems().observe(this, allMedias -> {
            listVwAdapter = new RecyclerViewAdapter(allMedias, MainActivity.this, this);

            // list total
            TextView listCountTv = findViewById(R.id.list_total_count_tv);

            totalList = listVwAdapter.getItemCount();

            if (totalList > 0) {
                TextView listCountMsgTv = findViewById(R.id.list_total_msg_tv);

                listCountTv.setText(String.valueOf(totalList));

                // msg text if plural
                if (totalList == 1) listCountMsgTv.setText("mensagem");
                else listCountMsgTv.setText("mensagens");

                findViewById(R.id.list_total_parent_ll).setVisibility(View.VISIBLE);
                findViewById(R.id.empty_instant_msg).setVisibility(View.GONE);
            } else {
                findViewById(R.id.list_total_parent_ll).setVisibility(View.GONE);
                findViewById(R.id.empty_instant_msg).setVisibility(View.VISIBLE);
            }

            listVw.setAdapter(listVwAdapter);
        });

        // ref: https://stackoverflow.com/questions/40726438/android-detect-when-the-last-item-in-a-recyclerview-is-visible
        listVw.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();

                boolean endHasBeenReached = lastVisible + 1 >= totalItemCount;
                if (totalItemCount >= 0 && endHasBeenReached) {
                    findViewById(R.id.end_msgs_list_tv).setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.end_msgs_list_tv).setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        createMediaList();
        watchMediaList();

        // TESTE CREATION ZONE
        MediaList.createNewItem("text", "this is a text, Febro...");
        // END TESTE CREATION ZONE
    }

    @Override
    public void onContactClick(int position) {
        Contact item = Objects.requireNonNull(mediaViewModel.allMedias.getValue()).get(position);
        // Log.d(TAG, "onContactClick: " + item.getId()); // e.g 4 if it is the fourth item in the list clicked on

        // choose media intent to open
        Intent intent = new Intent(MainActivity.this, NewContact.class);
        intent.putExtra(MEDIA_ID, item.getId());
        startActivity(intent);
    }
}


/* ARCHIVES
  FloatingActionButton fab = findViewById(R.id.add_contact_fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewContact.class);
            startActivityForResult(intent, NEW_MEDIA_ACTIVITY_REQUEST_CODE);
        });

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


 */