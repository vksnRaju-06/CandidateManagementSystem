package com.wipro.candidate.service;

import com.wipro.candidate.bean.CandidateBean;
import com.wipro.candidate.dao.CandidateDAO;
import com.wipro.candidate.util.WrongDataException;
import java.util.ArrayList;

public class CandidateMain {
    
   
    public String addCandidate(CandidateBean candBean) {
        try {
            // Validation checks - throw WrongDataException if validation fails
            if (candBean == null) {
                throw new WrongDataException();
            }
            
            if (candBean.getName() == null || candBean.getName().isEmpty()) {
                throw new WrongDataException();
            }
            
            if (candBean.getName().length() < 2) {
                throw new WrongDataException();
            }
            
            // Check if marks are within valid range (0 to 100)
            if (candBean.getM1() < 0 || candBean.getM1() > 100 ||
                candBean.getM2() < 0 || candBean.getM2() > 100 ||
                candBean.getM3() < 0 || candBean.getM3() > 100) {
                throw new WrongDataException();
            }
            
            // Generate Candidate ID
            CandidateDAO dao = new CandidateDAO();
            String candidateId = dao.generateCandidateId(candBean.getName());
            
            if (candidateId == null) {
                return "Error";
            }
            
            // Set the generated ID to the bean
            candBean.setId(candidateId);
            
            // Compute result and grade based on total marks
            int totalMarks = candBean.getM1() + candBean.getM2() + candBean.getM3();
            String result;
            String grade;
            
            if (totalMarks >= 240) {
                result = "PASS";
                grade = "Distinction";
            } else if (totalMarks >= 180 && totalMarks < 240) {
                result = "PASS";
                grade = "First Class";
            } else if (totalMarks >= 150 && totalMarks < 180) {
                result = "PASS";
                grade = "Second Class";
            } else if (totalMarks >= 105 && totalMarks < 150) {
                result = "PASS";
                grade = "Third Class";
            } else {
                result = "FAIL";
                grade = "No Grade";
            }
            
            // Set result and grade to the bean
            candBean.setResult(result);
            candBean.setGrade(grade);
            
            // Add candidate to database
            String dbResult = dao.addCandidate(candBean);
            
            if (dbResult.equals("SUCCESS")) {
                return candidateId + ":" + result;
            } else {
                return "Error";
            }
            
        } catch (WrongDataException e) {
            return "Data incorrect";
        }
    }
    
    
    public ArrayList<CandidateBean> displayAll(String criteria) {
        try {
            // Validate criteria
            if (!criteria.equalsIgnoreCase("PASS") && 
                !criteria.equalsIgnoreCase("FAIL") && 
                !criteria.equalsIgnoreCase("ALL")) {
                throw new WrongDataException();
            }
            
            // Get candidates based on criteria
            CandidateDAO dao = new CandidateDAO();
            return dao.getByResult(criteria);
            
        } catch (WrongDataException e) {
            return null;
        }
    }
    
       public static void main(String[] args) {
        CandidateMain candidateMain = new CandidateMain();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        boolean running = true;
        
        while (running) {
            System.out.println("\n========================================");
            System.out.println("  CANDIDATE MANAGEMENT SYSTEM");
            System.out.println("========================================");
            System.out.println("1. Add New Candidate");
            System.out.println("2. View All Candidates");
            System.out.println("3. View Passed Candidates");
            System.out.println("4. View Failed Candidates");
            System.out.println("5. Exit");
            System.out.println("========================================");
            System.out.print("Enter your choice: ");
            
            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Please enter a number.");
                continue;
            }
            
            switch (choice) {
                case 1:
                    // Add new candidate
                    System.out.println("\n--- Add New Candidate ---");
                    
                    System.out.print("Enter candidate name: ");
                    String name = scanner.nextLine().trim();
                    
                    System.out.print("Enter marks for Subject 1 (0-100): ");
                    int m1 = 0;
                    try {
                        m1 = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks! Operation cancelled.");
                        break;
                    }
                    
                    System.out.print("Enter marks for Subject 2 (0-100): ");
                    int m2 = 0;
                    try {
                        m2 = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks! Operation cancelled.");
                        break;
                    }
                    
                    System.out.print("Enter marks for Subject 3 (0-100): ");
                    int m3 = 0;
                    try {
                        m3 = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid marks! Operation cancelled.");
                        break;
                    }
                    
                    // Create candidate bean
                    CandidateBean candidate = new CandidateBean();
                    candidate.setName(name);
                    candidate.setM1(m1);
                    candidate.setM2(m2);
                    candidate
.setM3(m3);
                    
                    // Add candidate
                    String result = candidateMain.addCandidate(candidate);
                    
                    if (result.equals("Data incorrect")) {
                        System.out.println("\nError: " + result);
                        System.out.println("Please check:");
                        System.out.println("  - Name must be at least 2 characters");
                        System.out.println("  - Marks must be between 0 and 100");
                    } else if (result.equals("Error")) {
                        System.out.println("\nDatabase error occurred. Please check your connection.");
                    } else {
                        String[] parts = result.split(":");
                        System.out.println("\nCandidate added successfully!");
                        System.out.println("  Candidate ID: " + parts[0]);
                        System.out.println("  Result: " + parts[1]);
                        System.out.println("  Total Marks: " + (m1 + m2 + m3));
                    }
                    break;
                    
                case 2:
                    // View all candidates
                    displayCandidates(candidateMain, "ALL");
                    break;
                    
                case 3:
                    // View passed candidates
                    displayCandidates(candidateMain, "PASS");
                    break;
                    
                case 4:
                    // View failed candidates
                    displayCandidates(candidateMain, "FAIL");
                    break;
                    
                case 5:
                    running = false;
                    System.out.println("\nThank you for using Candidate Management System!");
                    System.out.println("Visit Again");
                    break;
                    
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
        
        scanner.close();
    }
    
    
    private static void displayCandidates(CandidateMain candidateMain, String criteria) {
        System.out.println("\n--- " + criteria + " Candidates ---");
        
        ArrayList<CandidateBean> candidates = candidateMain.displayAll(criteria);
        
        if (candidates == null || candidates.isEmpty()) {
            System.out.println("No candidates found.");
        } else {
            System.out.println("Total: " + candidates.size() + " candidate(s)\n");
            System.out.println(String.format("%-8s %-15s %-5s %-5s %-5s %-10s %-15s %-15s", 
                "ID", "Name", "M1", "M2", "M3", "Total", "Result", "Grade"));
            System.out.println("--------------------------------------------------------------------------------");
            
            for (CandidateBean candidate : candidates) {
                int total = candidate.getM1() + candidate.getM2() + candidate.getM3();
                System.out.println(String.format("%-8s %-15s %-5d %-5d %-5d %-10d %-15s %-15s",
                    candidate.getId(),
                    candidate.getName(),
                    candidate.getM1(),
                    candidate.getM2(),
                    candidate.getM3(),
                    total,
                    candidate.getResult(),
                    candidate.getGrade()
                ));
            }
        }
    }
}