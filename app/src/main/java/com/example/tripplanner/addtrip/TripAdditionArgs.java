package com.example.tripplanner.tripdetail;

import android.os.Bundle;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.navigation.NavArgs;
import com.example.tripplanner.core.model.Trip;
import java.io.Serializable;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;

public class TripAdditionArgs implements NavArgs {
    private final HashMap arguments = new HashMap();

    private TripAdditionArgs() {
    }

    private TripAdditionArgs(HashMap argumentsMap) {
        this.arguments.putAll(argumentsMap);
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static TripAdditionArgs fromBundle(@NonNull Bundle bundle) {
        TripAdditionArgs __result = new TripAdditionArgs();
        bundle.setClassLoader(TripAdditionArgs.class.getClassLoader());
        if (bundle.containsKey("tripDataForEdit")) {
            Trip tripData;
            if (Parcelable.class.isAssignableFrom(Trip.class) || Serializable.class.isAssignableFrom(Trip.class)) {
                tripData = (Trip) bundle.get("tripDataForEdit");
            } else {
                throw new UnsupportedOperationException(Trip.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
            }
            if (tripData == null) {
                throw new IllegalArgumentException("Argument \"tripDataForEdit\" is marked as non-null but was passed a null value.");
            }
            __result.arguments.put("tripDataForEdit", tripData);
        } else {
            throw new IllegalArgumentException("Required argument \"tripData\" is missing and does not have an android:defaultValue");
        }
        return __result;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Trip getTripData() {
        return (Trip) arguments.get("tripDataForEdit");
    }

    @SuppressWarnings("unchecked")
    @NonNull
    public Bundle toBundle() {
        Bundle __result = new Bundle();
        if (arguments.containsKey("tripDataForEdit")) {
            Trip tripData = (Trip) arguments.get("tripDataForEdit");
            if (Parcelable.class.isAssignableFrom(Trip.class) || tripData == null) {
                __result.putParcelable("tripDataForEdit", Parcelable.class.cast(tripData));
            } else if (Serializable.class.isAssignableFrom(Trip.class)) {
                __result.putSerializable("tripDataForEdit", Serializable.class.cast(tripData));
            } else {
                throw new UnsupportedOperationException(Trip.class.getName() + " must implement Parcelable or Serializable or must be an Enum.");
            }
        }
        return __result;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        TripAdditionArgs that = (TripAdditionArgs) object;
        if (arguments.containsKey("tripDataForEdit") != that.arguments.containsKey("tripDataForEdit")) {
            return false;
        }
        if (getTripData() != null ? !getTripData().equals(that.getTripData()) : that.getTripData() != null) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (getTripData() != null ? getTripData().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TripAdditionArgs{"
                + "tripDataForEdit=" + getTripData()
                + "}";
    }

    public static class Builder {
        private final HashMap arguments = new HashMap();

        public Builder(TripAdditionArgs original) {
            this.arguments.putAll(original.arguments);
        }

        public Builder(@NonNull Trip tripData) {
            if (tripData == null) {
                throw new IllegalArgumentException("Argument \"tripData\" is marked as non-null but was passed a null value.");
            }
            this.arguments.put("tripDataForEdit", tripData);
        }

        @NonNull
        public TripAdditionArgs build() {
            TripAdditionArgs result = new TripAdditionArgs(arguments);
            return result;
        }

        @NonNull
        public Builder setTripData(@NonNull Trip tripData) {
            if (tripData == null) {
                throw new IllegalArgumentException("Argument \"tripData\" is marked as non-null but was passed a null value.");
            }
            this.arguments.put("tripDataForEdit", tripData);
            return this;
        }

        @SuppressWarnings("unchecked")
        @NonNull
        public Trip getTripData() {
            return (Trip) arguments.get("tripDataForEdit");
        }
    }
}
