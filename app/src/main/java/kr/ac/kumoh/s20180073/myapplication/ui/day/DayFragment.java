package kr.ac.kumoh.s20180073.myapplication.ui.day;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import kr.ac.kumoh.s20180073.myapplication.DayViewModel;
import kr.ac.kumoh.s20180073.myapplication.R;

import static android.app.Activity.RESULT_OK;
import static java.lang.String.*;

public class DayFragment extends Fragment {

    private DayViewModel dayViewModel;
    private DayAdapter myAdater = new DayAdapter();

    RecyclerView IsResult;

    int color;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dayViewModel = ViewModelProviders.of(this).get(DayViewModel.class);
        View root = inflater.inflate(R.layout.fragment_day, container, false);

        IsResult = root.findViewById(R.id.IsResult);


        dayViewModel.getList().observe(getViewLifecycleOwner(), new Observer<ArrayList<DayViewModel.dayList>>() {
            @Override
            public void onChanged(ArrayList<DayViewModel.dayList> dayLists) {
                myAdater.notifyDataSetChanged();

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                IsResult.setLayoutManager(layoutManager);
                IsResult.setHasFixedSize(true);
                IsResult.setItemAnimator((RecyclerView.ItemAnimator)(new DefaultItemAnimator()));
                DayAdapter adapter = new DayAdapter();
                IsResult.setAdapter(adapter);
            }
        });

        //adapter.notifyDataSetChanged();

        dayViewModel.requestList();

        FloatingActionButton addButton = root.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimeActivity.class);
                startActivityForResult(intent, 101);
            }
        });

        return root;
    }

    public class DayAdapter extends RecyclerView.Adapter<DayAdapter.ViewHolder>{
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Toast.makeText(getContext(), "뷰홀더 생성", Toast.LENGTH_LONG).show();
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.item_day, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Toast.makeText(getContext(), "뷰홀더 연결", Toast.LENGTH_LONG).show();
            Log.d("Read", "Element " + position + " set.");
            holder.work.setText(dayViewModel.getList(position).getWork());
            holder.startTime.setText(dayViewModel.getList(position).getStarthour() + ":" + dayViewModel.getList(position).getStartminute());
            holder.endTime.setText(dayViewModel.getList(position).getEndhour() + ":" + dayViewModel.getList(position).getEndminute());
            holder.gap.setText(dayViewModel.getList(position).getGaphour() + ":" + dayViewModel.getList(position).getGapminute());

            holder.itemView.setBackgroundColor(Integer.parseInt(dayViewModel.getList(position).getColor()));
        }

        @Override
        public int getItemCount() {
            return dayViewModel.getSize();
        }

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView work, startTime, endTime, gap;
            public ViewHolder(View itemView){
                super(itemView);
                work = itemView.findViewById(R.id.work);
                startTime = itemView.findViewById(R.id.startTime);
                endTime = itemView.findViewById(R.id.endTime);
                gap = itemView.findViewById(R.id.gap);

                itemView.setBackgroundColor(color);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 101){
            if(resultCode == RESULT_OK){
                color = data.getIntExtra("color", 0);
                Toast.makeText(getContext(), Integer.toString(color), Toast.LENGTH_LONG).show();
            }
        }
    }
}
