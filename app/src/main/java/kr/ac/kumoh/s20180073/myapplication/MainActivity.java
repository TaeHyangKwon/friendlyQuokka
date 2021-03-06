package kr.ac.kumoh.s20180073.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import kr.ac.kumoh.s20180073.myapplication.ui.month.GraphActivity;
import kr.ac.kumoh.s20180073.myapplication.ui.month.MonthFragment;

public class MainActivity extends AppCompatActivity implements MonthFragment.onDataPassListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_month, R.id.navigation_day, R.id.navigation_week)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    public void onDataPass(int data) {
        int day = data;
        //Toast.makeText(this, Integer.toString(day),Toast.LENGTH_LONG).show();

        Intent intent = new Intent(this, GraphActivity.class);
        intent.putExtra("day", day);
        startActivity(intent);
    }
}