package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.DataAccessObject;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

import java.sql.Connection;

public class CreateCompany implements Command {

    private View view;
    private CompanyDAO companyDAO;

    public CreateCompany(View view, CompanyDAO companyDAO) {
        this.view = view;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Create company";
    }

    @Override
    public void process() {

        view.write("Enter a company title");
        String title = view.read();
        view.write("Enter a company location");
        String location = view.read();
        Company company = new Company();
        company.setCompanyName(title);
        company.setLocation(location);
        companyDAO.create(company);
    }
}
