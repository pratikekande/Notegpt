package com.project1.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.project1.configuration.FirebaseInitialization;
import com.project1.model.Note;

public class NotesDao {
    
    public static Firestore db;

    static{
        db = FirebaseInitialization.getFireStore();
    }

    public void addData(String collection, String document, Note data)
    throws InterruptedException, ExecutionException{
        DocumentReference docRef = db.collection(collection).document(document);
        ApiFuture<WriteResult> result = docRef.set(data);
        result.get();
    }

    public List<Note> getDataList(String collection, String userName)
    throws ExecutionException, InterruptedException{

        try{
            CollectionReference colRef = db.collection(collection);

            // Query to filter notes where userName field matches the provided userName

            Query query = colRef.whereEqualTo("userName", userName);
            ApiFuture<QuerySnapshot> querySnapshotFuture = query.get();
            QuerySnapshot querySnapshot = querySnapshotFuture.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
            List<Note> noteList = new ArrayList<>();

            for (QueryDocumentSnapshot document : documents) {
                Note note = document.toObject(Note.class);
                noteList.add(note);
            }


            return noteList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }

        
        
    }
}
