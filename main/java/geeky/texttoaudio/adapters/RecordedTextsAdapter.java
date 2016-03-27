package geeky.texttoaudio.adapters;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import coreutils.activities.ActivityWrapper;
import coreutils.media.audio.player.AudioPlayer;
import geeky.texttoaudio.R;
import geeky.texttoaudio.constants.AppConstants;

/**
 * Created by victor on 3/25/2016.
 */
public class RecordedTextsAdapter extends RecyclerView.Adapter<RecordedTextsAdapter.RecordedTextsViewHolder> {

    private AudioPlayer audioPlayer;
    private ActivityWrapper activityWrapper;
    private int layout;
    private ArrayList<String> filenames;

    public RecordedTextsAdapter(ActivityWrapper activityWrapper, int layout, ArrayList<String> filenames) {
        this.activityWrapper = activityWrapper;
        this.layout = layout;
        this.filenames = filenames;
        audioPlayer = new AudioPlayer();
    }

    @Override
    public RecordedTextsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = getActivityWrapper().getLayoutInflater().inflate(getLayout(), parent, false);
        final RecordedTextsViewHolder recordedTextsViewHolder = new RecordedTextsViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onRecordedTextAudioClicked(recordedTextsViewHolder.getAdapterPosition());
            }
        });
        return recordedTextsViewHolder;
    }

    private void onRecordedTextAudioClicked(int adapterPosition) {

        try {
            getAudioPlayer().playAudio(AppConstants.RECORDED_TEXT_STORAGE_DIR + getFilenames().get(adapterPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBindViewHolder(RecordedTextsViewHolder holder, int position) {
        holder.setFileName(getFilenames().get(position));
    }

    @Override
    public int getItemCount() {
        return getFilenames().size();
    }

    public ActivityWrapper getActivityWrapper() {
        return activityWrapper;
    }

    public int getLayout() {
        return layout;
    }

    public ArrayList<String> getFilenames() {
        return filenames;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    class RecordedTextsViewHolder extends RecyclerView.ViewHolder {

        private AppCompatTextView tvFilename;

        public RecordedTextsViewHolder(View itemView) {
            super(itemView);

            tvFilename = (AppCompatTextView) itemView.findViewById(R.id.list_recorded_texts_appcompat_textview_filename);
        }

        public void setFileName(String filename) {
            tvFilename.setText(filename);
        }
    }
}
