package com.example.final_emts;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RecordsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecordsAdapter adapter;
    private ImageView imgbtn, printN_share;
    private List<recordsData> recordsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        imgbtn = findViewById(R.id.backBtn);
        printN_share = findViewById(R.id.printN_share);
        recyclerView = findViewById(R.id.recyclerView55);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recordsList = new ArrayList<>();
        adapter = new RecordsAdapter(recordsList, this);

        recyclerView.setAdapter(adapter);

        // Retrieve data from Firebase and populate the recordsList
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Records");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recordsList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    recordsData recordsData = snapshot.getValue(recordsData.class);
                    if (recordsData != null) {
                        recordsList.add(recordsData);
                    }
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });

        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        printN_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(v);
            }
        });
    }

    // Function to get the current date and time
    String getCurrentDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy", Locale.getDefault());
        return sdf.format(new Date());
    }

    public void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.popup_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.menu_print) {
                    printAllItems();
                    return true;
                } else if (item.getItemId() == R.id.menu_share) {
                    shareData();
                    return true;
                } else {
                    return false;
                }
            }
        });

        popupMenu.show();
    }

    private void printAllItems() {
        for (int i = 0; i < recordsList.size(); i++) {
            recordsData item = recordsList.get(i);
            String printableContent = createPrintableContent(item);
            Log.d("PrintItem", printableContent);
        }
    }

    private void shareData() {
        // Get the printable content
        String printableContent = generatePrintableContent();

        // Create an intent to share the content
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, printableContent);
        shareIntent.setType("text/plain");
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

    private String generatePrintableContent() {
        StringBuilder printableContent = new StringBuilder();

        for (int i = 0; i < recordsList.size(); i++) {
            recordsData item = recordsList.get(i);
            String itemContent = createPrintableContent(item);
            printableContent.append(itemContent).append("\n");
        }

        return printableContent.toString();
    }

    private String createPrintableContent(recordsData item) {
        // Create a formatted string that represents the content of the item.
        // You can access the properties of the item and format them as needed.
        // Example: return "Item content: " + item.toString();
        return "Location: " + item.getTitle() + ", Description: " + item.getDescription() + ", Date Of completion: "+ item.getDate()+", Time of Completion: "+item.getTime()+", Contact N0: 17850487"; // Format as needed.
    }

}
