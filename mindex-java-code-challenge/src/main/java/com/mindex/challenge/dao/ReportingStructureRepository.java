package com.mindex.challenge.dao;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.data.Employee;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface ReportingStructureRepository extends MongoRepository<Employee, String> {
    ReportingStructure findByEmployeeId(String employeeId);
}