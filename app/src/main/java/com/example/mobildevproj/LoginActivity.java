package com.example.mobildevproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobildevproj.data.AuthRepository;

/** Screen 1: Sign in. This is the screen the app opens on, unless someone is already signed in. */
public class LoginActivity extends AppCompatActivity {

    private AuthRepository auth;
    private EditText emailInput;
    private EditText passwordInput;
    private TextView errorText;

    /** Opens the sign-in screen and clears everything behind it (used after signing out). */
    public static void openFresh(Activity from) {
        Intent intent = new Intent(from, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = new AuthRepository(this);

        // Already signed in from a previous launch: go straight to the home screen.
        if (auth.isSignedIn()) {
            HomeActivity.openFresh(this);
            return;
        }

        setContentView(R.layout.activity_login);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        errorText = findViewById(R.id.text_error);

        findViewById(R.id.button_sign_in).setOnClickListener(v -> trySignIn());
        findViewById(R.id.link_create_account).setOnClickListener(v ->
                startActivity(new Intent(this, SignUpActivity.class)));

        // Pressing "Done" on the keyboard also signs in.
        passwordInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                trySignIn();
                return true;
            }
            return false;
        });
    }

    private void trySignIn() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.error_fill_all_fields));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(getString(R.string.error_invalid_email));
            return;
        }

        AuthRepository.Result result = auth.signIn(email, password);
        if (result.success) {
            HomeActivity.openFresh(this);
        } else {
            showError(result.errorMessage);
        }
    }

    private void showError(String message) {
        errorText.setText(message);
        errorText.setVisibility(TextView.VISIBLE);
    }
}
