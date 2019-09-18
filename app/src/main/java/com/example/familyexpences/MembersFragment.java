package com.example.familyexpences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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

import com.example.familyexpences.Constants.Constants;
import com.example.familyexpences.DB.SQLiteDatabaseHelper;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;


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
    public int familyAdminID;
    public int FamilyId;

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


        final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getActivity());

        SharedPreferences sp = getActivity().getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
        String loggedUser = sp.getString(Constants.LOGGED_USER, "");
        familyAdminID = db.getFamilyAdminId(db.getUserId(loggedUser));
        FamilyId = db.getFamilyIDByAdmin(familyAdminID);

        if(familyAdminID != 0 ){
            Toast.makeText(getActivity(),"You are admin!",Toast.LENGTH_SHORT).show();

            final String userName = db.checkRequests(FamilyId);
            if( userName != ""){
                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle("New request");
                alert.setMessage("User " + userName + " would like to join the family.");
                alert.setButton(Dialog.BUTTON_POSITIVE,"Accept",new DialogInterface.OnClickListener(){

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int UserId = db.getUserId(userName);
                        if (db.acceptRequest(FamilyId,UserId)){
                            Toast.makeText(getActivity(),"Accepted!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                alert.setButton(Dialog.BUTTON_NEGATIVE,"Delete",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        int UserId = db.getUserId(userName);

                        if(db.deleteRequest(UserId)){
                            Toast.makeText(getActivity(),"Deleted!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(),"Something went wrong!",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                alert.show();
            } else {
                AlertDialog alert = new AlertDialog.Builder(getActivity()).create();
                alert.setTitle("No new request");
                alert.setMessage("Check this page for new requests");
                alert.setButton(Dialog.BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //nothing - no new requests
                    }
                });


                alert.show();
            }


        }

    }
    public void viewData() {

        final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getActivity());

        SharedPreferences sp = getActivity().getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
        String loggedUser = sp.getString(Constants.LOGGED_USER, "");
        final int familyAdminID = db.getFamilyAdminId(db.getUserId(loggedUser));

        final List<String> MembersList = db.getFamilyMembers(familyAdminID);
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
