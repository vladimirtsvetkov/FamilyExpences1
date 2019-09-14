package com.example.familyexpences;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.familyexpences.DB.SQLiteDatabaseHelper;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MembersFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MembersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MembersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

      ListView MembersLV;

    public MembersFragment() { }

    // TODO: Rename and change types and number of parameters
    public static MembersFragment newInstance(String param1, String param2) {
        MembersFragment fragment = new MembersFragment();
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
    public void viewData() {
        final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getActivity());
        final List<String> MembersList = db.getUsers();
        System.out.println(MembersList);

        ArrayAdapter<String> MembersAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.support_simple_spinner_dropdown_item,
                MembersList);
        MembersLV.setAdapter(MembersAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Members");
        View view = inflater.inflate(R.layout.fragment_members, container, false);
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        MembersLV  = (ListView) getView().findViewById(R.id.MembersLV);

        viewData();

        /*AddCategoryBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddCategoryET.getText().length() == 0){
                    Toast.makeText(getActivity(),"Enter Category!",Toast.LENGTH_SHORT).show();
                }else{
                    final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getActivity());
                    if (db.addCategory(AddCategoryET.getText().toString())) {
                        Toast.makeText(getActivity(),"Success! " + AddCategoryET.getText().toString()+ " was added to the list!" ,Toast.LENGTH_SHORT).show();
                        AddCategoryET.setText("");
                        viewData();
                    } else {
                        Toast.makeText(getActivity(),"Error!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });*/
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
