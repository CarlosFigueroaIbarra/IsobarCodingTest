package com.roundarch.codetest.part2;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import com.roundarch.codetest.R;

public class EditActivity extends FragmentActivity {

    DataModel model;
    EditFragment editFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        model = (DataModel)getIntent().getSerializableExtra("model");
        editFragment = (EditFragment)getSupportFragmentManager().findFragmentById(R.id.edit_fragment);
        editFragment.setModel(model);
        setResult(Activity.RESULT_CANCELED);
        // TODO - you will need to obtain the model object provided to this activity and provide it to the EditFragment
    }
}
