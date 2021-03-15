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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.sagunastore.HomeActivity;
import com.example.sagunastore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoastlibrary.StyleableToast;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFrag extends Fragment {

    public SignUpFrag() {
        // Required empty public constructor
    }
    //Layouts
    private FrameLayout parentFrameLayout;
    //EditText Variables
    private ImageView imageView;
    private ProgressBar progressBar;
    private EditText editTextEmail;
    private EditText editTextFullname;
    private EditText editTextPhoneNumber;
    private EditText editTextPassword;
    private TextView haveAnAccount;
    private Button registerBtn;
    //Firebase Objects
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        haveAnAccount = view.findViewById(R.id.have_account_textview);
        parentFrameLayout = getActivity().findViewById(R.id.Register_frame_layout);

        //Logo and ProgressBar
        imageView = view.findViewById(R.id.logoname);
        progressBar = view.findViewById(R.id.progressbar2);

        //Edit Text Fields
        editTextEmail = view.findViewById(R.id.register_email);
        editTextFullname = view.findViewById(R.id.register_fullname);
        editTextPhoneNumber = view.findViewById(R.id.register_phoneNumber);
        editTextPassword = view.findViewById(R.id.register_password);

        //Button
        registerBtn = view.findViewById(R.id.register_button);

        //FireBase Authentication
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        haveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new LoginFrag());
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

    private void registerUser() {
        final String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        final String phoneNumber = editTextPhoneNumber.getText().toString().trim();
        final String fullName = editTextFullname.getText().toString().trim();

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
        //Mobile Number Criteria
        else if(phoneNumber.length() <= 9) {
            editTextPhoneNumber.setError("Please Enter a Valid Phone Number");
            editTextPhoneNumber.requestFocus();
        }
        //Full Name Criteria
        else if(fullName.isEmpty()) {
            editTextFullname.setError("Please Enter Your Name ");
            editTextFullname.requestFocus();
            return;
        }
        else {

            progressBar.setVisibility(View.VISIBLE);
            registerBtn.setEnabled(false);

            //Authentication
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if(task.isSuccessful()) {

                        Map<Object,String> userData = new HashMap<>();
                        userData.put("Full Name",fullName);
                        userData.put("Email",email);
                        userData.put("Phone Number",phoneNumber);

                        firebaseFirestore.collection("USERS ")
                                .add(userData)
                                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentReference> task) {
                                        if(task.isSuccessful()) {
                                            StyleableToast.makeText(getActivity(),"User Registered Successfully",R.style.CustomTheme).show();
                                            Intent mainIntent = new Intent(getActivity(), HomeActivity.class);
                                            startActivity(mainIntent);
                                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            getActivity().finish();
                                        }
                                        else {
                                            StyleableToast.makeText(getActivity(),task.getException().getMessage(),R.style.CustomTheme).show();
                                        }

                                    }
                                });

                    }
                    else {
                        registerBtn.setEnabled(true);
                        if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                            StyleableToast.makeText(getActivity(),"User is Already Registered !!!",R.style.CustomTheme).show();

                        } else {
                            StyleableToast.makeText(getActivity(),task.getException().getMessage(),R.style.CustomTheme).show();

                        }
                    }
                }
            });

        }
    }


}
