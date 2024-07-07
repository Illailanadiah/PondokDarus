package com.example.pondokdarus;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Student {
    private String fullname;
    private String icNum;
    private String form;

    public Student() {
        // Default constructor required for calls to DataSnapshot.getValue(Student.class)
    }

    public Student(String fullname, String icNum, String form) {
        this.fullname = fullname;
        this.icNum = icNum;
        this.form = form;
    }

    public String getFullname() {
        return fullname;
    }

    public String getIcNum() {
        return icNum;
    }

    public String getForm() {
        return form;
    }

    public static void fetchStudentData(String studentId, FirestoreCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("students").document(studentId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Student student = document.toObject(Student.class);
                            callback.onCallback(student);
                        } else {
                            callback.onCallback(null);
                        }
                    } else {
                        callback.onCallback(null);
                    }
                });
    }

    public interface FirestoreCallback {
        void onCallback(Student student);
    }
}
