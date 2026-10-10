package com.example.mobildevproj;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobildevproj.data.AuthRepository;
import com.example.mobildevproj.data.Trip;
import com.example.mobildevproj.data.TripRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

/** Screen 3: Home. Shows how much you are owed / owe overall, plus the list of your trips. */
public class HomeActivity extends AppCompatActivity {

    private AuthRepository auth;

    /** Opens the home screen and clears the sign-in screens behind it. */
    public static void openFresh(Activity from) {
        Intent intent = new Intent(from, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        from.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        auth = new AuthRepository(this);

        showAvatar();
        showTrips(new TripRepository().getMyTrips());
        setUpButtons();
    }

    /** The round avatar in the top corner shows the user's initials. Tapping it offers sign out. */
    private void showAvatar() {
        TextView avatar = findViewById(R.id.avatar);
        avatar.setText(initialsOf(auth.getDisplayName()));
        avatar.setOnClickListener(v -> confirmSignOut());
    }

    private void showTrips(List<Trip> trips) {
        // Totals for the summary card at the top.
        double owedToYou = 0;
        double youOwe = 0;
        for (Trip trip : trips) {
            if (trip.balance > 0) {
                owedToYou += trip.balance;
            } else {
                youOwe -= trip.balance; // balance is negative, so this adds
            }
        }
        ((TextView) findViewById(R.id.text_owed_to_you)).setText(Money.plain(owedToYou));
        ((TextView) findViewById(R.id.text_you_owe)).setText(Money.plain(youOwe));

        RecyclerView list = findViewById(R.id.list_trips);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(new TripAdapter(trips));
    }

    private void setUpButtons() {
        // Creating a trip isn't built yet.
        findViewById(R.id.button_new_trip).setOnClickListener(v -> showComingSoon());

        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_trips);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_trips) {
                return true;
            }
            // Activity and Profile screens aren't built yet; stay on Trips.
            showComingSoon();
            return false;
        });
    }

    private void confirmSignOut() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.sign_out_title)
                .setPositiveButton(R.string.sign_out, (dialog, which) -> {
                    auth.signOut();
                    LoginActivity.openFresh(this);
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void showComingSoon() {
        Toast.makeText(this, R.string.coming_soon, Toast.LENGTH_SHORT).show();
    }

    /** "Acsah L." -> "AL", "Sam" -> "S", "" -> "?" */
    private static String initialsOf(String displayName) {
        String[] words = displayName.trim().split("\\s+");
        StringBuilder initials = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty() && initials.length() < 2) {
                initials.append(Character.toUpperCase(word.charAt(0)));
            }
        }
        return initials.length() == 0 ? "?" : initials.toString();
    }
}
