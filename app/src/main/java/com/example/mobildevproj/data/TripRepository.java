package com.example.mobildevproj.data;

import java.util.Arrays;
import java.util.List;

/**
 * BACKEND STAND-IN for the list of trips.
 *
 * FOR THE BACKEND DEVELOPER: replace the body of getMyTrips() with a real query that returns the
 * signed-in user's trips (as host or as an invited member). The home screen only needs this one
 * method and the Trip fields.
 */
public class TripRepository {

    /** Fake data so the home screen has something to show. */
    public List<Trip> getMyTrips() {
        return Arrays.asList(
                new Trip("Cocoa Beach trip", 5, 62.50, 250.00),
                new Trip("Mountain cabin weekend", 3, 80.00, -80.00),
                new Trip("Orlando day trip", 4, 35.00, 0.00));
    }
}
