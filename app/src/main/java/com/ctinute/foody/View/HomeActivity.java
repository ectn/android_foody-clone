package com.ctinute.foody.View;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.ctinute.foody.View.Adapters.ViewPagerAdapter;
import com.ctinute.foody.View.Fragment.HomeWhatFragment;
import com.ctinute.foody.View.Fragment.HomeWhereFragment;
import com.ctinute.foody.R;

public class HomeActivity extends AppCompatActivity {

    RadioButton buttonWhere;
    RadioButton buttonWhat;
    RelativeLayout layout;

    ViewPager viewPagerMain;
    ViewPagerAdapter viewPagerMainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layout = (RelativeLayout) findViewById(R.id.rl);

        Toolbar appbar = (Toolbar) findViewById(R.id.appbar);
        setSupportActionBar(appbar);

        buttonWhere = (RadioButton) findViewById(R.id.button_where);
        buttonWhat = (RadioButton) findViewById(R.id.button_what);

        // khoi tao pager home
        viewPagerMain = (ViewPager) findViewById(R.id.viewPager_home);
        viewPagerMainAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerMainAdapter.addFragment(new HomeWhereFragment(),"where");
        viewPagerMainAdapter.addFragment(new HomeWhatFragment(),"what");
        viewPagerMain.setAdapter(viewPagerMainAdapter);

        viewPagerMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageSelected(int position)
            {
                switch (position){
                    case 0:
                        buttonWhere.setChecked(true);
                        break;
                    case 1:
                        buttonWhat.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {}

            @Override
            public void onPageScrolled(int position, float arg1, int arg2) {}
        });

        buttonWhere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewPagerMain.setCurrentItem(0,true);
            }
        });
        buttonWhat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewPagerMain.setCurrentItem(1,true);
            }
        });

        // cac gia tri mac dinh
        buttonWhere.setChecked(true);
        viewPagerMain.setCurrentItem(0);

        // menu
        ImageView bottomMenu = (ImageView) findViewById(R.id.bottom_menu);
        bottomMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                Log.w("log","menu");
                PopupMenu attachFilePopup = new PopupMenu(getBaseContext(), layout.getRootView(), Gravity.BOTTOM );
                attachFilePopup.inflate(R.menu.menu_bottom);
                attachFilePopup.show();
                */
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (viewPagerMain.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            viewPagerMain.setCurrentItem(viewPagerMain.getCurrentItem() - 1);
        }
    }
}
