package com.skarapedulbuk.mysimplenotes.domain;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirebaseRepo implements TasksRepository {

    private static final String TASKS = "tasks";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String ISDONE = "isDone";


    private final FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    public void getTasks(Callback<List<MyTask>> callback) {
        db.collection(TASKS)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            ArrayList<MyTask> result = new ArrayList<>();

                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String id = document.getId();
                                String title = document.getString(TITLE);
                                String description = document.getString(DESCRIPTION);
                                Boolean isDone = document.getBoolean(ISDONE);

                                result.add(new MyTask(id, title, description, isDone));

                                //   Log.d(TAG, document.getId() + " => " + document.getData());
                            }
                            callback.onSuccess(result);

                        } else {
                            // Log.w(TAG, "Error getting documents.", task.getException());
                            callback.onError(task.getException());
                        }
                    }
                });
    }

    @Override
    public void clear(Callback<Void> callback) {
      /*  db.collection(TASKS)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
db.batch()
                    }
                });
    */}

    @Override
    public void add(String title, String description, Boolean isDone, Callback<MyTask> callback) {
        Map<String, Object> data = new HashMap<>();
        data.put(TITLE, title);
        data.put(DESCRIPTION, description);
        data.put(ISDONE, isDone);

        db.collection(TASKS)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        //  Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        MyTask myTask = new MyTask(documentReference.getId(), title, description, isDone);
                        callback.onSuccess(myTask);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //   Log.w(TAG, "Error adding document", e);
                        callback.onError(e);
                    }
                });

    }

    @Override
    public void edit(String id, String title, String description, Boolean isDone, Callback<MyTask> callback) {
        Map<String, Object> data = new HashMap<>();

        data.put(TITLE, title);
        data.put(DESCRIPTION, description);
        data.put(ISDONE, isDone);

        db.collection(TASKS)
                .document(id)
                .update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(new MyTask(id, title, description, isDone));
                        } else {
                            callback.onError(task.getException());
                        }

                    }
                });
    }

    @Override
    public void delete(MyTask myTask, Callback<MyTask> callback) {
        db.collection(TASKS)
                .document(myTask.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccess(null);
                        } else {
                            callback.onError(task.getException());
                        }
                    }
                });
    }
}
