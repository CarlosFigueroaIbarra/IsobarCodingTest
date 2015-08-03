package com.roundarch.codetest.part1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.roundarch.codetest.R;

/**
 * Created by mdigiovanni on 8/15/13.
 */
public class Part1Fragment extends Fragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
    // TODO - any member variables you need to store?
    ToggleButton toggler;
    SeekBar bar1;
    SeekBar bar2;
    TextView difference;

    //FIXME: Improve something! Anything
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part1, null);

        // TODO - obtain references to your views from the layout
        toggler = (ToggleButton)view.findViewById(R.id.toggler);
        toggler.setOnClickListener(this);

        bar1 = (SeekBar) view.findViewById(R.id.seekBar1);
        bar1.setOnSeekBarChangeListener(this);
        bar2 = (SeekBar) view.findViewById(R.id.seekBar2);
        bar2.setOnSeekBarChangeListener(this);
        difference = (TextView)view.findViewById(R.id.difference);


        // TODO - hook up any event listeners that make sense for the task

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.toggler:
                if (toggler.isChecked()) {
                    bar1.setProgress(0);
                    bar2.setProgress(0);
                }
                break;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        update(seekBar);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    public void update(SeekBar seekBar) {
        if (toggler.isChecked()) {
            if (seekBar == bar1) {
                bar2.setProgress(bar1.getProgress());
            } else {
                bar1.setProgress(bar2.getProgress());
            }
            difference.setText("0");
        } else {
            difference.setText("" + (bar1.getProgress() - bar2.getProgress()));
        }
    }
}
