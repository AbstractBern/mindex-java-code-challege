package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mapping.callback.EntityCallbacks;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import java.time.LocalDate;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompensationServiceImplTest {
    private String createCompensationUrl;
    private String readCompensationUrl;

    // I could not quite implement the database for this service
    @Autowired
    private CompensationService compensationService;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    void setUp() {
        createCompensationUrl = "http://localhost:" + port + "/compensation";
        readCompensationUrl = "http://localhost:" + port + "/compensation/{id}";
    }

    @AfterEach
    void tearDown() {
        createCompensationUrl = null;
        readCompensationUrl = null;
    }
    /* SInce i could not correctly integrate a DB for this service, the idea
        is that when read and create are called in the service, a function in COmpensation
        is called to save or read to a csv file in this project to persist memory in an almost efficient manner

     */
    @Test
    void testCreateReadCompensation() {
        Compensation newComp = new Compensation();
        Employee newEmp = new Employee();
        newEmp.setEmployeeId(UUID.randomUUID().toString());
        newComp.setEmployee(newEmp);
        newComp.setSalary("21.00");
        newComp.setEffectiveDate(LocalDate.now().toString());
        employeeService.create(newEmp);
        compensationService.create(newComp);
        //check
        Compensation testComp = restTemplate.postForEntity(createCompensationUrl,newComp,Compensation.class).getBody();
        assertNotNull(testComp);
        assertNotNull(testComp.getEmployee().getEmployeeId());
        assertCompensationEquivalence(newComp, testComp);

        // get info
        Compensation readCompensation = restTemplate.getForEntity(readCompensationUrl, Compensation.class, testComp.getEmployee().getEmployeeId()).getBody();
        assertEquals(testComp.getEmployee().getEmployeeId(), readCompensation.getEmployee().getEmployeeId());
        assertCompensationEquivalence(newComp, readCompensation);
    }

    private static void assertCompensationEquivalence(Compensation expected, Compensation returnComp) {
        assertEquals(expected.getEmployee(), returnComp.getEmployee());
        assertEquals(expected.getSalary(), returnComp.getSalary());
        assertEquals(expected.getEffectiveDate(), returnComp.getEffectiveDate());
    }
}