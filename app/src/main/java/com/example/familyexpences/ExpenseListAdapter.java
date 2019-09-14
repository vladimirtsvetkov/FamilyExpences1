package com.example.familyexpences;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.familyexpences.DTOs.Expense;

import java.math.BigDecimal;
import java.util.List;

public class ExpenseListAdapter extends ArrayAdapter<Expense> {

    private Context mContext;
    private int mResource;

    public ExpenseListAdapter(@NonNull Context context, int resource, @NonNull List<Expense> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String userName = getItem(position).getUserName();
        String categoryName = getItem(position).getCategory();
        String description = getItem(position).getDescription();
        BigDecimal price = getItem(position).getPrice();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView userNameTextView = convertView.findViewById(R.id.userNameTextView);
        TextView categoryTextView = convertView.findViewById(R.id.categoryTextView);
        TextView priceTextView = convertView.findViewById(R.id.priceTextView);
        TextView DescriptionTextView = convertView.findViewById(R.id.DescriptionTextView);

        userNameTextView.setText(userName);
        categoryTextView.setText(categoryName);
        DescriptionTextView.setText(description);
        priceTextView.setText(price.toString());



//        return super.getView(position, convertView, parent);
        return convertView;
    }
}
