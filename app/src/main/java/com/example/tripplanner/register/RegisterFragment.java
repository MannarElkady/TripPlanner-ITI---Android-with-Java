package com.example.tripplanner.register;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.tripplanner.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends Fragment {

    Button registerButton;
    EditText email, password, confirmPassword;

    private RegisterViewModel registerViewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        registerButton = view.findViewById(R.id.register_registerButton);
        email = view.findViewById(R.id.register_emailTextInput);
        password = view.findViewById(R.id.register_passwordTextInput);
        confirmPassword = view.findViewById(R.id.register_confirmPasswordTextInput);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(password.getText().toString().equals(confirmPassword.getText().toString())){
                    registerViewModel.registerWithEmailAndPassword(email.getText().toString(), password.getText().toString(), RegisterFragment.this.getActivity());
                }

            }
        });

        return view;
    }

}
