package com.mindex.challenge.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Compensation {
    private Employee employee;
    private String salary;
    private String effectiveDate;
    public Compensation() {
    }
    public Employee getEmployee() {
        return this.employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public String getSalary() {
        return this.salary;
    }
    public void setSalary(String salary) {
        this.salary = salary;
    }
    public String getEffectiveDate() {
        return this.effectiveDate;
    }
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public void save(Compensation compensation) {
        try {
            File dbFile = new File("src/main/resources/static/compensation_db.csv");
            FileWriter db = new FileWriter(dbFile, true);
            String prep = compensation.employee.getEmployeeId() + "," + compensation.salary + "," + compensation.effectiveDate + "\n";
            db.write(prep);
            db.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Compensation find(String id) {
        Compensation tmp = new Compensation();
        try {
            //System.out.println("FINDING\n");
            File dbFile = new File("src/main/resources/static/compensation_db.csv");
            Scanner scanIt = new Scanner(dbFile);
            while (scanIt.hasNextLine()) {
                //separate
                String fileLine = scanIt.nextLine();
                String[] tokens = fileLine.split(",");
                String thisId = tokens[0];
                String sal = tokens[1];
                String effectDt = tokens[2];
                // build employee object
                Employee emp = new Employee();
                emp.setEmployeeId(thisId);
                //make comp to return
                if (emp.getEmployeeId().equals(id)) {
                    tmp.setEmployee(emp);
                    tmp.setSalary(sal);
                    tmp.setEffectiveDate(effectDt);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return tmp;
    }
}
