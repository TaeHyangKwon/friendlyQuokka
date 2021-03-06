package kr.ac.kumoh.s20180073.myapplication.ui.day;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

import kr.ac.kumoh.s20180073.myapplication.R;
import kr.ac.kumoh.s20180073.myapplication.TimeViewModel;
import petrov.kristiyan.colorpicker.ColorPicker;

public class TimeActivity extends AppCompatActivity {

    private TimeViewModel timeViewModel;

    TextView startTime;
    TextView endTime;
    boolean inputStart = false;
    boolean inputEnd = false;
    int date, starthour, startminute, endhour, endeminute, gaphour, gapminute, setcolor;
    String work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        timeViewModel = ViewModelProviders.of(this).get(TimeViewModel.class);

        startTime = findViewById(R.id.startTime);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
                inputStart = true;
            }
        });

        endTime = findViewById(R.id.endTime);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTime();
                inputEnd = true;
            }
        });

        final Spinner workSpinner = findViewById(R.id.workSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner_work, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workSpinner.setAdapter(adapter);

        workSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                work = workSpinner.getSelectedItem().toString();
                //Toast.makeText(TimeActivity.this, work, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(TimeActivity.this, "????????? ?????? ??? ?????????!", Toast.LENGTH_LONG).show();
            }
        });

        Button colorButton = findViewById(R.id.colorButton);
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openColorPicker();
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //????????? ????????? ??????
                Calendar calendar = new GregorianCalendar(Locale.KOREA);
                date = calendar.get(Calendar.DAY_OF_MONTH);
                //Toast.makeText(TimeActivity.this, Integer.toString(date), Toast.LENGTH_LONG).show();
                Toast.makeText(TimeActivity.this, Integer.toString(starthour), Toast.LENGTH_LONG).show();
                timeViewModel.requestList(Integer.toString(date), Integer.toString(starthour), Integer.toString(startminute), Integer.toString(endhour), Integer.toString(endeminute), Integer.toString(gaphour), Integer.toString(gapminute), work, Integer.toString(setcolor));

                finish();
            }
        });
    }

    void setTime(){
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR);
        int mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                if(inputStart == true) {
                    startTime.setText(String.format("%02d:%02d", hour, minute));
                    starthour = hour;
                    startminute = minute;
                    inputStart = false;
                }
                else if(inputEnd==true) {
                    endTime.setText(String.format("%02d:%02d", hour, minute));
                    endhour = hour;
                    endeminute = minute;
                    inputEnd = false;
                    setGap();
                }
            }
        }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    void setGap(){
        TextView gap = findViewById(R.id.gap);

        if(endhour >= starthour){
            gaphour = endhour - starthour;
            gapminute = endeminute - startminute;

            if(endhour == starthour) {
                if (endeminute >= startminute) {
                    gap.setText(String.format("%02d:%02d", gaphour, gapminute));
                }
            }
            else{
                gap.setText(String.format("%02d:%02d", gaphour, gapminute));

                if (endeminute < startminute){
                    gaphour = gaphour - 1;
                    gapminute = 60 - Math.abs(endeminute - startminute);
                    gap.setText(String.format("%02d:%02d", gaphour, gapminute));
                }
            }
        }
    }

    void openColorPicker(){
        final ColorPicker colorPicker = new ColorPicker(this);
        ArrayList<String> colors = new ArrayList<>();

        colors.add("#e6ee9c");
        colors.add("#fff59d");
        colors.add("#ffe2b7");
        colors.add("#ffe082");
        colors.add("#dcb9ff");
        colors.add("#ffccff");
        colors.add("#afd7ff");
        colors.add("#ff7d7d");
        colors.add("#d0cece");
        colors.add("#595959");

        colorPicker.setColors(colors).setColumns(4).setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        //ok ?????? ?????? ??? ?????????
                        //DayFragment??? ????????? ??? ???????????? ???????
                        Intent intent = new Intent();
                        intent.putExtra("color", color);
                        setResult(RESULT_OK, intent);
                        setcolor = color;
                        //Toast.makeText(TimeActivity.this, Integer.toString(color), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }
}