package com.example.mobildevproj;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobildevproj.data.AuthRepository;

/** Screen 2: Create account. */
public class SignUpActivity extends AppCompatActivity {

    private static final int MIN_PASSWORD_LENGTH = 8;

    private AuthRepository auth;
    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;
    private TextView errorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth = new AuthRepository(this);
        nameInput = findViewById(R.id.input_display_name);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        confirmPasswordInput = findViewById(R.id.input_confirm_password);
        errorText = findViewById(R.id.text_error);

        findViewById(R.id.link_back).setOnClickListener(v -> finish());
        findViewById(R.id.button_create_account).setOnClickListener(v -> tryCreateAccount());

        // Pressing "Done" on the keyboard also creates the account.
        confirmPasswordInput.setOnEditorActionListener((view, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                tryCreateAccount();
                return true;
            }
            return false;
        });
    }

    private void tryCreateAccount() {
        String name = nameInput.getText().toString().trim();
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirm = confirmPasswordInput.getText().toString();

        // Check the form in the same order the fields appear, and show the first problem.
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
            showError(getString(R.string.error_fill_all_fields));
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(getString(R.string.error_invalid_email));
            return;
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            showError(getString(R.string.error_password_short));
            return;
        }
        if (!password.equals(confirm)) {
            showError(getString(R.string.error_passwords_dont_match));
            return;
        }

        AuthRepository.Result result = auth.signUp(name, email, password);
        if (result.success) {
            HomeActivity.openFresh(this);
        } else {
            showError(result.errorMessage);
        }
    }

    private void showError(String message) {
        errorText.setText(message);
        errorText.setVisibility(View.VISIBLE);
    }
}
