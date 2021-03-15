package com.example.sagunastore.register;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sagunastore.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoastlibrary.StyleableToast;


/**
 * A simple {@link Fragment} subclass.
 */
public class forgetPasswordFragment extends Fragment {

    public forgetPasswordFragment() {
        // Required empty public constructor
    }

    private ImageView forgotPasswordImageView;
    private TextView forgottenPassword;
    private TextView DontWorryText;
    private EditText editTextForgotEmail;
    private Button resetPasswordButton;
    private TextView textViewGoBackToLogin;
    private FrameLayout parentFrameLayout;

    private ViewGroup linearLayout;
    private ImageView emailDeliveredIcon;
    private ImageView emailIconError;
    private TextView errorTextView;

    private FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password, container, false);
        parentFrameLayout = getActivity().findViewById(R.id.Register_frame_layout);
        editTextForgotEmail = view.findViewById(R.id.forgot_password_email);
        resetPasswordButton = view.findViewById(R.id.reset_password_button);
        textViewGoBackToLogin = view.findViewById(R.id.back_to_login_textview);
        forgotPasswordImageView = view.findViewById(R.id.forgot_password_image);
        forgottenPassword = view.findViewById(R.id.forgot_ur_password_textview);
        DontWorryText = view.findViewById(R.id.dont_worry_textview);
        linearLayout = view.findViewById(R.id.forgotPasswordLinearLayout);
        emailDeliveredIcon = view.findViewById(R.id.email_delivered_image);
        emailIconError = view.findViewById(R.id.email_delivering_image);
        errorTextView = view.findViewById(R.id.message_textview_forgotpassword);

        firebaseAuth = FirebaseAuth.getInstance();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextForgotEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                checkEmail();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        resetPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                resetPasswordButton.setEnabled(false);
                errorTextView.setVisibility(View.GONE);
                final String email = editTextForgotEmail.getText().toString().trim();
                if(email.isEmpty()) {
                    editTextForgotEmail.setError("Email is Required");
                    editTextForgotEmail.requestFocus();
                    return;
                }
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()) {
                            String message = "Recovery E-Mail Sent Successfully";
                            resetPasswordButton.setEnabled(false);
                            emailIconError.setVisibility(View.GONE);
                            emailDeliveredIcon.setVisibility(View.VISIBLE);
                            errorTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                            TransitionManager.beginDelayedTransition(linearLayout);
                            errorTextView.setText(message);
                            errorTextView.setVisibility(View.VISIBLE);

                            StyleableToast.makeText(getActivity(),"Reset Password Link Sent Successfully..!!!",R.style.CustomTheme).show();
                        }
                        else {
                            String error = task.getException().getMessage();
                            errorTextView.setTextColor(getResources().getColor(R.color.red_error));
                            errorTextView.setText(error);
                            emailIconError.setVisibility(View.VISIBLE);
                            TransitionManager.beginDelayedTransition(linearLayout);
                            errorTextView.setVisibility(View.VISIBLE);
                        }
                        resetPasswordButton.setEnabled(true);
                    }
                });
            }
        });
        textViewGoBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFragment(new LoginFrag());
            }
        });
    }

    private void checkEmail() {
        if(TextUtils.isEmpty(editTextForgotEmail.getText().toString().trim())) {
            resetPasswordButton.setEnabled(false);
        }
        else {
            resetPasswordButton.setEnabled(true);
        }
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_from_left,R.anim.slideout_from_right);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}
