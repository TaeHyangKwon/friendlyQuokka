package kr.ac.kumoh.s20180073.myapplication.ui.month;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import kr.ac.kumoh.s20180073.myapplication.DayViewModel;
import kr.ac.kumoh.s20180073.myapplication.R;

public class GraphActivity extends AppCompatActivity{

    private DayViewModel dayViewModel;
    int day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        dayViewModel = ViewModelProviders.of(this).get(DayViewModel.class);

        Intent intent = getIntent();
        day = intent.getIntExtra("day", 0);

        dayViewModel.getList().observe(this, new Observer<ArrayList<DayViewModel.dayList>>() {
            @Override
            public void onChanged(ArrayList<DayViewModel.dayList> dayLists) {
                PieChart pieChart = findViewById(R.id.piechart);

                pieChart.setUsePercentValues(true);
                pieChart.getDescription().setEnabled(false);
                pieChart.setExtraOffsets(5,10,5,5);

                pieChart.setDragDecelerationFrictionCoef(0.95f);

                pieChart.setDrawHoleEnabled(false);
                pieChart.setHoleColor(Color.WHITE);
                pieChart.setHoleRadius(30);
                pieChart.setTransparentCircleRadius(61f);

                ArrayList<PieEntry> yValues = new ArrayList<>();

                int[] colorArray = new int[dayViewModel.getSize()];

                for(int i = 0; i < dayViewModel.getSize(); i++){
                    if(day == Integer.parseInt(dayViewModel.getList(i).getDate())){
                        colorArray[i] = Integer.parseInt(dayViewModel.getList(i).getColor());
                        yValues.add(new PieEntry(Integer.parseInt(dayViewModel.getList(i).getGaphour()), dayViewModel.getList(i).getWork()));
                    }
                }

                PieDataSet dataSet = new PieDataSet(yValues, "work");
                dataSet.setSliceSpace(3f);
                dataSet.setSelectionShift(5f);
                //dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
                dataSet.setColors(colorArray);

                PieData data = new PieData(dataSet);
                data.setValueTextSize(15);

                pieChart.setData(data);
                pieChart.invalidate();
            }
        });
        dayViewModel.requestList();
    }
}
