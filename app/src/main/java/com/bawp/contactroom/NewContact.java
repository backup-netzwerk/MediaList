package com.bawp.contactroom;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bawp.contactroom.model.MediaModel;
import com.bawp.contactroom.model.MediaViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

public class NewContact extends AppCompatActivity {
    public static final String NAME_REPLY = "name_reply";
    public static final String OCCUPATION = "occupation";
    private EditText enterName;
    private EditText enterOccupation;
    private Button saveInfoButton;
    private int contactId = 0;
    private Boolean isEdit = false;
    private Button updateButton;
    private Button deleteButton;

    private MediaViewModel mediaViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_contact);
        enterName = findViewById(R.id.enter_name);
        enterOccupation = findViewById(R.id.enter_occupation);
        saveInfoButton = findViewById(R.id.save_button);

        mediaViewModel = new ViewModelProvider.AndroidViewModelFactory(NewContact.this
                .getApplication())
                .create(MediaViewModel.class);

        if (getIntent().hasExtra(MainActivity.MEDIA_ID)) {
            contactId = getIntent().getIntExtra(MainActivity.MEDIA_ID, 0);

            mediaViewModel.get(contactId).observe(this, contact -> {
                if (contact != null) {
                    enterOccupation.setText(contact.getOccupation());
                    enterName.setText(contact.getName());
                }
            });
            isEdit = true;

        }


        saveInfoButton.setOnClickListener(view -> {
            Intent replyIntent = new Intent();

            if (!TextUtils.isEmpty(enterName.getText())
                    && !TextUtils.isEmpty(enterOccupation.getText())) {
                String name = enterName.getText().toString();
                String occupation = enterOccupation.getText().toString();

                replyIntent.putExtra(NAME_REPLY, name);
                replyIntent.putExtra(OCCUPATION, occupation);
                setResult(RESULT_OK, replyIntent);


            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();

        });

        deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(view -> edit(true));
        //Update button
        updateButton = findViewById(R.id.update_button);
        updateButton.setOnClickListener(view -> edit(false));


        if (isEdit) {
            saveInfoButton.setVisibility(View.GONE);
        } else {
            updateButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.GONE);
        }

    }

    private void edit(Boolean isDelete) {
        String name = enterName.getText().toString().trim();
        String occupation = enterOccupation.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(occupation)) {
            Snackbar.make(enterName, R.string.empty, Snackbar.LENGTH_SHORT)
                    .show();
        } else {
            MediaModel mediaModel = new MediaModel();
            mediaModel.setId(contactId);
            mediaModel.setName(name);
            mediaModel.setOccupation(occupation);
            if (isDelete)
                MediaViewModel.delete(mediaModel);
            else
                MediaViewModel.update(mediaModel);
            finish();

        }
    }
}