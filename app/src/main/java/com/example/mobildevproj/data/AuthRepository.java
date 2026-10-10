package com.example.mobildevproj.data;

import android.content.Context;
import android.content.SharedPreferences;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;
import java.util.Locale;

/**
 * BACKEND STAND-IN for sign in / create account.
 *
 * This is a temporary, on-device version so the screens can be tested before the real backend
 * exists. Accounts are saved in this phone's SharedPreferences only, so they are NOT shared
 * between devices.
 *
 * FOR THE BACKEND DEVELOPER: the UI only ever calls the 5 public methods below
 * (signUp, signIn, signOut, isSignedIn, getDisplayName). Re-implement those with the real
 * backend (Firebase, REST API, etc.) and keep the same method signatures, and no UI code has
 * to change. If a call needs to be asynchronous, tell the UI developer and we will add a callback.
 */
public class AuthRepository {

    /** What signUp / signIn give back: either success, or an error message to show the user. */
    public static class Result {
        public final boolean success;
        public final String errorMessage; // null when success is true

        private Result(boolean success, String errorMessage) {
            this.success = success;
            this.errorMessage = errorMessage;
        }

        static Result ok() {
            return new Result(true, null);
        }

        static Result fail(String message) {
            return new Result(false, message);
        }
    }

    private static final String PREFS_NAME = "auth_prefs";
    private static final String KEY_SIGNED_IN_EMAIL = "signed_in_email";

    private final SharedPreferences prefs;

    public AuthRepository(Context context) {
        prefs = context.getApplicationContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /** Creates a new account and signs the user in. Fails if the email is already used. */
    public Result signUp(String displayName, String email, String password) {
        String id = normalize(email);
        if (prefs.contains(passwordKey(id))) {
            return Result.fail("An account with this email already exists.");
        }
        prefs.edit()
                .putString(nameKey(id), displayName.trim())
                .putString(passwordKey(id), hash(password))
                .putString(KEY_SIGNED_IN_EMAIL, id)
                .apply();
        return Result.ok();
    }

    /** Signs in with an existing account. Fails if the email or password is wrong. */
    public Result signIn(String email, String password) {
        String id = normalize(email);
        String savedHash = prefs.getString(passwordKey(id), null);
        if (savedHash == null || !savedHash.equals(hash(password))) {
            return Result.fail("Incorrect email or password.");
        }
        prefs.edit().putString(KEY_SIGNED_IN_EMAIL, id).apply();
        return Result.ok();
    }

    public void signOut() {
        prefs.edit().remove(KEY_SIGNED_IN_EMAIL).apply();
    }

    /** True if someone is signed in. This is how the app skips the sign-in screen on launch. */
    public boolean isSignedIn() {
        return prefs.getString(KEY_SIGNED_IN_EMAIL, null) != null;
    }

    /** The signed-in user's display name, or "" if nobody is signed in. */
    public String getDisplayName() {
        String id = prefs.getString(KEY_SIGNED_IN_EMAIL, null);
        return id == null ? "" : prefs.getString(nameKey(id), "");
    }

    // ---- private helpers (not part of what the UI uses) ----

    private static String normalize(String email) {
        return email.trim().toLowerCase(Locale.ROOT);
    }

    private static String nameKey(String id) {
        return "name:" + id;
    }

    private static String passwordKey(String id) {
        return "password:" + id;
    }

    /** One-way hash so the plain password is never saved. A real backend should do this server-side. */
    private static String hash(String password) {
        try {
            byte[] bytes = MessageDigest.getInstance("SHA-256")
                    .digest(password.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(bytes);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }
}
