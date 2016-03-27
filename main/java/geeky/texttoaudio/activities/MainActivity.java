package geeky.texttoaudio.activities;

import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;

import coreutils.activities.ActivityWrapper;
import geeky.texttoaudio.R;
import geeky.texttoaudio.fragments.PlayRecordedTextFragment;
import geeky.texttoaudio.fragments.RecordTextFragment;

public class MainActivity extends ActivityWrapper
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;


    /**
     * Performs initialization of all loaders
     * Called when the activity is created
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreateActivity(Bundle savedInstanceState) {
        setContentView(R.layout.activity_wrapper);
        initViews();
        attachFragment(new RecordTextFragment(), true);
    }

    /**
     * Called when the activity is resumed
     */
    @Override
    protected void onResumeActivity() {

    }

    /**
     * Called when the activity is paused
     */
    @Override
    protected void onPauseActivity() {

    }

    /**
     * Called when the activity is destroyed
     */
    @Override
    protected void onDestroyActivity() {

    }

    /**
     * Returns the id of the fragment container view
     */
    @Override
    public int getFragmentsContainerId() {
        return R.id.activity_main_relativeLayout_container;
    }

    /**
     * Called when a new fragment is attached to the the activity
     *
     * @param fragment
     * @param addToBackStack
     */
    @Override
    public void onFragmentAttached(Fragment fragment, boolean addToBackStack) {

    }

    /**
     * Provide methods for opening and closing the navigation drawer
     */
    @Override
    public void onNavigationClickListener() {
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_wrapper_drawerLayout_drawer);

        if (!drawerLayout.isDrawerOpen(Gravity.LEFT)) {
            drawerLayout.openDrawer(Gravity.LEFT);
        }
    }


    /**
     * Called when an item in the navigation menu is selected.
     *
     * @param item The selected item
     * @return true to display the item as the selected item
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        closeDrawer();
        switch (item.getItemId()) {
            case R.id.record_audio:
                attachFragment(new RecordTextFragment(), true);
                break;
            case R.id.view_recorded_audios:
                attachFragment(new PlayRecordedTextFragment(), true);
                break;
        }
        return false;
    }


    private void initViews() {
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}
