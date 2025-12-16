package com.wipro.candidate.bean;

public class CandidateBean {
    
    // Private attributes
    private String id;       // Student Id
    private String name;     // Student name
    private int m1;          // Mark in First Subject
    private int m2;          // Mark in Second Subject
    private int m3;          // Mark in Third Subject
    private String result;   // Result
    private String grade;    // Grade
    
    // Default constructor
    public CandidateBean() {
    }
    
    // Parameterized constructor
    public CandidateBean(String id, String name, int m1, int m2, int m3, String result, String grade) {
        this.id = id;
        this.name = name;
        this.m1 = m1;
        this.m2 = m2;
        this.m3 = m3;
        this.result = result;
        this.grade = grade;
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getM1() {
        return m1;
    }
    
    public void setM1(int m1) {
        this.m1 = m1;
    }
    
    public int getM2() {
        return m2;
    }
    
    public void setM2(int m2) {
        this.m2 = m2;
    }
    
    public int getM3() {
        return m3;
    }
    
    public void setM3(int m3) {
        this.m3 = m3;
    }
    
    public String getResult() {
        return result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    public String getGrade() {
        return grade;
    }
    
    public void setGrade(String grade) {
        this.grade = grade;
    }
    
    @Override
    public String toString() {
        return "CandidateBean [id=" + id + ", name=" + name + ", m1=" + m1 + ", m2=" + m2 + ", m3=" + m3
                + ", result=" + result + ", grade=" + grade + "]";
    }
}