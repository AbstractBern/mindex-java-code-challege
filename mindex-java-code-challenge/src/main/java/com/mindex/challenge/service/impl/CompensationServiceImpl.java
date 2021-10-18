package com.mindex.challenge.service.impl;


import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class CompensationServiceImpl implements CompensationService {
    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    //@Autowired
    //private CompensationRepository compensationRepository;

    @Override
    public Compensation create(Compensation compensation) {
        Employee newEmp = new Employee();
        newEmp.setEmployeeId(UUID.randomUUID().toString());
        // salary, name are made through request
        compensation.setEmployee(newEmp);
        compensation.setEffectiveDate(LocalDate.now().toString());
        LOG.debug("Creating compensation with id [{}]", newEmp.getEmployeeId());

        // I cant implement a database for some odd reason.. so i just a text file
        //compensationRepository.save(compensation);
        compensation.save(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Creating compensation with id [{}]", id);

        //Compensation emp = compensationRepository.findByEmployeeId(id);
        // Since i cant quite integrate a new database snapshot, or mongodb, and or even backendless i try text file
        return Compensation.find(id);
    }
}
