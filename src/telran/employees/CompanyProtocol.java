package telran.employees;

import java.io.Serializable;
import java.util.List;
import java.util.function.BiFunction;
import telran.employees.dto.Employee;
import telran.employees.dto.UpdateData;
import telran.employees.dto.FromTo;
import telran.employees.service.Company;
import telran.net.ApplProtocol;
import telran.net.Request;
import telran.net.Response;
import telran.net.ResponseCode;

public class CompanyProtocol implements ApplProtocol {

	private Company company;

	public CompanyProtocol(Company company) {
		this.company = company;
	}

	@Override
	public Response getResponse(Request request) {
		Response response = null;
		String requestType = request.requestType();
		Serializable data = request.requestData();
		try {
			Serializable responseData = switch(requestType) {
			case "employee/add" -> employee_add(data);
			case "employee/get" -> employee_get(data);
			case "employees/get" -> employees_get(data);
			case "department/update" -> department_update(data);
		
			case "employee/remove" -> employee_remove(data);
			case "department/salary/distribution" -> department_salary_distribution(data);
			case "salary/distribution" -> salary_distribution(data);
			case "employees/department" -> employees_department(data);
			case "employees/salary" -> employees_salary(data);
			case "employees/age" -> employees_age(data);
			case "salary/update" -> salary_update(data);
			

			    default -> new Response(ResponseCode.WRONG_TYPE, requestType +
			    		" is unsupported in the Company Protocol");
			};
			response = (responseData instanceof Response) ? 
					(Response) responseData :
				new Response(ResponseCode.OK, responseData);
			
		} catch (Exception e) {
			response = new Response(ResponseCode.WRONG_DATA, e.toString());
		}
		return response;
	}
	
	Serializable  employeesFromTo(Serializable data, BiFunction <Integer, Integer, List<Employee>> foo) {
		FromTo fromTo = (FromTo) data;
		return (Serializable) foo.apply(fromTo.from(), fromTo.to());
	}
	
	Serializable employees_age(Serializable data) {
		return employeesFromTo(data, (a, b) -> company.getEmployeesByAge(a, b) );
	}

	Serializable employees_salary(Serializable data) {
		return employeesFromTo(data, (a, b) -> company.getEmployeesBySalary(a, b));

	}

	Serializable employees_department(Serializable data) {
		String department = (String) data;
		return (Serializable) company.getEmployeesByDepartment(department);
	}

	Serializable salary_distribution(Serializable data) {
		int interval = (int) data;

		return (Serializable) company.getSalaryDistribution(interval);
	}

	Serializable department_salary_distribution(Serializable data) {
		
		return (Serializable) company.getDepartmentSalaryDistribution();
	}

	Serializable employee_remove(Serializable data) {		
		long id = (long) data;
		return company.removeEmployee(id);
	}
	
	<T> Serializable updateBy(Long id,  BiFunction <Long, T, Employee> foo, T whatUpdate) {

		
		return (Serializable) foo.apply(id, whatUpdate);
		
	}
	

	private Serializable salary_update(Serializable data) {
		@SuppressWarnings("unchecked")
		UpdateData<String> updateData = (UpdateData<String>) data;
		long id = updateData.id();
		int updateSalary = Integer.parseInt(updateData.data());
		
		return updateBy(id, (a, b) -> company.updateSalary(a, b), updateSalary);		
	//alt:	return company.updateSalary(id, updateSalary);
	}
		
	 private Serializable department_update(Serializable data) {		
		@SuppressWarnings("unchecked")
		UpdateData<String> updateData = (UpdateData<String>) data;
		long id = updateData.id();
		String department = updateData.data();
		return updateBy(id, (a, b) -> company.updateDepartment(a, b), department);	
	//alt:	return company.updateDepartment(id, department);
	}

	Serializable employees_get(Serializable data) {
		
		return (Serializable) company.getEmployees();
	}

	Serializable employee_get(Serializable data) {
		long id = (long) data;
		return company.getEmployee(id);
	}

	Serializable employee_add(Serializable data) {
		
		Employee empl = (Employee) data;
		return company.addEmployee(empl);
	}

}
