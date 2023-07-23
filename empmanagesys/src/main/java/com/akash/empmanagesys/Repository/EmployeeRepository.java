package com.akash.empmanagesys.Repository;

import com.akash.empmanagesys.Model.Employee;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends CouchbaseRepository<Employee, String> {
}
