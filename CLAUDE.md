# Trip Planner (MobilDevProj)

Android app (Java + XML layouts, Material 3) for planning a group trip and splitting its cost.

## What the app does
- A host creates a trip with a description and a total cost.
- The host invites people. Invitees confirm in the app whether they are going.
- The cost is split **evenly between everyone who confirmed, and the host counts as one person**.
  Example: $100 total, host + 3 confirmed = 4 people = $25 each. The per-person cost changes as people confirm.
- Paying is an honor system: each person marks themselves as paid or not. No real payments.

## Team roles
- **Frontend (UI/UX):** Ameen. Screens, layouts, styles.
- **Backend:** a teammate. Accounts, trips, invites, confirmations, paid status.
- Keep code simple and readable so either person (and their AI assistant) can follow it.

## Design reference
Wireframes for an app called "Tally" (an expense splitter) are used for **look and layout only**. The product is the trip planner described above, so ignore the wireframes' exact/percent splits and settle-up engine. Style: one green accent, grey filled inputs, light theme only.

## Project layout (`app/src/main/`)
- `java/com/example/mobildevproj/`
  - `LoginActivity`, `SignUpActivity`, `HomeActivity`: the three screens built so far (UI only).
  - `TripAdapter`: fills the trips list on Home. `Money`: formats dollar amounts.
  - `data/`: **the backend hand-off point.** `AuthRepository` and `TripRepository` are on-device stand-ins.
    Replace their method bodies with the real backend and keep the method signatures; the UI should not need to change.
    (`AuthRepository` currently saves accounts in SharedPreferences, so accounts exist on one phone only.)
- `res/layout/`: screen layouts. `res/values/strings.xml`: all user-facing text. `colors.xml`, `styles.xml`, `themes.xml`: look and feel.

## Status
- Done: Sign in, Create account, Home (sample trips, owed/owe summary, bottom nav).
- Placeholders ("Coming soon"): New trip, Activity tab, Profile tab.
- Not done: create trip, invite/join, confirm going, paid/unpaid, landscape two-pane layout, dark theme.

## Build and run
- Open in Android Studio and press Run, or `gradlew.bat assembleDebug`.
- `minSdk = 36`: the app only installs on Android 16+ (consider lowering).
- If `gradlew` says JAVA_HOME is not set, use Android Studio's JDK: `C:\Program Files\Android\Android Studio\jbr`.

## Git
- Repo: https://github.com/Farva-mo/MobilDevProj (HTTPS). Default branch `main`.
- Work on feature branches (e.g. `feature/sign-in`) and merge via pull request. `git pull` before starting.
