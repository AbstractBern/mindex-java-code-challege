package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReportingStructureServiceImplTest {
    private String readReportingStructureUrl;

    // I used the employee service for this since data does not need to persist
    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    void setUp() {
        readReportingStructureUrl = "http://localhost:" + port + "/{id}";
    }

    @AfterEach
    void tearDown() {
        readReportingStructureUrl = null;
    }
    /*  Honestly, I dont know how to test with this web app environment but the testing would be something in this manner.         */
    @Test
    void testReadReportingStructure() {
        List<Employee> list = Collections.<Employee> emptyList();
        Employee newEmployee = new Employee();
        newEmployee.setEmployeeId(UUID.randomUUID().toString());
        newEmployee.setFirstName("Bernardo Flores");
        newEmployee.setLastName("Flores");
        newEmployee.setDepartment("Technology");
        newEmployee.setPosition("Engineer");
        newEmployee.setDirectReports(list);   //no direct reports so 0
        // make a report structure
        ReportingStructure repStructure = new ReportingStructure();
        repStructure.setNumberOfReports(0);
        repStructure.setName("Bernardo Flores");
        employeeService.create(newEmployee);

        //check
        ReportingStructure testStructure = restTemplate.getForEntity(readReportingStructureUrl, ReportingStructure.class,newEmployee.getEmployeeId()).getBody();
        assertNotNull(testStructure);
        assertNotNull(testStructure.getName());
        assertEquals(0, testStructure.getNumberOfReports());
        assertReportingEquivalence(repStructure, testStructure);

    }

    private static void assertReportingEquivalence(ReportingStructure expected, ReportingStructure actual) {
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
    }
}