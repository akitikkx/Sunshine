package com.ahmedtikiwa.sunshine.app;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.ui.PlacePicker;

/**
 * Created by Ahmed on 2017/01/10.
 */

public class LocationEditTextPreference extends EditTextPreference {

    private static final int DEFAULT_MIN_LOCATION_LENGTH = 2;
    private int mMinimumLength;

    public LocationEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.LocationEditTextPreference, 0, 0);
        try {
            mMinimumLength = typedArray.getInteger(R.styleable.LocationEditTextPreference_minLength, DEFAULT_MIN_LOCATION_LENGTH);
        } finally {
            typedArray.recycle();
        }

        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode == ConnectionResult.SUCCESS) {
            setWidgetLayoutResource(R.layout.pref_current_location);
        }
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        View view = super.onCreateView(parent);
        View currentLocation = view.findViewById(R.id.current_location);
        currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();

                // Launch the Place Picker so that the user can specify their location, and then
                // return the result to SettingsActivity.
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();


                // We are in a view right now, not an activity. So we need to get ourselves
                // an activity that we can use to start our Place Picker intent. By using
                // SettingsActivity in this way, we can ensure the result of the Place Picker
                // intent comes to the right place for us to process it.
                Activity settingsActivity = (SettingsActivity) context;
                try {
                    settingsActivity.startActivityForResult(
                            builder.build((SettingsActivity)getContext()), SettingsActivity.PLACE_PICKER_REQUEST);

                } catch (GooglePlayServicesNotAvailableException
                        | GooglePlayServicesRepairableException e) {
                    // What did you do?? This is why we check Google Play services in onResume!!!
                    // The difference in these exception types is the difference between pausing
                    // for a moment to prompt the user to update/install/enable Play services vs
                    // complete and utter failure.
                    // If you prefer to manage Google Play services dynamically, then you can do so
                    // by responding to these exceptions in the right moment. But I prefer a cleaner
                    // user experience, which is why you check all of this when the app resumes,
                    // and then disable/enable features based on that availability.
                }
            }
        });

        return view;
    }

    @Override
    protected void showDialog(Bundle state) {
        super.showDialog(state);

        EditText editText = getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Dialog dialog = getDialog();
                if (dialog instanceof AlertDialog) {
                    AlertDialog alertDialog = (AlertDialog) dialog;
                    Button positiveBtn = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    if (editable.length() < mMinimumLength) {
                        positiveBtn.setEnabled(false);
                    } else {
                        positiveBtn.setEnabled(true);
                    }
                }
            }
        });
    }
}
