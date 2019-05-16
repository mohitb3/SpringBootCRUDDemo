package com.luv2code.springboot.cruddemo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.luv2code.springboot.cruddemo.entity.Employee;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
// import org.springframework.transaction.annotation.Transactional;

@Repository
public class EmployeeDAOHibernateImpl implements EmployeeDAO {

  private EntityManager entityManager;

  @Autowired
  public EmployeeDAOHibernateImpl(EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  // @Transactional  // handles transation management so we don't have to manually start and commit transaction <-- removing this to use service layer instead
  public List<Employee> findAll() {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // create a query
    Query<Employee> query = currentSession.createQuery("from Employee", Employee.class);

    // execute query and get result list
    List<Employee> employees = query.getResultList();

    // return the results
    return employees;
  };

  @Override
  public Employee findById(int id) {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // get the employee
    Employee employee = currentSession.get(Employee.class, id);

    // return the employee
    return employee;
  };

  @Override
  public void save(Employee employee) {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // save the employee
    currentSession.saveOrUpdate(employee);
  };

  @Override
  public void deleteById(int id) {
    // get the current hibernate session
    Session currentSession = entityManager.unwrap(Session.class);

    // delete object with primary key
    Query query = currentSession.createQuery("delete from Employee where id=:employeeId");
    query.setParameter("employeeId", id);
    query.executeUpdate();
  };
}