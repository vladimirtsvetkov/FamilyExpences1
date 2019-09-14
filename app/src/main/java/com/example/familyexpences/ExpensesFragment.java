package com.example.familyexpences;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.familyexpences.Constants.Constants;
import com.example.familyexpences.DB.SQLiteDatabaseHelper;
import com.example.familyexpences.DTOs.Category;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpensesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExpensesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExpensesFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String selectedCategory;
    EditText ExpenseNameET;
    EditText ExpenseDetailsET;
    EditText ExpenseAmountET;
    Spinner ExpenseCategorySpinner;
    DatePicker datePicker1;
    Button AddExpenseBT;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ExpensesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExpensesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExpensesFragment newInstance(String param1, String param2) {
        ExpensesFragment fragment = new ExpensesFragment();
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
        // Inflate the layout for this fragment
        getActivity().setTitle("Add Expenses");
        return inflater.inflate(R.layout.fragment_expenses, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ExpenseNameET = (EditText) getView().findViewById(R.id.AddCategoryET);
        ExpenseDetailsET = (EditText) getView().findViewById(R.id.ExpenseDetailsET);
        ExpenseAmountET = (EditText) getView().findViewById(R.id.ExpenseAmountET);
        ExpenseCategorySpinner = (Spinner)getView().findViewById(R.id.categories_spinner);
        datePicker1 = (DatePicker) getView().findViewById(R.id.datePicker1);
        AddExpenseBT = (Button) getView().findViewById(R.id.AddExpenseBT);

        final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getActivity());
        final List<Category> categories = db.getCategories();
        final List<String> categoryNames = new ArrayList<String>();

        for (Category category : categories) {
            categoryNames.add(category.getName());
        }
        ArrayAdapter<String> CategoriesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,categoryNames);
        CategoriesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        ExpenseCategorySpinner.setPrompt("Select Category");
        ExpenseCategorySpinner.setAdapter(CategoriesAdapter);

        ExpenseCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryNames.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddExpenseBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences(Constants.LOGIN, MODE_PRIVATE);
                String loggedUser = sp.getString(Constants.LOGGED_USER, "");
                int userId = db.getUserId(loggedUser);


//                int userId = 1;//TODO

                BigDecimal price = new BigDecimal(ExpenseAmountET.getText().toString());
                String description = ExpenseDetailsET.getText().toString();
                int categoryId = 0;
                for (Category category : categories) {
                    if(category.getName().equals(selectedCategory)){
                        categoryId = category.getId();
                    }
                }


//                Date myDate = new Date(2019,5,29);
//                long currentDate = myDate.getTime();
                long currentDate = System.currentTimeMillis();
                try {
                    db.addExpense(userId, categoryId, price, currentDate, description);
                }catch (Exception e){
                    Log.wtf("MYERROR", e.getMessage());
                }

                ExpenseAmountET.setText("");
                ExpenseDetailsET.setText("");
            }
        });
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
