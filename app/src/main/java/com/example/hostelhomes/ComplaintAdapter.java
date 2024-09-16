package com.example.hostelhomes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ComplaintAdapter extends ArrayAdapter<Model> {

    private Context context;
    private List<Model> complaintList;

    public ComplaintAdapter(Context context, List<Model> complaintList) {
        super(context, 0, complaintList);
        this.context = context;
        this.complaintList = complaintList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the complaint for this position
        Model complaint = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_complaint, parent, false);
        }

        // Lookup view for data population
        TextView textComplaint = convertView.findViewById(R.id.text_complaint);
        TextView textStatus = convertView.findViewById(R.id.text_status);
        TextView textDate = convertView.findViewById(R.id.text_date);

        // Populate the data into the template view
        textComplaint.setText(complaint.getComplaintText());
        textStatus.setText("Status: " + complaint.getStatus());

        // Format timestamp to readable date
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
        String date = sdf.format(new Date(complaint.getTimestamp()));
        textDate.setText(date);

        // Return the completed view to render on screen
        return convertView;
    }
}
