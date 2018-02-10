package com.andi.absensi;


import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;




import android.widget.RelativeLayout;

/**
 * Created by Kuncoro on 22/03/2016.
 */
public class Welcome extends android.app.Fragment {

    public Welcome(){}
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = (View) inflater.inflate(R.layout.fragment_welcome, container, false);

        getActivity().setTitle("Absensi Siswa");

        return view;
    }
}
