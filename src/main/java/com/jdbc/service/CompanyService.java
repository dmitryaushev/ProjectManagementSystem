package com.jdbc.service;

import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CompanyService {

    CompanyDAO companyDAO;

    public CompanyService(Connection connection) {
        companyDAO = new CompanyDAO(connection);
    }

    public void createCompany() {

        Scanner scanner = new Scanner(System.in);

        Company company = new Company();
        String companyName;
        String location;

        System.out.println("Enter a company name");
        companyName = scanner.nextLine();
        System.out.println("Enter a company location");
        location = scanner.nextLine();

        company.setCompanyName(companyName);
        company.setLocation(location);

        companyDAO.create(company);
    }

    public void deleteCompany(int id) {
        companyDAO.remove(id);
    }

    public Company getCompany(int id) {
        return companyDAO.getByID(id);
    }

    public List<Company> getAllCompanies() {
        return companyDAO.getAll();
    }
}
