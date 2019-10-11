package com.example.familyexpences;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.familyexpences.DB.SQLiteDatabaseHelper;
import com.example.familyexpences.DTOs.Expense;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DashboardFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    CalendarView calendarView;
    ListView expensesListView;
    Button buttonDeleteExpense;

    public DashboardFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Dashboard");
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        expensesListView = view.findViewById(R.id.expensesListView);

        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        calendarView = (CalendarView) view.findViewById(R.id.dashboardCalendar);
        buttonDeleteExpense = (Button) view.findViewById(R.id.buttonDeleteExpense);

        final SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(getActivity());

        buttonDeleteExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteExpense();
            }
        });

        Date myDate = this.getYesterday(calendarView.getDate());
        Date lastDate = this.getMidnight(calendarView.getDate());



//        List<Expense> expenses = db.getExpenses();
        List<Expense> expenses = db.getExpenses(myDate.getTime(), lastDate.getTime());
        BigDecimal sum = new BigDecimal("0");

        for (Expense item : expenses
        ) {
            BigDecimal price = item.getPrice();
            sum = sum.add(price);
        }

        ExpenseListAdapter adapter = new ExpenseListAdapter(getActivity(), R.layout.adapter_view_layout, expenses);
        expensesListView.setAdapter(adapter);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = "some";
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(Calendar.YEAR,i);
                selectedDate.set(Calendar.MONDAY,i1);
                selectedDate.set(Calendar.DAY_OF_MONTH,i2);

                Date myDate = getYesterday(selectedDate.getTimeInMillis());
                Date lastDate = getMidnight(selectedDate.getTimeInMillis());

                List<Expense> newExpenses = db.getExpenses(myDate.getTime(), lastDate.getTime());
                ExpenseListAdapter newAdapter = new ExpenseListAdapter(getActivity(), R.layout.adapter_view_layout, newExpenses);
                expensesListView.setAdapter(newAdapter);
            }
        });


        super.onViewCreated(view, savedInstanceState);
    }

    public Date getYesterday(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);

        return calendar.getTime();
    }

    public Date getMidnight(long millis){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        calendar.set(Calendar.MILLISECOND, 999);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.HOUR_OF_DAY, 23);

        return calendar.getTime();
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
