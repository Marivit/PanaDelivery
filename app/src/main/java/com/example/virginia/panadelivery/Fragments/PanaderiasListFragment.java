package com.example.virginia.panadelivery.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.virginia.panadelivery.Adapters.PanaderiasListAdapter;
import com.example.virginia.panadelivery.Modelos.Panaderia;
import com.example.virginia.panadelivery.R;
import com.example.virginia.panadelivery.Services.FirestoreService;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PanaderiasListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PanaderiasListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PanaderiasListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView listaPanaderias;
    private List<Panaderia> panaderias;
    private PanaderiasListAdapter panaderiasListAdapter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String TAG = "frag";
    private OnFragmentInteractionListener mListener;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public PanaderiasListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PanaderiasListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PanaderiasListFragment newInstance(String param1, String param2) {
        PanaderiasListFragment fragment = new PanaderiasListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        listaPanaderias =  (RecyclerView) getView().findViewById(R.id.listaPanaderias);

        Log.d(TAG, getActivity().getPackageName());

        panaderias = new ArrayList<>();
        panaderiasListAdapter = new PanaderiasListAdapter(panaderias);
        listaPanaderias.setHasFixedSize(true);
        listaPanaderias.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPanaderias.setAdapter(panaderiasListAdapter);



        db.collection("Panaderias").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    //TODO: Agregar modified

                    if(doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("nombre");
                        Log.d(TAG, name);

                        Panaderia panaderia = doc.getDocument().toObject(Panaderia.class);

                        panaderias.add(panaderia);
                        Log.d(TAG, Integer.toString(panaderiasListAdapter.getItemCount()));
                        panaderiasListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View mView= inflater.inflate(R.layout.fragment_panaderias_list, container, false);

        listaPanaderias =  (RecyclerView) mView.findViewById(R.id.listaPanaderias);


        FragmentManager fm = getActivity().getSupportFragmentManager();

        panaderias = new ArrayList<>();
        panaderiasListAdapter = new PanaderiasListAdapter(panaderias, fm);
        listaPanaderias.setHasFixedSize(true);
        listaPanaderias.setLayoutManager(new LinearLayoutManager(getContext()));
        listaPanaderias.setAdapter(panaderiasListAdapter);



        db.collection("Panaderias").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.d(TAG, e.getMessage());

                }
                for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {
                    //TODO: Agregar modified

                    if(doc.getType() == DocumentChange.Type.ADDED) {
                        String name = doc.getDocument().getString("nombre");
                        Log.d(TAG, name);

                        Panaderia panaderia = doc.getDocument().toObject(Panaderia.class);
                        FirestoreService fs = new FirestoreService();

                       panaderia.setId(doc.getDocument().getId());
                        panaderias.add(panaderia);
                        Log.d(TAG, Integer.toString(panaderiasListAdapter.getItemCount()));
                        panaderiasListAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
        return mView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    /*
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }
*/


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
