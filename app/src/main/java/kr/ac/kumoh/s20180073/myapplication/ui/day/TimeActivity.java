package kr.ac.kumoh.s20180073.myapplication.ui.day;

import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

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

import kr.ac.kumoh.s20180073.myapplication.R;
import petrov.kristiyan.colorpicker.ColorPicker;

public class TimeActivity extends AppCompatActivity {

    private DayViewModel dayViewModel;

    TextView startTime;
    TextView endTime;
    boolean inputStart = false;
    boolean inputEnd = false;
    int starthour, startminute, endhour, endeminute, gaphour, gapminute;
    String select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        //dayViewModel = ViewModelProviders.of(this).get(DayViewModel.class);

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
                select = workSpinner.getSelectedItem().toString();
                //Toast.makeText(TimeActivity.this, select, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(TimeActivity.this, "일정을 선택 해 주세요!", Toast.LENGTH_LONG).show();
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
                //저장버튼 눌렀을 때 아마 디비에 저장?
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
                        //ok 버튼 클릭 시 이벤트
                    }

                    @Override
                    public void onCancel() {

                    }
                }).show();
    }
}