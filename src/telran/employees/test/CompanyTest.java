package telran.employees.test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import telran.employees.dto.*;
import telran.employees.service.Company;
import telran.employees.service.CompanyImpl;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CompanyTest {
	private static final long ID1 = 123;
	private static final String DEP1 = "dep1";
	private static final int SALARY1 = 10000;
	private static final int YEAR1 = 2000;
	private static final LocalDate DATE1 = LocalDate.ofYearDay(YEAR1, 100);
	private static final long ID2 = 124;
	private static final long ID3 = 125;
	private static final long ID4 = 126;
	private static final long ID5 = 127;
	private static final String DEP2 = "dep2";
	private static final String DEP3 = "dep3";
	private static final int SALARY2 = 5000;
	private static final int SALARY3 = 15000;
	private static final int YEAR2 = 1990;
	private static final LocalDate DATE2 = LocalDate.ofYearDay(YEAR2, 100);
	private static final int YEAR3 = 2003;
	private static final LocalDate DATE3 = LocalDate.ofYearDay(YEAR3, 100);
	private static final long ID_NOT_EXIST = 10000000;
	private static final String TEST_DATA = "test.data";
	Employee empl1 = new Employee(ID1, "name", DEP1, SALARY1, DATE1);
	Employee empl2 = new Employee(ID2, "name", DEP2, SALARY2, DATE2);
	Employee empl3 = new Employee(ID3, "name", DEP1, SALARY1, DATE1);
	Employee empl4 = new Employee(ID4, "name", DEP2, SALARY2, DATE2);
	Employee empl5 = new Employee(ID5, "name", DEP3, SALARY3, DATE3);
	Employee[] employees = {empl1, empl2, empl3, empl4, empl5};
	Company company;

	@BeforeEach
	void setUp() throws Exception {
		company = new CompanyImpl();
		for(Employee empl: employees) {
			company.addEmployee(empl);
		}
	}

	@Test
	void testAddEmployee() {
		assertFalse(company.addEmployee(empl1));
		assertTrue(company.addEmployee(new Employee(ID_NOT_EXIST, "name", DEP1, SALARY1, DATE1)));
	}

	@Test
	void testRemoveEmployee() {
		assertNull(company.removeEmployee(ID_NOT_EXIST));
		assertEquals(empl1, company.removeEmployee(ID1));
		Employee[] expected = {empl2, empl3, empl4, empl5};
		assertArrayEquals(expected, company.getEmployees()
				.toArray(Employee[]::new));
		
	}

	@Test
	void testGetEmployee() {
		assertEquals(empl1, company.getEmployee(ID1));
		assertNull(company.getEmployee(ID_NOT_EXIST));
	}

	@Test
	void testGetEmployees() {
		assertArrayEquals(employees, company.getEmployees()
				.toArray(Employee[]::new));
	}

	@Test
	void testGetDepartmentSalaryDistribution() {
		DepartmentSalary [] expected = {
			new DepartmentSalary(DEP2, SALARY2),
			new DepartmentSalary(DEP1, SALARY1),
			new DepartmentSalary(DEP3, SALARY3)
		};
		DepartmentSalary [] actual = company.getDepartmentSalaryDistribution()
				.stream().sorted((ds1, ds2) -> Double.compare(ds1.salary(), ds2.salary())).
				toArray(DepartmentSalary[]::new);
		assertArrayEquals(expected, actual);
	}

	@Test
	void testGetSalaryDistribution() {
		int interval = 5000;
		SalaryDistribution[] expected = {
				new SalaryDistribution(SALARY2, SALARY2 + interval - 1, 2),
				new SalaryDistribution(SALARY1, SALARY1 + interval - 1, 3),
				new SalaryDistribution(SALARY3, SALARY3 + interval - 1, 1),
		};
		company.addEmployee(new Employee(ID_NOT_EXIST, DEP2, DEP2, 13000,  DATE1));
		SalaryDistribution[] actual =
				company.getSalaryDistribution(interval)
				.toArray(SalaryDistribution[]::new);
		assertArrayEquals(expected, actual);
	}
	
	@Test
	void testGetEmployeesByDepartment() {
		Employee[] dep1Expected = {empl1, empl3};
		Employee[] dep2Expected = {empl2, empl4};
		Employee[] dep3Expected = {empl5};
		Employee[] dep1Actual = company.getEmployeesByDepartment(DEP1).toArray(Employee[]::new);
		Employee[] dep2Actual = company.getEmployeesByDepartment(DEP2).toArray(Employee[]::new);
		Employee[] dep3Actual = company.getEmployeesByDepartment(DEP3).toArray(Employee[]::new);
		assertArrayEquals(dep1Expected, dep1Actual);
		assertArrayEquals(dep2Expected, dep2Actual);
		assertArrayEquals(dep3Expected, dep3Actual);				
	}
	
	@Test
	void testGetEmployeesByAge() {
		Employee[] expected1 = {empl2, empl4, empl1, empl3};
		Employee[] expected2 = {empl1, empl3, empl5};
		Employee[] actual1 = company.getEmployeesByAge(22, 40).toArray(Employee[]::new);
		Employee[] actual2 = company.getEmployeesByAge(18, 25).toArray(Employee[]::new);
		assertArrayEquals(expected1, actual1);
		assertArrayEquals(expected2, actual2);
	}
	
	@Test
	void testUpdateSalary() {
		assertEquals(empl1, company.updateSalary(ID1, SALARY2));
		assertEquals(null, company.updateSalary(ID1, SALARY2));		
	}
	
	@Test
	void testUpdateDepartment() {
		assertEquals(empl1, company.updateDepartment(ID1, DEP2));
		assertEquals(null, company.updateDepartment(ID1, DEP2));
	}

	@Test
	@Order(2)
	void testRestore() {
		Company newCompany = new CompanyImpl();
		newCompany.restore(TEST_DATA);
		assertArrayEquals(employees, newCompany.getEmployees()
				.toArray(Employee[]::new));
		
	}

	@Test
	@Order(1)
	void testSave() {
		company.save(TEST_DATA);
	}

}
