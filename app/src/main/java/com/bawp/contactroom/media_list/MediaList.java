package com.bawp.contactroom.media_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bawp.contactroom.NewContact;
import com.bawp.contactroom.R;
import com.bawp.contactroom.adapter.RecyclerViewAdapter;
import com.bawp.contactroom.model.Contact;
import com.bawp.contactroom.model.MediaViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class MediaList extends AppCompatActivity implements RecyclerViewAdapter.OnContactClickListener {
    Activity act;
    private final Context con;

    private final RecyclerView listVw;
    private RecyclerViewAdapter listVwAdapter;
    private final MediaViewModel mediaViewModel;
    static int totalList = 0;

    private static final String TAG = "TAG_MediaList";
    public static final String MEDIA_ID = "media_id";

    public MediaList(Context context) {
        act = (Activity) context;
        this.con = context;

        listVw = act.findViewById(R.id.recycler_view);
        listVw.setHasFixedSize(true);
        listVw.setLayoutManager(new LinearLayoutManager(context));

        mediaViewModel = new ViewModelProvider.AndroidViewModelFactory(act.getApplication())
                .create(MediaViewModel.class);
        Log.d(TAG, "MediaList: mediaViewModel" + mediaViewModel.getAllItems());
        // lifecycleowner (this first 'this' as observe param) requires extends AppCompatActivity
        mediaViewModel.getAllItems().observe(this, allMedias -> {
            Log.d(TAG, "MediaList: getAllItems INSIDE running....!@!");
            listVwAdapter = new RecyclerViewAdapter(allMedias, context, this);

            // list total
            TextView listCountTv = act.findViewById(R.id.list_total_count_tv);

            totalList = listVwAdapter.getItemCount();
            Log.d(TAG, "MediaList: totalListINNER" + listVwAdapter.getItemCount());
            if(totalList > 0) {
                TextView listCountMsgTv = act.findViewById(R.id.list_total_msg_tv);

                listCountTv.setText(String.valueOf(totalList));

                // msg text if plural
                if(totalList == 1) listCountMsgTv.setText("mensagem");
                else listCountMsgTv.setText("mensagens");
            }
            else {
                Log.d(TAG, "MediaList: " + "runnign 0 list");
                act.findViewById(R.id.list_total_parent_ll).setVisibility(View.GONE);
            }

            listVw.setAdapter(listVwAdapter);
        });
    }

    public static void createNewItem(String mediaType, String desc) {
        Locale localeBr = new Locale("pt", "BR");
        DateFormat sdf = new SimpleDateFormat("dd'/'MM'/'yy 'às' HH:mm", localeBr);
        String nowDate = sdf.format(new Date());

        // time ago timer - but not implemented since date is a saved as String by now.
        // long cMills = System.currentTimeMillis();
//        Date convertedDate = new Date();
//        String timeAgo = null;
//        try {
//            convertedDate = sdf.parse(nowDate);
//            assert convertedDate != null;
//            timeAgo = (String) DateUtils.getRelativeTimeSpanString(convertedDate.getTime(), cMills, DateUtils.FORMAT_ABBREV_RELATIVE);
//            Log.d(TAG, "createNewItem: timeAgo" + timeAgo);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


        Contact newMedia = new Contact(mediaType, desc, nowDate);
        MediaViewModel.insert(newMedia);
    }

    public boolean isListEmpty() {
        return totalList == 0;
    };

    @Override
    public void onContactClick(int position) {
        Contact item = Objects.requireNonNull(mediaViewModel.allMedias.getValue()).get(position);
        // Log.d(TAG, "onContactClick: " + item.getId()); // e.g 4 if it is the fourth item in the list clicked on

        // choose media intent to open
        Intent intent = new Intent(con, NewContact.class);
        intent.putExtra(MEDIA_ID, item.getId());
        con.startActivity(intent);
    }
}
