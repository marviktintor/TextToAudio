package geeky.texttoaudio.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.ArrayList;

import coreutils.fragments.FragmentWrapper;
import geeky.texttoaudio.R;
import geeky.texttoaudio.adapters.RecordedTextsAdapter;
import geeky.texttoaudio.constants.AppConstants;

/**
 * Created by victor on 3/25/2016.
 */
public class PlayRecordedTextFragment extends FragmentWrapper {

    private RecyclerView rvRecordedAudio;

    /**
     * Called when the fragment is created
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {

    }

    /**
     * Used for setting the title of the Activity
     *
     * @return activity title
     */
    @Nullable
    @Override
    public String getActivityTitle() {
        return "Play Recorded Audio";
    }

    /**
     * Callback used to receive any bundle passed to the fragment when this fragment was created
     */
    @Override
    public void receiveBundle() {

    }

    /**
     * Used for creating a custom view for the fragment
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Nullable
    @Override
    public View onCreateFragmentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getParentLayout(), container, false);
    }

    /**
     * Initializes the child views for the fragment being created.
     *
     * @param view
     */
    @Override
    public View initFragmentViews(View view) {
        rvRecordedAudio = (RecyclerView) view.findViewById(R.id.fragment_recorded_audios_recycler_view_audios);
        rvRecordedAudio.setLayoutManager(new LinearLayoutManager(getActivityWrapper()));
        ArrayList<String> recordedTexts = getRecordedTextAudios();
        rvRecordedAudio.setAdapter(new RecordedTextsAdapter(getActivityWrapper(), R.layout.list_text_recorded_audio, recordedTexts));
        return view;
    }

    private ArrayList<String> getRecordedTextAudios() {
        File file = new File(AppConstants.RECORDED_TEXT_STORAGE_DIR);
        ArrayList<String> recordedTextAudios = new ArrayList<>(file.list().length);
        for (String filename : file.list()) {
            recordedTextAudios.add(filename);
        }
        return recordedTextAudios;
    }

    /**
     * Callback used to hold methods that performHTTPRequest all the contents of the bundle passed to the fragment
     */
    @Override
    public void consumeBundle() {

    }

    /**
     * Callback called to attach data to all the views in the fragment
     */
    @Override
    public void attachViewsData() {

    }

    /**
     * Called when a fragment is first attached to its context.
     * {#onCreate(Bundle)} will be called after this.
     */
    @Override
    public void onAttachFragment() {

    }

    /**
     * Called when the fragment is visible to the user and actively running.
     * This is generally
     * tied to {Activity#onResume() Activity.onResume} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onResumeFragment() {

    }

    /**
     * Called to provide implementation to initiate the syncing of the contents of the current fragment
     */
    @Override
    public void performPartialSync() {

    }

    /**
     * Called when only the contents of the current fragment are synced
     */
    @Override
    public void onPerformPartialSync() {

    }

    /**
     * Called when the Fragment is no longer resumed.  This is generally
     * tied to  Activity#onPause() Activity.onPause} of the containing
     * Activity's lifecycle.
     */
    @Override
    public void onPauseFragment() {

    }

    /**
     * Called when the fragment is no longer in use.  This is called
     * after onStop()} and before onDetach()}.
     */
    @Override
    public void onDestroyFragment() {

    }

    /**
     * Returns the layout resource id for the layout used to populate the view for this fragment
     *
     * @return the layout resource id
     */
    @Override
    public int getParentLayout() {
        return R.layout.fragment_recorded_audios;
    }
}
