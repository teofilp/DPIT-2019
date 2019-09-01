package com.runtime_terror.myapplication.database;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class FirestoreSetup {
    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static CollectionReference rootRef = db.collection("RESTAURANTS");

    public FirebaseFirestore getDb() {
        return db;
    }

    public CollectionReference getRootRef() {
        return rootRef;
    }
}
