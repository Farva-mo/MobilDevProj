package com.example.mobildevproj;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobildevproj.data.Trip;

import java.util.List;

/** Fills the home screen list: one item_trip.xml row per Trip. */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.TripViewHolder> {

    private final List<Trip> trips;

    public TripAdapter(List<Trip> trips) {
        this.trips = trips;
    }

    @NonNull
    @Override
    public TripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip, parent, false);
        return new TripViewHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull TripViewHolder holder, int position) {
        Trip trip = trips.get(position);

        holder.name.setText(trip.name);
        holder.subtitle.setText(holder.itemView.getContext().getString(
                R.string.trip_subtitle, trip.goingCount, Money.plain(trip.costPerPerson)));

        holder.balance.setText(Money.signed(trip.balance));
        int colorRes = trip.balance < 0 ? R.color.money_negative
                : trip.balance > 0 ? R.color.money_positive
                : R.color.text_secondary;
        holder.balance.setTextColor(holder.itemView.getContext().getColor(colorRes));
    }

    @Override
    public int getItemCount() {
        return trips.size();
    }

    static class TripViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final TextView subtitle;
        final TextView balance;

        TripViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.text_trip_name);
            subtitle = itemView.findViewById(R.id.text_trip_subtitle);
            balance = itemView.findViewById(R.id.text_trip_balance);
        }
    }
}
