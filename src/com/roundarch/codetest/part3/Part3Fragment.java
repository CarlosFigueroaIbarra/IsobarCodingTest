package com.roundarch.codetest.part3;


import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.roundarch.codetest.R;

import java.util.ArrayList;

public class Part3Fragment extends Fragment {
    Part3Service myService;
    IntentFilter filter;
    MyReceiver receiver;
    MyAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part3, null);

        View emptyView = (View) view.findViewById(R.id.empty_textview);
        ListView listView = (ListView) view.findViewById(R.id.part3_listview);
        listView.setEmptyView(emptyView);

        mAdapter = new MyAdapter(getActivity(), new ArrayList<MyLocation>());
        listView.setAdapter(mAdapter);

        // TODO - the listview will need to be provided with a source for data

        // TODO - (optional) you can set up handling to list item selection if you wish

        receiver = new MyReceiver();
        filter  = new IntentFilter();
        filter.addAction(Part3Service.ACTION_SERVICE_DATA_UPDATED);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO - for Part3, this might be a good place to bind to our Service
        Intent intent = new Intent(getActivity(), Part3Service.class);
        getActivity().bindService(intent, myConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unbindService(myConnection);
    }

    private ServiceConnection myConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            Part3Service.Part3ServiceBinder binder = (Part3Service.Part3ServiceBinder) service;
            myService = binder.getService();
            myService.updateData();
        }

        public void onServiceDisconnected(ComponentName arg0) {
            myService = null;
        }
    };

    @Override
    public void onResume() {
        super.onResume();

        getActivity().registerReceiver(receiver, filter);

        if (myService!=null) {
            myService.updateData();
        }

        // TODO - when the fragment resumes, it would be a good time to register to receieve broadcasts
        // TODO - from the service.  The broadcast will serve as a way to inform us that data is available
        // TODO - for consumption

        // TODO - this is also a good place to leverage the Service's IBinder interface to tell it you want
        // TODO - to refresh data

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(receiver);
    }

    // TODO - our listView needs a source of data, and here might be a good place to create that

    // TODO - we also need a means of responding to the Broadcasts sent by our Service

    class MyReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Part3Service.ACTION_SERVICE_DATA_UPDATED)) {
                ArrayList<MyLocation> location = intent.getParcelableArrayListExtra("res");
                if (location != null) {
                    Log.i(getClass().getName(), "LIST READY");
                    if (location==null || location.size() == 0) return;
                    mAdapter.clear();
                    mAdapter.addAll(location);
                }
            }
        }
    }
}
