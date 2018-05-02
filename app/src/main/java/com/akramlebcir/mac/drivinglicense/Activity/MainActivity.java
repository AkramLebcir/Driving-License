package com.akramlebcir.mac.drivinglicense.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akramlebcir.mac.drivinglicense.Fragment.InfractionFragment;
import com.akramlebcir.mac.drivinglicense.Fragment.LawFragment;
import com.akramlebcir.mac.drivinglicense.R;
import com.akramlebcir.mac.drivinglicense.Adapter.ViewPagerAdapter;
import com.akramlebcir.mac.drivinglicense.model.Citizen;
import com.akramlebcir.mac.drivinglicense.model.DriverLicense;
import com.akramlebcir.mac.drivinglicense.model.Infraction;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        ArrayList<Infraction> list = new ArrayList<>();
//        list.add(new Infraction(1,1,1,"infraction","","/police/hjskdhaskdsa",false,2000));
//        list.add(new Infraction(2,6,4,"infraction","","/police/hjskdhaskdsa",false,5000));
//        list.add(new Infraction(3,2,2,"infraction","","/police/hjskdhaskdsa",false,2500));
//        Citizen citizen = new Citizen("uVgm1uxDhif7cHomWxPLGwEtWXI3",
//                "male",
//                "setif",
//                "/image.png",
//                "algeria",
//                "akram",
//                "lebcir",
//                "16-03-1995",
//                "B+",
//                "alger",
//                23,
//                new DriverLicense("fhakfhaslfhajlkhfjj",
//                        "16-03-2014",
//                        "16-03-2022",
//                        24,
//                        "b",
//                        list));
//
//        Log.v("err firebase","citizen");
//        DatabaseReference myRef = database.getReference("Citizen/uVgm1uxDhif7cHomWxPLGwEtWXI3");
//
//        myRef.setValue(citizen);


        toolbar = findViewById(R.id.toolbar_id);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Main Page");
        }
        toolbar.setSubtitle("Test Subtitle");
        toolbar.inflateMenu(R.menu.main_menu);

        android.support.v4.app.Fragment f1 = new InfractionFragment();
        android.support.v4.app.Fragment f2 = new LawFragment();

        ViewPager viewPager = findViewById(R.id.viewpager_id);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.AddFragment( f1,"Infraction");
        pagerAdapter.AddFragment( f2,"Law");
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tablayout_id);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_view_day_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_account_balance_black_24dp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

        @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
            if (id == R.id.signOut){
            auth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
