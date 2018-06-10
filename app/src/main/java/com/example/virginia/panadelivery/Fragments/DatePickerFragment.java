package com.example.virginia.panadelivery.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import com.example.virginia.panadelivery.Activities.RegisterActivity;
import com.example.virginia.panadelivery.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatePickerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
        String fecha;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpl = new DatePickerDialog(getActivity(), this, year, month, day);

        // Create a new instance of DatePickerDialog and return it
        return dpl;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        fecha = Integer.toString(dayOfMonth);
        fecha = fecha + "/" + Integer.toString(month) + "/" + Integer.toString(year);
        ((RegisterActivity) getActivity()).setEditTextFecha(fecha);

    }
}