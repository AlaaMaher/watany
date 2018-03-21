package com.magdsoft.watany.helpers;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.magdsoft.watany.adapter.CustomHintAdapter;


public class CustomHintSpinner<T> {
    /*
 * Copyright (c) 2015 Sergio Rodrigo
 *
 * This software may be modified and distributed under the terms
 * of the MIT license. See the LICENSE file for details.
 */
    /**
     * Provides methods to work with a hint element.
     */

    private static final String TAG = me.srodrigo.androidhintspinner.HintSpinner.class.getSimpleName();
    private final Spinner spinner;
    private final CustomHintAdapter<T> adapter;
    private final me.srodrigo.androidhintspinner.HintSpinner.Callback<T> callback;

    public CustomHintSpinner(Spinner spinner, CustomHintAdapter<T> adapter, me.srodrigo.androidhintspinner.HintSpinner.Callback<T> callback) {
        this.spinner = spinner;
        this.adapter = adapter;
        this.callback = callback;
    }

    /**
     * Initializes the hint spinner.
     * <p>
     * By default, the hint is selected when calling this method.
     */
    public void init() {
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "position selected: " + position);
                if (CustomHintSpinner.this.callback == null) {
                    throw new IllegalStateException("callback cannot be null");
                }
                if (!isHintPosition(position)) {
                    Object item = CustomHintSpinner.this.spinner.getItemAtPosition(position);
                    CustomHintSpinner.this.callback.onItemSelected(position, (T) item);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "Nothing selected");
            }
        });
        selectHint();
    }

    private boolean isHintPosition(int position) {
        return position == adapter.getHintPosition();
    }

    /**
     * Selects the hint element.
     */
    public void selectHint() {
        spinner.setSelection(adapter.getHintPosition());
    }

    /**
     * Used to handle the spinner events.
     *
     * @param <T> Type of the data used by the spinner
     */
    public interface Callback<T> {
        /**
         * When a spinner item has been selected.
         *
         * @param position       Position selected
         * @param itemAtPosition Item selected
         */
        void onItemSelected(int position, T itemAtPosition);
    }
}


