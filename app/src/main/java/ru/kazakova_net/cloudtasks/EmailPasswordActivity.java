package ru.kazakova_net.cloudtasks;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EmailPasswordActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "CommonLogging";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private EditText mETEmail;
    private EditText mETPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_password);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                } else {
                    // User is signed out
                }
            }
        };

        mETEmail = findViewById(R.id.et_email);
        mETPassword = findViewById(R.id.et_password);

        mETEmail.setOnClickListener(EmailPasswordActivity.this);
        mETPassword.setOnClickListener(EmailPasswordActivity.this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_registration:
                createAccount(mETEmail.getText().toString(), mETPassword.getText().toString());
                break;
            case R.id.btn_sign_in:
                signIn(mETEmail.getText().toString(), mETPassword.getText().toString());
                break;
        }
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this, "Авторизация успешна",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            Toast.makeText(EmailPasswordActivity.this, "Авторизация провалена",
                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(EmailPasswordActivity.this, "Регистрация успешна",
                                    Toast.LENGTH_SHORT).show();

                            FirebaseUser user = mAuth.getCurrentUser();

                            updateUI(user);
                        } else {
                            Toast.makeText(EmailPasswordActivity.this, "Регистрация провалена",
                                    Toast.LENGTH_SHORT).show();

                            updateUI(null);
                        }
                    }
                });
    }
}
