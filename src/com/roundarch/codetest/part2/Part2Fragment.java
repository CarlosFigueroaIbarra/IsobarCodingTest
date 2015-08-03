package com.roundarch.codetest.part2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;

import com.roundarch.codetest.R;

public class Part2Fragment extends Fragment {
    private static String EXTRA_MODEL = "extra_model";
    private DataModel mModel = new DataModel();

    private EditText textView1;
    private EditText textView3;
    private EditText textView2;

    @Override
    public View
            onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit, null);

        // TODO -
        view.findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick_edit();
            }
        });

        textView1 = (EditText)view.findViewById(R.id.editText1);
        textView2 = (EditText)view.findViewById(R.id.editText2);
        textView3 = (EditText)view.findViewById(R.id.editText3);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(EXTRA_MODEL)) {
                mModel = (DataModel) savedInstanceState.get(EXTRA_MODEL);
            }
        }

        setTextViews();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(EXTRA_MODEL, mModel);

        super.onSaveInstanceState(outState);
    }

    public void onClick_edit() {
        // TODO - package up the data model and provide it to the new EditActivity as it is being created
        Intent intent = new Intent(this.getActivity(), EditActivity.class);

        mModel.setText1(textView1.getText().toString());
        mModel.setText2(textView2.getText().toString());
        mModel.setText3(Double.parseDouble(textView3.getText().toString()));

        intent.putExtra("model", mModel);

        // TODO - this probably isn't the best way to start the EditActivty, try to fix it
        startActivityForResult(intent, 1);
    }

    // TODO - provide a method to obtain the data model when it is returned from the EditActivity

    private void setTextViews() {
        textView1.setText(mModel.getText1());
        textView2.setText(mModel.getText2());
        textView3.setText(String.valueOf(mModel.getText3()));
    }

    public void onActivityResult(int requestCode, int result, Intent data) {
        switch(requestCode) {
            case 1:
                if (result == Activity.RESULT_OK) {
                    mModel = (DataModel)data.getSerializableExtra("res");
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            setTextViews();
                        }
                    });
                }
                break;
        }
    }
}
