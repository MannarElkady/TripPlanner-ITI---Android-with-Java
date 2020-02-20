package com.example.tripplanner.register;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.example.tripplanner.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private final String TAG = "RegisterViewModel";
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public void registerWithEmailAndPassword(String email, String password, Activity activity){
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            Log.i(TAG, user.getEmail());
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                        }
                    }
                });
    }

}
