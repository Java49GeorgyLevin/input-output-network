package telran.employees.service;

import java.util.List;
import java.util.stream.Collectors;

import telran.employees.dto.DepartmentSalary;
import telran.employees.dto.Employee;
import telran.employees.dto.SalaryDistribution;

import java.time.LocalDate;
import java.util.*;
public class CompanyImpl implements Company {
  LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
  TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
  HashMap<String, Collection<Employee>> employeesDepartment = new HashMap<>();
  TreeMap<LocalDate, Collection<Employee>> employeesAge = new TreeMap<>();
  
	@Override
	public boolean addEmployee(Employee empl) {
		boolean res = false;
		Employee emplRes = employees.putIfAbsent(empl.id(), empl);
		if(emplRes == null) {
			res = true;
			addEmployeeSalary(empl);
			addEmployeeDepartment(empl);
			addEmployeeAge(empl);
		}
		return res;
	}

	private void addEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		employeesSalary.computeIfAbsent(salary, k -> new HashSet<>())
		.add(empl);		
	}
	
	private void addEmployeeDepartment(Employee empl) {
		String department = empl.department();
		employeesDepartment.computeIfAbsent(department, k -> new HashSet<>())
		.add(empl);
	}	
	
	private void addEmployeeAge(Employee empl) {
		LocalDate birthDate = empl.birthDate();
		employeesAge.computeIfAbsent(birthDate, k -> new HashSet<>())
		.add(empl);		
	}

	@Override
	public Employee removeEmployee(long id) {
		Employee res = employees.remove(id);
		if(res != null) {
			removeEmployeeSalary(res);
			removeEmployeeDepartment(res);
			removeEmployeeAge(res);
		}
		return res;
	}

	private void removeEmployeeAge(Employee empl) {
		LocalDate birthDate = empl.birthDate();
		Collection<Employee> employeesCol = employeesAge.get(birthDate);
		employeesCol.remove(empl);
		if(employees.isEmpty()) {
			employeesAge.remove(birthDate);
		}
		
	}

	private void removeEmployeeDepartment(Employee empl) {
		String department = empl.department();
		Collection<Employee> employeesCol = employeesDepartment.get(department);
		employeesCol.remove(empl);
		if(employeesCol.isEmpty()) {
			employeesDepartment.remove(department);
		}	
		
	}

	private void removeEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		Collection<Employee> employeesCol = employeesSalary.get(salary);
		employeesCol.remove(empl);
		if(employeesCol.isEmpty()) {
			employeesSalary.remove(salary);
		}
		
	}

	@Override
	public Employee getEmployee(long id) {
		
		return employees.get(id);
	}

	@Override
	public List<Employee> getEmployees() {
		
		return new ArrayList<>(employees.values());
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department,
						Collectors.averagingInt(Employee::salary)))
				.entrySet().stream().map(e -> new DepartmentSalary(e.getKey(),
						e.getValue())).toList();
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		
		return employees.values().stream()
				.collect(Collectors.groupingBy(e -> e.salary()/interval,
						Collectors.counting()))
				.entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval,
						e.getKey() * interval + interval - 1, e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary()))
						.toList();
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		
		return employeesDepartment.getOrDefault(department, Collections.emptyList())
				.stream()
				.sorted((e1, e2) -> Long.compare(e1.id(), e2.id()))
				.toList();
		
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		
		return employeesSalary.subMap(salaryFrom, true, salaryTo, true)
				.values().stream().flatMap(col -> col.stream()
						.sorted((empl1, empl2) ->
						Long.compare(empl1.id(), empl2.id())))
				
				.toList();
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		if(ageFrom > ageTo || ageFrom < 0 || ageTo > 100) {
			return Collections.emptyList();
		}
		
		int yearToday = LocalDate.now().getYear();
		int dayToday = LocalDate.now().getDayOfYear();
		LocalDate dateFrom = LocalDate.ofYearDay(yearToday - ageTo - 1, dayToday + 1);
		LocalDate dateTo = LocalDate.ofYearDay(yearToday - ageFrom, dayToday);
		
		
		return employeesAge.subMap(dateFrom, true, dateTo, true).values().stream()
				.flatMap(col -> col.stream()
						.sorted((empl1, empl2) -> 
						Long.compare(empl1.id(), empl2.id()))
						)
				.toList();
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		Employee empl = getEmployee(id);
		if(newSalary != empl.salary()) {
			removeEmployee(id);
			addEmployee(new Employee(id, empl.name(), empl.department(), newSalary, empl.birthDate()));			
		} else {
			empl = null;
		}
		
		return empl;
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		Employee empl = getEmployee(id);
		if(newDepartment != null && !newDepartment.equals(empl.department())) {
			removeEmployee(id);
			addEmployee(new Employee(id, empl.name(), newDepartment, empl.salary(), empl.birthDate()));
		} else {
			empl = null;
		}

		return empl;
	}
	

}
