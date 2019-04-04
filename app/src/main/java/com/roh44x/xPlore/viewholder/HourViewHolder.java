package com.roh44x.xPlore.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.roh44x.xPlore.R;
import com.roh44x.xPlore.model.Hour;

public class HourViewHolder extends RecyclerView.ViewHolder {

    public TextView startHour, subjectHour, hourInterval, teacherHour, classRoom;

    public HourViewHolder(@NonNull View itemView) {
        super(itemView);

        startHour = itemView.findViewById(R.id.startHour);
        subjectHour = itemView.findViewById(R.id.currentHour);
        hourInterval = itemView.findViewById(R.id.intervalHour);
        teacherHour = itemView.findViewById(R.id.teacherHour);
        classRoom = itemView.findViewById(R.id.classRoom);
    }


    public void bindToHour(Hour hour)
    {
        startHour.setText(hour.startHour);
        subjectHour.setText(hour.subjectHour);
        hourInterval.setText(hour.hourInterval);
        teacherHour.setText(hour.teacherHour);
        classRoom.setText(hour.classRoom);
    }
}
