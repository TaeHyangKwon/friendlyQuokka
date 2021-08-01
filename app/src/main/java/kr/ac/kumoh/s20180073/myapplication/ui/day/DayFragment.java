package kr.ac.kumoh.s20180073.myapplication.ui.day;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.Calendar;

import kr.ac.kumoh.s20180073.myapplication.R;

public class DayFragment extends Fragment {

    private DayViewModel dayViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dayViewModel =
                ViewModelProviders.of(this).get(DayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_day, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard);
        //dayViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            //@Override
            //public void onChanged(@Nullable String s) {
                //textView.setText(s);
            //}
        //});

        FloatingActionButton addButton = root.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimeActivity.class);
                startActivityForResult(intent, 101);
                //startActivity(new Intent(getContext(), TimeActivity.class));
            }
        });

        return root;
    }
}