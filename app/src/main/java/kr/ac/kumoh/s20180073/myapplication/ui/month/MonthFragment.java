package kr.ac.kumoh.s20180073.myapplication.ui.month;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import kr.ac.kumoh.s20180073.myapplication.R;
import kr.ac.kumoh.s20180073.myapplication.ui.day.TimeActivity;

import static android.app.Activity.RESULT_OK;

public class MonthFragment extends Fragment {

    public interface onDataPassListener{
        void onDataPass(int data);
    }

    private onDataPassListener mOnDataPasLestener;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof onDataPassListener){
            mOnDataPasLestener = (onDataPassListener) context;
        }
        else{
            throw new RuntimeException(context.toString() + "must implement onDataListener");
        }
    }

    @Override
    public void onDetach(){
        super.onDetach();
        mOnDataPasLestener = null;
    }

    private MonthViewModel monthViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        monthViewModel =
                ViewModelProviders.of(this).get(MonthViewModel.class);
        View root = inflater.inflate(R.layout.fragment_month, container, false);

        final CalendarView calender = root.findViewById(R.id.calendar);

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                //날짜 선택 시
                //프래그먼트 새로 만들어서 원 그래프 출력하면 될듯
                //날짜 넘겨줘야 될듯
                mOnDataPasLestener.onDataPass(day);
            }
        });

        return root;
    }
}