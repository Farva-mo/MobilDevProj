# MobilDevProj

Group trip planner (Android, Java + XML). A host creates a trip with a description and total cost, invites people, and the cost is split evenly between everyone who confirms (host included). Paying is an honor system.

## Changes so far (branch `feature/sign-in`)

**Frontend (Ameen)**
- Sign in screen, Create account screen, and Home screen (list of trips), built from the wireframes.
- Create account checks: all fields filled, valid email, password of 8+ characters, passwords match.
- Stays signed in between launches. Tap the avatar on Home to sign out.
- Home shows "Owed to you" / "You owe" totals and a list of trips with people going and cost each.
- New trip button, Activity tab and Profile tab are placeholders ("Coming soon").

**For the backend developer**
- There is no real backend yet. `app/src/main/java/com/example/mobildevproj/data/` holds temporary stand-ins:
  - `AuthRepository`: `signUp`, `signIn`, `signOut`, `isSignedIn`, `getDisplayName`. Currently saves accounts on the phone only.
  - `TripRepository`: `getMyTrips()` returns 3 fake trips.
- Replace the inside of those methods with the real backend and keep the method names/signatures. The screens only call these, so the UI should not need to change.
- The app package is `com.example.mobildevproj`. If you use Firebase, register that name (or tell us if it should be renamed first).

## Not done yet
Create trip, invite/join, confirm going, paid/unpaid, landscape layout.

## Run it
Open in Android Studio and press Run. Note `minSdk = 36`, so the emulator/phone needs Android 16 or newer.

## Git
`git clone https://github.com/Farva-mo/MobilDevProj.git`, then `git checkout feature/sign-in` to see this work. Make a new branch for your own changes and open a pull request.
