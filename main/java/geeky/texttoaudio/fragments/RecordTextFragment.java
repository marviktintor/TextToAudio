package geeky.texttoaudio.fragments;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.tts.UtteranceProgressListener;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.Locale;

import coreutils.fragments.FragmentWrapper;
import coreutils.media.audio.recorder.MicRecorder;
import coreutils.media.audio.tts.TTSEngine;
import geeky.texttoaudio.R;
import geeky.texttoaudio.constants.AppConstants;
import geeky.texttoaudio.intents.AppIntents;

/**
 * Created by victor on 3/25/2016.
 */
public class RecordTextFragment extends FragmentWrapper {

    private static final int REQUEST_GET_TTS_ENGINE_STATE = 1 << 0;

    private EditText etTextToRead;
    private AppCompatButton btReadText;
    private AppCompatCheckBox cbSaveAudio;

    private TTSEngine textToSpeechEngine;

    private MicRecorder recorder;

    private PendingIntent pendingIntent;

    /**
     * Called when the fragment is created
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreateFragment(@Nullable Bundle savedInstanceState) {
        recorder = new MicRecorder();
        textToSpeechEngine = new TTSEngine(getActivity(), Locale.ENGLISH);
        getTextToSpeechEngine().setOnUtteranceListener(utteranceListener);
        getActivity().registerReceiver(receiver, new IntentFilter(AppIntents.EXTRA_TEXT_TO_SPEECH_STATE));
        pendingIntent = PendingIntent.getBroadcast(getActivity(), REQUEST_GET_TTS_ENGINE_STATE, new Intent(AppIntents.EXTRA_TEXT_TO_SPEECH_STATE), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    /**
     * Used for setting the title of the Activity
     *
     * @return activity title
     */
    @Nullable
    @Override
    public String getActivityTitle() {
        return "Read/Record Text as Audio";
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
        etTextToRead = (EditText) view.findViewById(R.id.fragment_record_audio_edittext_text_to_record);
        btReadText = (AppCompatButton) view.findViewById(R.id.fragment_record_audio_button_read_text);
        cbSaveAudio = (AppCompatCheckBox) view.findViewById(R.id.fragment_record_audio_checkbox_record_audio_while_playing);

        btReadText.setOnClickListener(onClickReadButton);

        return view;
    }

    View.OnClickListener onClickReadButton = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (etTextToRead.getText().length() > 0) {
                if (cbSaveAudio.isChecked()) {
                    recordReadText();
                }
                getTextToSpeechEngine().readText(etTextToRead.getText().toString());
            }
        }

    };

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
        getActivity().unregisterReceiver(receiver);
        getTextToSpeechEngine().onActivityDestroy();
    }

    /**
     * Returns the layout resource id for the layout used to populate the view for this fragment
     *
     * @return the layout resource id
     */
    @Override
    public int getParentLayout() {
        return R.layout.fragment_record_text_audio;
    }

    UtteranceProgressListener utteranceListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onDone(String utteranceId) {

        }

        @Override
        public void onError(String utteranceId) {

        }
    };

    public TTSEngine getTextToSpeechEngine() {
        return textToSpeechEngine;
    }

    public MicRecorder getRecorder() {
        return recorder;
    }

    /**
     * Records the text being read
     */
    private void recordReadText() {
        getUtilities().showSimpleSnackBar(etTextToRead, "Recording Audio");
        recorder.recordAudio(AppConstants.RECORDED_TEXT_STORAGE_DIR + "Audio_" + System.currentTimeMillis() + ".ogg");
        getUtilities().startRepeatingAlarm(SystemClock.elapsedRealtime(), 1000, pendingIntent);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppIntents.EXTRA_TEXT_TO_SPEECH_STATE)) {
                getUtilities().toast("Is Speaking :" + getTextToSpeechEngine().isSpeaking() + " is Recording " + getRecorder().isRecording());
                if (!textToSpeechEngine.isSpeaking()) {
                    getUtilities().stopRepeatingAlarm(pendingIntent);
                    getRecorder().stopRecording();
                }
            }
        }

    };

}
