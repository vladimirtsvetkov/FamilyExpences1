package com.example.familyexpences;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link StatisticsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {
    PieChart pieChart;
    private OnFragmentInteractionListener mListener;

    public StatisticsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        getActivity().setTitle("Statistics");
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        pieChart = view.findViewById(R.id.pieChart);


        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5,10,5, 5);
        pieChart.setDragDecelerationFrictionCoef(0.99f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yVaues = new ArrayList<>();
        yVaues.add(new PieEntry(3f , "Food"));
        yVaues.add(new PieEntry(10f, "cigarettes"));
        yVaues.add(new PieEntry(10f, "Car"));
        yVaues.add(new PieEntry(10f, "Rakia"));
        yVaues.add(new PieEntry(10f, "Vodka"));
        yVaues.add(new PieEntry(10f, "Pasta"));
        yVaues.add(new PieEntry(10f, "Salad"));
        yVaues.add(new PieEntry(10f, "Tomato"));
        yVaues.add(new PieEntry(10f, "Kartofi"));
        yVaues.add(new PieEntry(10f, "Wiski"));

        pieChart.animateY(500);

        PieDataSet dataSet = new PieDataSet(yVaues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.JOYFUL_COLORS);
        PieData data = new PieData((dataSet));
        data.setValueTextSize(15f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
        return view;
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
