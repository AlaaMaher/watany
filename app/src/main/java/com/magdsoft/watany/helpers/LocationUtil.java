package com.magdsoft.watany.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;

import com.magdsoft.watany.R;

import static android.content.Context.LOCATION_SERVICE;


public class LocationUtil {


    public static void checkGpsEnabled(Context mContext) {
        LocationManager service = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);
        boolean enabled = service
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

// check if enabled and if not send user to the GSP settings
// Better solution would be to display a dialog and suggesting to
// go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            mContext.startActivity(intent);
        }
    }

    public static String getCountryName(Context context) {
        return context.getResources().getConfiguration().locale.getDisplayCountry();
    }


    public static void makeLocationAlert(Activity activity, final int SETTINGS_REQUEST) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }
        builder.setTitle(R.string.location_missing)
                .setMessage(R.string.activate_location)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        activity.startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_REQUEST);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }


    public static String getAddressLine(Address address) {
        StringBuilder sb = new StringBuilder();
        if (address != null) {
            sb.append(address.getAdminArea());
            sb.append(" / ").append(address.getCountryName());
        }
        return sb.toString();
    }


}
