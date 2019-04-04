package com.roh44x.xPlore.model;

public class Hour {
    public String startHour;
    public String subjectHour;
    public String hourInterval;
    public String teacherHour;
    public String classRoom;


    public Hour(){}

    public Hour(String startHour, String subjectHour, String hourInterval, String teacherHour, String classRoom) {
        this.startHour = startHour;
        this.subjectHour = subjectHour;
        this.hourInterval = hourInterval;
        this.teacherHour = teacherHour;
        this.classRoom = classRoom;
    }
}
