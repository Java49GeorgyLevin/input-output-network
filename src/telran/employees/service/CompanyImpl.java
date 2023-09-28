package telran.employees.service;

import java.util.concurrent.locks.*;
import java.util.stream.Collectors;

import telran.employees.dto.*;
import java.time.LocalDate;
import java.util.*;

public class CompanyImpl implements Company {
	static ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
	static Lock readLock = rwLock.readLock();
	static Lock writeLock = rwLock.writeLock();
	LinkedHashMap<Long, Employee> employees = new LinkedHashMap<>();
	TreeMap<Integer, Collection<Employee>> employeesSalary = new TreeMap<>();
	TreeMap<LocalDate, Collection<Employee>> employeesAge = new TreeMap<>();
	HashMap<String, Collection<Employee>> employeesDepartment = new HashMap<>();

	@Override
	public boolean addEmployee(Employee empl) {
		boolean res = false;
		try {
			writeLock.lock();		
		Employee emplRes = employees.putIfAbsent(empl.id(), empl);
		if (emplRes == null) {
			res = true;
			addEmployeeSalary(empl);
			addEmployeeAge(empl);
			addEmployeeDepartment(empl);
			} 
		} finally {
			writeLock.unlock();
		}
		return res;
	}

	private <T> void addToIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {
		map.computeIfAbsent(key, k -> new HashSet<>()).add(empl);
	}
	private <T> void removeFromIndex(Employee empl, T key, Map<T, Collection<Employee>> map) {

		Collection<Employee> employeesCol = map.get(key);
		employeesCol.remove(empl);
		if (employeesCol.isEmpty()) {
			map.remove(key);
		}
	}

	private void addEmployeeSalary(Employee empl) {
		addToIndex(empl, empl.salary(), employeesSalary);

	}

	private void addEmployeeAge(Employee empl) {
		addToIndex(empl, empl.birthDate(), employeesAge);

	}

	private void addEmployeeDepartment(Employee empl) {
		addToIndex(empl, empl.department(), employeesDepartment);

	}

	@Override
	public Employee removeEmployee(long id) {
		Employee res = null;
		try {
			writeLock.lock();
		
		res = employees.remove(id);
		if (res != null) {
			removeEmployeeSalary(res);
			removeEmployeeAge(res);
			removeEmployeeDepartment(res);
			}
		} finally {
			writeLock.unlock();
		}
		return res;
	}

	

	private void removeEmployeeSalary(Employee empl) {
		int salary = empl.salary();
		removeFromIndex(empl, salary, employeesSalary);

	}

	private void removeEmployeeAge(Employee empl) {
		removeFromIndex(empl, empl.birthDate(), employeesAge);

	}

	private void removeEmployeeDepartment(Employee empl) {
		removeFromIndex(empl, empl.department(), employeesDepartment);

	}

	@Override
	public Employee getEmployee(long id) {
		Employee empl = null;
		try {
			readLock.lock();
			empl = employees.get(id);
		} finally {
			readLock.unlock();
		}

		return empl;
	}

	@Override
	public List<Employee> getEmployees() {
		List<Employee> res = null;
		try {
			readLock.lock();
			res = new ArrayList<>(employees.values());
		} finally {
			readLock.unlock();
		}

		return res;
	}

	@Override
	public List<DepartmentSalary> getDepartmentSalaryDistribution() {
		List<DepartmentSalary> res = null;
		try {
			readLock.lock();
			res = employees.values().stream()
				.collect(Collectors.groupingBy(Employee::department, Collectors.averagingInt(Employee::salary)))
				.entrySet().stream().map(e -> new DepartmentSalary(e.getKey(), e.getValue())).toList();
		} finally {
			readLock.unlock();
		}
		return res;
	}

	@Override
	public List<SalaryDistribution> getSalaryDistribution(int interval) {
		List<SalaryDistribution> res = null;
		try {
			readLock.lock();
			res = employees.values().stream()
				.collect(Collectors.groupingBy(e -> e.salary() / interval, Collectors.counting())).entrySet().stream()
				.map(e -> new SalaryDistribution(e.getKey() * interval, e.getKey() * interval + interval - 1,
						e.getValue().intValue()))
				.sorted((sd1, sd2) -> Integer.compare(sd1.minSalary(), sd2.minSalary())).toList();
		} finally {
			readLock.unlock();
		}
		return res;
	}

	@Override
	public List<Employee> getEmployeesByDepartment(String department) {
		List<Employee> res = null;
		try {
			readLock.lock();		
			Collection<Employee> employeesCol = employeesDepartment.get(department);
			res = new ArrayList<>();
				if (employeesCol != null) {
					res.addAll(employeesCol);
				}
		} finally {
			readLock.unlock();
		}
		return res;
	}

	@Override
	public List<Employee> getEmployeesBySalary(int salaryFrom, int salaryTo) {
		List<Employee> res = null;
		try {
			readLock.lock();
			res = employeesSalary.subMap(salaryFrom, true, salaryTo, true).values().stream()
				.flatMap(col -> col.stream().sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))
				.toList();
		} finally {
			readLock.unlock();
		}
		return res;
	}

	@Override
	public List<Employee> getEmployeesByAge(int ageFrom, int ageTo) {
		List<Employee> res = null;
		try {
			readLock.lock();
			LocalDate dateTo = LocalDate.now().minusYears(ageFrom);
			LocalDate dateFrom = LocalDate.now().minusYears(ageTo);
			res = employeesAge.subMap(dateFrom, true, dateTo, true).values().stream()
				.flatMap(col -> col.stream()
				.sorted((empl1, empl2) -> Long.compare(empl1.id(), empl2.id())))
				.toList();
		} finally {
			readLock.unlock();
		}
		return res;		
	}

	@Override
	public Employee updateSalary(long id, int newSalary) {
		Employee empl = null;
		try {
			readLock.lock();
			empl = removeEmployee(id);
		if(empl != null) {
			Employee newEmployee = new Employee(id, empl.name(),
					empl.department(), newSalary, empl.birthDate());
			addEmployee(newEmployee);
			} 
		}finally {
			readLock.unlock();
		}		
		return empl;
	}

	@Override
	public Employee updateDepartment(long id, String newDepartment) {
		Employee empl = null;
		try {
			readLock.lock();
			empl = removeEmployee(id);
		if(empl != null) {
			Employee newEmployee = new Employee(id, empl.name(),
					newDepartment, empl.salary(), empl.birthDate());
			addEmployee(newEmployee);
			}
		} finally {
			readLock.unlock();
		}
		return empl;
	}

}
