package com.penguins.argentovivo_social;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BlankFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BlankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BlankFragment2 extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public BlankFragment2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BlankFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BlankFragment newInstance(String param1, String param2) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //Log.d("lmfaooo", fm.findFragmentByTag("1").toString());

        View rootView = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        //mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        ArrayList<FeedImage> myDataset = new ArrayList<>();
        myDataset.add(new FeedImage("Mario Rossi", "46.272166, 11.283434", BitmapFactory.decodeFile("/sdcard/bz_2.jpg"),BitmapFactory.decodeFile("/sdcard/bz_1.jpg"),28));
        myDataset.add(new FeedImage("Giuseppe Verdi", "Bolzano", BitmapFactory.decodeFile("/sdcard/bz_3.jpg"), BitmapFactory.decodeFile("/sdcard/img_app.jpg"),128));
        myDataset.add(new FeedImage("Giulio", "Trento", BitmapFactory.decodeFile("/sdcard/img_app.jpg"),BitmapFactory.decodeFile("/sdcard/img_app_2.png"),345));
        myDataset.add(new FeedImage("Giulio", "Trento", BitmapFactory.decodeFile("/sdcard/img_app.jpg"),BitmapFactory.decodeFile("/sdcard/img_app_2.png"),345));
        myDataset.add(new FeedImage("Giulio", "Trento", BitmapFactory.decodeFile("/sdcard/img_app.jpg"),BitmapFactory.decodeFile("/sdcard/img_app_2.png"),345));
        myDataset.add(new FeedImage("Giulio", "Trento", BitmapFactory.decodeFile("/sdcard/img_app.jpg"),BitmapFactory.decodeFile("/sdcard/img_app_2.png"),345));
        myDataset.add(new FeedImage("Giulio", "Trento", BitmapFactory.decodeFile("/sdcard/img_app.jpg"),BitmapFactory.decodeFile("/sdcard/img_app_2.png"),345));
        myDataset.add(new FeedImage("Giulio", "Trento", BitmapFactory.decodeFile("/sdcard/img_app.jpg"),BitmapFactory.decodeFile("/sdcard/img_app_2.png"),345));

        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
