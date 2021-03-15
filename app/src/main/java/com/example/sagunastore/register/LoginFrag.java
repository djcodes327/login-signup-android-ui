package com.example.sagunastore.register;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagunastore.HomeActivity;
import com.example.sagunastore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import static com.example.sagunastore.register.RegisterActivity.onResetPasswordFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFrag extends Fragment {

    public LoginFrag() {
        // Required empty public constructor
    }

    //Objects Declarations
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginInButton;
    private TextView dontHaveAccount;
    private TextView forgotPassword;
    private FrameLayout parentFrameLayout;
    ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        dontHaveAccount = view.findViewById(R.id.dont_have_account_textview);
        forgotPassword = view.findViewById(R.id.forget_password_textview);
        parentFrameLayout = getActivity().findViewById(R.id.Register_frame_layout);
        editTextEmail = view.findViewById(R.id.login_email);
        editTextPassword = view.findViewById(R.id.login_password);
        loginInButton = view.findViewById(R.id.login_button);
        progressBar = view.findViewById(R.id.progressbar1);
        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        loginInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        dontHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new SignUpFrag());
            }
        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResetPasswordFragment = true;
                setFragment(new forgetPasswordFragment());
            }
        });
    }

    private void loginUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();


        //Email Criteria
        if(email.isEmpty()) {
            editTextEmail.setError("Email is Required");
            editTextEmail.requestFocus();
            return;
        }
        //Checking a Valid E-Mail
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please Enter A Valid E-Mail Address");
            editTextEmail.requestFocus();
            return;
        }
        //Checking if Field is Blank
        else if(password.isEmpty()) {
            editTextPassword.setError("Password is Required !!!");
            editTextPassword.requestFocus();
            return;
        }
        //Password Criteria
        else if(password.length()<8) {
            editTextPassword.setError("Enter a Password of Minimum 8 Characters !!");
            editTextPassword.requestFocus();
            return;
        }

        else {

            progressBar.setVisibility(View.VISIBLE);
            loginInButton.setEnabled(false);

            //Authentication
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        StyleableToast.makeText(getActivity(),"Logging In.....",R.style.CustomTheme).show();
                        Intent mainIntent = new Intent(getActivity(), HomeActivity.class);
                        startActivity(mainIntent);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        getActivity().finish();
                    }
                    else {
                        loginInButton.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                        StyleableToast.makeText(getActivity(),task.getException().getMessage(),R.style.CustomTheme).show();
                    }
                }
            });

        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_right,R.anim.slideout_from_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
