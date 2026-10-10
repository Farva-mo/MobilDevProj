package com.example.mobildevproj.data;

/**
 * One trip as shown in the home screen list.
 *
 * Cost rule: the host sets a total cost and it is split evenly between everyone going
 * (the host counts as one person), so costPerPerson = totalCost / goingCount.
 */
public class Trip {
    public final String name;
    public final int goingCount;        // people who confirmed, including the host
    public final double costPerPerson;  // in dollars
    public final double balance;        // > 0: others owe you, < 0: you owe, 0: all paid up

    public Trip(String name, int goingCount, double costPerPerson, double balance) {
        this.name = name;
        this.goingCount = goingCount;
        this.costPerPerson = costPerPerson;
        this.balance = balance;
    }
}
