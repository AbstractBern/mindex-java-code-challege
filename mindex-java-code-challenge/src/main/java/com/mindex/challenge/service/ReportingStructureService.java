package com.mindex.challenge.service;
////spring.data.mongodb.uri=mongodb+srv://<username>:<pwd>@<cluster>.mongodb.net/compensate
//spring.data.mongodb.database=compensate
import com.mindex.challenge.data.ReportingStructure;
public interface ReportingStructureService {
    ReportingStructure read(String id);
}
