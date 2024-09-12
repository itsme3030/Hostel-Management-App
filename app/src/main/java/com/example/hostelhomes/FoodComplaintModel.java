package com.example.hostelhomes;

public class FoodComplaintModel {

    private String studentId;
    private String staffId;
    private String department;
    private String complaintText;
    private String status;
    private long timestamp;

    // Default constructor for Firebase
    public FoodComplaintModel() {}

    // Constructor with parameters
    public FoodComplaintModel(String studentId, String staffId, String department, String complaintText, String status, long timestamp) {
        this.studentId = studentId;
        this.staffId = staffId;
        this.department = department;
        this.complaintText = complaintText;
        this.status = status;
        this.timestamp = timestamp;
    }

    // Getters and setters

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getComplaintText() {
        return complaintText;
    }

    public void setComplaintText(String complaintText) {
        this.complaintText = complaintText;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
