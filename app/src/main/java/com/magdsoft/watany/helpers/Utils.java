package com.magdsoft.watany.helpers;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.magdsoft.watany.R;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.regex.Pattern;



public final class Utils {

    public static int color(Context context, int id) {
        return ContextCompat.getColor(context, id);
    }

    public static String string(Context context, int id) {
        return context.getString(id);
    }

    public static float dimen(Context context, int id) {
        return context.getResources().getDimension(id);
    }


    public static GaroApplication getApiApplication(Context context) {
        return ((GaroApplication) context.getApplicationContext());
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void startActivity(Context context, Class c) {
        context.startActivity(new Intent(context, c));
    }

    public static void startActivityForResult(Activity activity, Class c, int resultCode) {
        activity.startActivityForResult(new Intent(activity, c), resultCode);
    }

    public static void startActivity(Context context, Class c, Integer... flags) {
        Intent intent = new Intent(context, c);
        for (Integer i : flags) {
            intent.addFlags(i);
        }
        context.startActivity(intent);
    }

    public static boolean isServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static <T> T fragment(AppCompatActivity context, int id) {
        return (T) context.getSupportFragmentManager().findFragmentById(id);
    }

    /* Read and write objects. */
    public static void writeObject(Context context, Object object, String filename) {
        ObjectOutputStream objectOut = null;
        try {
            FileOutputStream fileOut = context.openFileOutput(filename, Activity.MODE_PRIVATE);
            objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(object);
            fileOut.getFD().sync();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOut != null) {
                try {
                    objectOut.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }
    }

    /**
     * @param context
     * @param filename
     * @return
     */
    public static <T> T readObject(Context context, String filename) {
        ObjectInputStream objectIn = null;
        Object object = null;
        try {
            FileInputStream fileIn = context.getApplicationContext().openFileInput(filename);
            objectIn = new ObjectInputStream(fileIn);
            object = objectIn.readObject();

        } catch (FileNotFoundException e) {
            // Do nothing
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (objectIn != null) {
                try {
                    objectIn.close();
                } catch (IOException e) {
                    // do nowt
                }
            }
        }

        try {
            return (T) object;
        } catch (ClassCastException e) {
            return null;
        }
    }

    public static boolean isValidName(CharSequence s) {
        return !TextUtils.isEmpty(s) && Pattern.compile("[a-zA-Z ]*").matcher(s).matches();
    }

    public static boolean isValidEmail(CharSequence s) {
        return !TextUtils.isEmpty(s) && android.util.Patterns.EMAIL_ADDRESS.matcher(s).matches();
    }

    public static boolean isValidPhoneNumber(CharSequence s) {
        return !TextUtils.isEmpty(s) && android.util.Patterns.PHONE.matcher(s).matches();
    }

    public static boolean isValidPassword(CharSequence s) {

        return s.length() >= 5;
    }

    public static boolean isNotConnected(Throwable t) {
        return null == t.getMessage()
                || t.getMessage().contains("No address associated with hostname")
                || t.getMessage().contains("connect timed out")
                || t.getMessage().contains("timeout");
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static Bitmap getBitmap(VectorDrawable vectorDrawable) {
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(),
                vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        } else if (drawable instanceof VectorDrawable) {
            return getBitmap((VectorDrawable) drawable);
        } else {
            throw new IllegalArgumentException("unsupported drawable type");
        }
    }

    public static void snackbar(Context context, @StringRes int id) {
        Snackbar.make(((Activity) context).findViewById(android.R.id.content), id, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackbar(Activity activity, @StringRes int id) {
        Snackbar.make(activity.findViewById(android.R.id.content), id, Snackbar.LENGTH_SHORT).show();
    }

    public static void snackbar(Activity activity, String s) {
        Snackbar.make(activity.findViewById(android.R.id.content), s, Snackbar.LENGTH_SHORT).show();
    }

    public static Snackbar errorSnackbar(Activity activity, @StringRes int id, View.OnClickListener action) {
        return Snackbar.make(activity.findViewById(android.R.id.content), id, Snackbar.LENGTH_SHORT)
                .setAction(R.string.retry, action)
                .setActionTextColor(activity.getResources().getColor(R.color.error))
                .setDuration(8000);

    }

    public static Snackbar errorSnackbar(Context context, @StringRes int id, View.OnClickListener action) {
        return Snackbar.make(((Activity) context).findViewById(android.R.id.content), id, Snackbar.LENGTH_SHORT)
                .setAction(R.string.retry, action)
                .setActionTextColor(context.getResources().getColor(R.color.error))
                .setDuration(8000);
    }

    public static boolean isNetworkAvailable(Context context) {
        boolean netstate = false;
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {

            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {

                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {

                        netstate = true;
                        break;
                    }
                }
            }
        }
        return netstate;
    }

}
