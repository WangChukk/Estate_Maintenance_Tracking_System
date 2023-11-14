package com.example.final_emts;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button submit;
    private DatabaseReference databaseReference;
    private EditText requesterName, requestTitle, requesterLocation, requesterDescription;
    private Spinner mySpinner;

    public AddFragment() {
        // Required empty public constructor
    }

    public static AddFragment newInstance(String param1, String param2) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add, container, false);
        submit = view.findViewById(R.id.submitReqbtn);
        requesterName = view.findViewById(R.id.requesterName);
        requestTitle = view.findViewById(R.id.requesterTitle);
        requesterLocation = view.findViewById(R.id.requesterLocation);
        requesterDescription = view.findViewById(R.id.requesterDescription);
        mySpinner = view.findViewById(R.id.myspinner);

        // Create an array of items
        String[] items = {"Class Room", "Hostels", "Others"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(adapter);

        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if any of the fields are empty
                if (areFieldsEmpty()) {
                    // Display an error message
                    showToast("Please fill in all fields.");
                } else {
                    // Show the confirmation dialog
                    showConfirmationDialog();
                }
            }

            private boolean areFieldsEmpty() {
                return requesterName.getText().toString().isEmpty() ||
                        requestTitle.getText().toString().isEmpty() ||
                        requesterLocation.getText().toString().isEmpty() ||
                        requesterDescription.getText().toString().isEmpty();
            }
            private void showConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setMessage("Are You Sure You Want To Submit?")
                        .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String selectedItem = mySpinner.getSelectedItem().toString();
                                if ("Class Room".equals(selectedItem) || "Others".equals(selectedItem)) {
                                    addDataToFirebaseEM();
                                    showToast("Request Successfully Submitted To Estate Manager");
                                } else if ("Hostels".equals(selectedItem)) {
                                    addDataToFirebaseSSO();
                                    showToast("Request Successfully Submitted To SSO");
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Handle "Cancel" button click (e.g., cancel the operation)
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            }

            public void addDataToFirebaseEM() {
                String name = requesterName.getText().toString();
                String title = requestTitle.getText().toString();
                String location = requesterLocation.getText().toString();
                String description = requesterDescription.getText().toString();
                String category = mySpinner.getSelectedItem().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userEmail = user.getEmail();
                    // Create a unique key for this data
                    String key = databaseReference.child("MaintenanceRequestToEM").push().getKey();

                    // Create a Map to hold the data with appropriate key-value pairs
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("name", name);
                    dataMap.put("title", title);
                    dataMap.put("location", location);
                    dataMap.put("description", description);
                    dataMap.put("category", category);
                    dataMap.put("userEmail", userEmail);
                    databaseReference.child("MaintenanceRequestToEM").child(key).setValue(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            navigateToHomePageLoader();
                        }
                    });
                }
            }
            public void addDataToFirebaseSSO() {
                String name = requesterName.getText().toString();
                String title = requestTitle.getText().toString();
                String location = requesterLocation.getText().toString();
                String description = requesterDescription.getText().toString();
                String category = mySpinner.getSelectedItem().toString();

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String userEmail = user.getEmail();
                    // Create a unique key for this data
                    String key = databaseReference.child("MaintenanceRequestToSSO").push().getKey();

                    // Create a Map to hold the data with appropriate key-value pairs
                    Map<String, Object> dataMap = new HashMap<>();
                    dataMap.put("name", name);
                    dataMap.put("title", title);
                    dataMap.put("location", location);
                    dataMap.put("description", description);
                    dataMap.put("category", category);
                    dataMap.put("userEmail", userEmail);
                    databaseReference.child("MaintenanceRequestToSSO").child(key).setValue(dataMap);
                }
            }

            private void navigateToHomePageLoader() {
                Intent intent = new Intent(requireContext(), Home_Page_LoaderActivity.class);
                startActivity(intent);
            }

            private void showToast(String message) {
                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
