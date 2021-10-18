package com.mindex.challenge.service.impl;

import org.springframework.stereotype.Service;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public ReportingStructure read(String id) {
        LOG.debug("Creating ReportingStructure with id [{}]", id);

        // easier this way cause we utilize data already in Employee DB
        Employee employee = employeeRepository.findByEmployeeId(id);
        ReportingStructure empStructure = new ReportingStructure();
        empStructure.setName(employee.getFirstName() + " " + employee.getLastName());
        empStructure.setNumberOfReports(employee.getDirectReports().size());

        if (employee == null || empStructure == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }
        return empStructure;
    }
}
