package com.mindex.challenge.data;

import java.util.List;

public class ReportingStructure {

    private String name;
    private String numberOfReports;
    public ReportingStructure(){

    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setNumberOfReports(int numReports){

        this.numberOfReports = String.valueOf(numReports);
    }
    public String getNumberOfReports() {
        return this.numberOfReports;
    }
}
