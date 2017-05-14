package and.com.bakingtime.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import and.com.bakingtime.R;
import and.com.bakingtime.model.Step;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dell on 14-05-2017.
 */

public class StepDetailFragment extends Fragment {

    @BindView(R.id.playerView)
    SimpleExoPlayerView playerView;
    @BindView(R.id.description)
    TextView description;
    @BindView(R.id.thumbnail)
    ImageView thumbnail;
    @BindView(R.id.prev_button)
    Button prevButton;
    @BindView(R.id.next_button)
    Button nextButton;
    Unbinder unbinder;

    private List<Step> stepList;
    private int position;

    public static final String LIST_INDEX = "position";
    public static final String STEP_ID_LIST = "step";

    public StepDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if(savedInstanceState!=null){
            stepList = savedInstanceState.getParcelableArrayList(STEP_ID_LIST);
            position = savedInstanceState.getInt(LIST_INDEX);
        }

        View rootView = inflater.inflate(R.layout.step_detail, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        if(stepList!=null){

            description.setText(stepList.get(position).getDescription());
            if(stepList.get(position).getThumbnailURL() == "")
                Picasso.with(getContext()).load(stepList.get(position).getThumbnailURL()).into(thumbnail);

            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position < stepList.size()-1)
                        position++;
                    else
                        position=0;

                    description.setText(stepList.get(position).getDescription());
                    if(stepList.get(position).getThumbnailURL() == "")
                        Picasso.with(getContext()).load(stepList.get(position).getThumbnailURL()).into(thumbnail);
                }
            });

            prevButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position == 0)
                        position=stepList.size()-1;
                    else
                        position--;

                    description.setText(stepList.get(position).getDescription());
                    if(stepList.get(position).getThumbnailURL() == "")
                        Picasso.with(getContext()).load(stepList.get(position).getThumbnailURL()).into(thumbnail);
                }
            });

        } else{
            Toast.makeText(getContext(),"no Steps present",Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public void setStepList(List<Step> stepList){
        this.stepList = stepList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(STEP_ID_LIST, (ArrayList<? extends Parcelable>) stepList);
        outState.putInt(LIST_INDEX,position);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
