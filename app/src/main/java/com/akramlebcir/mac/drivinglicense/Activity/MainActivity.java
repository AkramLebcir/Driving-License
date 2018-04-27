package com.akramlebcir.mac.drivinglicense.Activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.akramlebcir.mac.drivinglicense.Fragment.InfractionFragment;
import com.akramlebcir.mac.drivinglicense.Fragment.LawFragment;
import com.akramlebcir.mac.drivinglicense.R;
import com.akramlebcir.mac.drivinglicense.Adapter.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    public Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.app_bar_search) {
            Toast.makeText(getApplicationContext(), "Search...", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
