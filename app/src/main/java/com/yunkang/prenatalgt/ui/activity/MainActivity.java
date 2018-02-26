package com.yunkang.prenatalgt.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hlib.widget.BottomNavigationViewEx;
import com.yunkang.prenatalgt.R;
import com.yunkang.prenatalgt.ui.base.BaseActivity;
import com.yunkang.prenatalgt.ui.fragment.FragmentFour;
import com.yunkang.prenatalgt.ui.fragment.FragmentOne;
import com.yunkang.prenatalgt.ui.fragment.FragmentThree;
import com.yunkang.prenatalgt.ui.fragment.FragmentTwo;

import butterknife.BindView;
import butterknife.ButterKnife;
import q.rorbin.badgeview.Badge;
import q.rorbin.badgeview.QBadgeView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fr_content)
    FrameLayout frContent;

    private BottomNavigationViewEx navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_home:
                    if (lastShowFragment != 0) {
                        switchFragment(lastShowFragment, 0);
                        lastShowFragment = 0;
                    }
                    return true;
                case R.id.navigation_dashboard:
                    if (lastShowFragment != 1) {
                        switchFragment(lastShowFragment, 1);
                        lastShowFragment = 1;
                    }
                    return true;
                case R.id.navigation_notifications:
                    if (lastShowFragment != 2) {
                        switchFragment(lastShowFragment, 2);
                        lastShowFragment = 2;
                    }
                    return true;
                case R.id.navigation_me:
                    if (lastShowFragment != 3) {
                        switchFragment(lastShowFragment, 3);
                        lastShowFragment = 3;
                    }
                    return true;
            }
            return false;
        }
    };
    private Fragment[] fragments;
    private int lastShowFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initFragments();
    }



    public static void startActivity(Context context){
        context.startActivity(new Intent(context,MainActivity.class));
    }

    /**
     * 切换Fragment
     *
     * @param lastIndex 上个显示Fragment的索引
     * @param index     需要显示的Fragment的索引
     */
    public void switchFragment(int lastIndex, int index) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        transaction.hide(fragments[lastIndex]);

        if (!fragments[index].isAdded()) {
            transaction.add(R.id.fr_content, fragments[index]);
        }

        transaction.show(fragments[index]).commitAllowingStateLoss();
    }

    private void initFragments() {

        FragmentOne one = new FragmentOne();
        FragmentTwo two = new FragmentTwo();
        FragmentThree three = new FragmentThree();
        FragmentFour four = new FragmentFour();

        fragments = new Fragment[]{one, two, three, four};
        lastShowFragment = 0;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fr_content, one)
                .show(one)
                .commit();
    }


    /**
     * 初始化控件
     */
    private void initView() {

        ColorStateList csl = createColorStateList();
        navigation = findViewById(R.id.navigation);
        navigation.enableItemShiftingMode(true);
        navigation.enableShiftingMode(false);
        navigation.setItemTextColor(csl);
        navigation.setItemIconTintList(csl);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        addBadgeAt(2, 1);
        addBadgeAt(1, 5);
    }

    @NonNull
    private ColorStateList createColorStateList() {
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked},
                new int[]{android.R.attr.state_checked}
        };

        int[] colors = new int[]{getResources().getColor(R.color.c_c6c6c6),
                getResources().getColor(R.color.colorPrimary)
        };
        return new ColorStateList(states, colors);
    }


    private Badge addBadgeAt(int position, int number) {
        // add badge
        return new QBadgeView(this)
                .setBadgeNumber(number)
                .setGravityOffset(12, 2, true)
                .bindTarget(navigation.getBottomNavigationItemView(position))
                .setOnDragStateChangedListener(new Badge.OnDragStateChangedListener() {
                    //拖动的时候的回调
                    @Override
                    public void onDragStateChanged(int dragState, Badge badge, View targetView) {
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState)
                            Toast.makeText(MainActivity.this,
                                    "hahahha  dragState:" + dragState,
                                    Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
