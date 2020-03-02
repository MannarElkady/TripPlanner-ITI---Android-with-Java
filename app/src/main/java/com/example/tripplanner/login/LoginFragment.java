package com.example.tripplanner.login;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.tripplanner.MainActivity;
import com.example.tripplanner.R;
import com.example.tripplanner.core.firestoredb.FirestoreConnection;
import com.example.tripplanner.homescreen.homeview.CurrentTripsHomeFragmentDirections;
import com.example.tripplanner.pasttrips.PastTripsFragmentDirections;
import com.example.tripplanner.reminder.NotificationHelper;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    private EditText email, password;
    private ProgressBar progressBar;

    private SignInButton googleSignInButton;
    private Button signInButton, registerButton;

    private final static int ID_HOME=2;
    private final static int ID_HISTORY=1;
    private final static int ID_PROFILE=3;
    private LoginViewModel loginViewModel;
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInOptions googleSignInOptions;

    MeowBottomNavigation buttomNavigation;
    private FirestoreConnection firestoreConnection;
    private final int GOOGLE_SIGN_IN_REQ_CODE = 123;



    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        getActivity().findViewById(R.id.buttom_nav).setVisibility(View.GONE);

        googleSignInButton = view.findViewById(R.id.googleSignInButton);
        signInButton = view.findViewById(R.id.signInButton);
        registerButton = view.findViewById(R.id.registerButton);
        progressBar = view.findViewById(R.id.progressBar);

        email = view.findViewById(R.id.emailTextInput);
        password = view.findViewById(R.id.passwordTextInput);

        ((TextView)googleSignInButton.getChildAt(0)).setText("Sign In With Google");

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQ_CODE);

            }
        });

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userEmail = email.getText().toString();
                String userPassword = password.getText().toString();
                if(userEmail.trim().equals("")){
                    Toast.makeText(LoginFragment.this.getActivity(), "Enter Email", Toast.LENGTH_LONG).show();
                }
                else if(userPassword.trim().equals("")){
                    Toast.makeText(LoginFragment.this.getActivity(), "Enter Password", Toast.LENGTH_LONG).show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    loginViewModel.loginWithEmailAndPassword(userEmail, userPassword, LoginFragment.this.getActivity(), progressBar);
                }
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to RegisterFragment
                Navigation.findNavController(getActivity(),R.id.fragments_functionality_layout).navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginViewModel = new ViewModelProvider(requireActivity()).get(LoginViewModel.class);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this.getContext(), googleSignInOptions);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_REQ_CODE){
            loginViewModel.loginWithGoogle(data, this.getActivity());
        }
    }

    /*Manar*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //buttomNavigation = getActivity().findViewById(R.id.buttom_nav);
        //buttomNavigation.setVisibility(View.VISIBLE);
    }
    /*Manar*/
}
