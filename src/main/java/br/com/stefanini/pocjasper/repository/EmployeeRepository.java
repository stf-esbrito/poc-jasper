package br.com.stefanini.pocjasper.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.stefanini.pocjasper.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long>{

	
}
