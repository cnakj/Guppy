package com.example.guppy.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.guppy.R;
import com.example.guppy.ui.AlertFragment;
import com.example.guppy.ui.BestWaterFragment;
import com.example.guppy.ui.MyFriendFragment;
import com.example.guppy.ui.MyWaterFragment;
import com.example.guppy.ui.SolutionFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    private MyWaterFragment myWaterFragment = new MyWaterFragment();
    private MyFriendFragment myFriendFragment = new MyFriendFragment();
    private BestWaterFragment bestWaterFragment = new BestWaterFragment();
    private SolutionFragment solutionFragment = new SolutionFragment();
    private AlertFragment alertFragment = new AlertFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, myWaterFragment).commitAllowingStateLoss();
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new ItemSelectedListener());
    }

    class ItemSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            switch(menuItem.getItemId())
            {
                case R.id.my_water_button:
                    transaction.replace(R.id.nav_host_fragment, myWaterFragment).commitAllowingStateLoss();
                    break;
                case R.id.my_friend_button:
                    transaction.replace(R.id.nav_host_fragment, myFriendFragment).commitAllowingStateLoss();
                    break;
                case R.id.best_water_button:
                    transaction.replace(R.id.nav_host_fragment, bestWaterFragment).commitAllowingStateLoss();
                    break;
                case R.id.solution_button:
                    transaction.replace(R.id.nav_host_fragment, solutionFragment).commitAllowingStateLoss();
                    break;
            }
            return true;
        }
    }

    public void onAlertClicked(View view) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, alertFragment).commitAllowingStateLoss();
    }
}
