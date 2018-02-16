package com.material.nereeducation.activity.notification;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.material.nereeducation.R;
import com.material.nereeducation.adapter.AdapterNotification;
import com.material.nereeducation.model.Notification;
import com.material.nereeducation.utils.Tools;
import com.material.nereeducation.widget.LineItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterNotification mAdapter;
    private ActionModeCallback actionModeCallback;
    private ActionMode actionMode;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initToolbar();
        initComponent();
        Tools.setSystemBarColor(this, R.color.black);
    }

    private void initToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Notifications");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private List<Notification> items = new ArrayList<>();
    private LinearLayout messageEmptyList;
    private void initComponent() {

        messageEmptyList = findViewById(R.id.messageEmptyList);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayout.VERTICAL));
        recyclerView.setHasFixedSize(true);

        //set data and list adapter
        mAdapter = new AdapterNotification(this, items);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnClickListener(new AdapterNotification.OnClickListener() {

            @Override
            public void onItemClick(View view, Notification obj, int pos) {
                if (mAdapter.getSelectedItemCount() > 0) {
                    enableActionMode(pos);
                } else {
                    // read the inbox which removes bold from the row
                    Notification notification = mAdapter.getItem(pos);
                    Toast.makeText(getApplicationContext(), "Read: " + notification.sender_id, Toast.LENGTH_SHORT).show();

                    updateStatus(notification.notificationId,"read",pos);

                }
            }

            @Override
            public void onItemLongClick(View view, Notification obj, int pos) {
                enableActionMode(pos);
            }
        });

        actionModeCallback = new ActionModeCallback();

        getNotificationData();

    }

    public void onItemClick(View view, Notification obj, int pos) {
        if (mAdapter.getSelectedItemCount() > 0) {
            enableActionMode(pos);
        } else { // read the inbox which removes bold from the row
            Notification notification = mAdapter.getItem(pos);
            Toast.makeText(getApplicationContext(), "Read: " + notification.sender_id, Toast.LENGTH_SHORT).show();
            updateStatus(notification.notificationId,"read",pos);
    } }

    private void getNotificationData()
    {
        items.clear();
        GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uuid = firebaseAuth.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("notifications/"+uuid+"/data_notification").orderByChild("status").startAt("read").endAt("unread").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null)
                {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        String dataReceived = gson.toJson(snapshot.getValue());
                        Notification notificationItems = gson.fromJson(dataReceived, Notification.class);
                        items.add(notificationItems);
                    }
                    mAdapter.notifyDataSetChanged();
                }else
                {
                    messageEmptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateStatus(final String notificationId, String status, final int position) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        String uuid = firebaseAuth.getUid();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("notifications/"+uuid+"/data_notification").child(notificationId).child("status").setValue(status
        ).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                mAdapter.notifyItemChanged(position); //Problem here - how to update text from BOLD to NORMAL for this item?
            }
        });
    }

    private void enableActionMode(int position) {
        if (actionMode == null) {
            actionMode = startSupportActionMode(actionModeCallback);
        }
        toggleSelection(position);
    }

    private void toggleSelection(int position) {
        mAdapter.toggleSelection(position);
        int count = mAdapter.getSelectedItemCount();

        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.menu_delete, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            if (id == R.id.action_delete) {
                deleteInboxes();
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mAdapter.clearSelections();
            actionMode = null;
        }
    }

    private void deleteInboxes() {
        List<Integer> selectedItemPositions = mAdapter.getSelectedItems();
        for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
            mAdapter.removeData(selectedItemPositions.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}