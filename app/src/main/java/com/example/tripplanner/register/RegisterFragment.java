package com.example.tripplanner.register;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tripplanner.R;
import com.example.tripplanner.login.LoginFragment;

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

        registerViewModel = new ViewModelProvider(requireActivity()).get(RegisterViewModel.class);

        registerButton = view.findViewById(R.id.register_registerButton);
        email = view.findViewById(R.id.register_emailTextInput);
        password = view.findViewById(R.id.register_passwordTextInput);
        confirmPassword = view.findViewById(R.id.register_confirmPasswordTextInput);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                String userConfirmPassword = confirmPassword.getText().toString();
                if(userEmail.trim().equals("")){
                    Toast.makeText(RegisterFragment.this.getActivity(), "Enter Email", Toast.LENGTH_LONG).show();
                }
                else if(userPassword.trim().equals("")){
                    Toast.makeText(RegisterFragment.this.getActivity(), "Enter Password", Toast.LENGTH_LONG).show();
                }
                else if(userConfirmPassword.trim().equals("")){
                    Toast.makeText(RegisterFragment.this.getActivity(), "Enter Confirm Password", Toast.LENGTH_LONG).show();
                }
                else {
                    if(userPassword.equals(userConfirmPassword)){
                        registerViewModel.registerWithEmailAndPassword(userEmail, userPassword, RegisterFragment.this.getActivity());
                    }
                    else{
                        Toast.makeText(RegisterFragment.this.getActivity(), "Password and Confirm Password are not similar", Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        return view;
    }

}
