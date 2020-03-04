package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;


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

        view.write("Enter a company name");
        String name = view.read();
        view.write("Enter a company location");
        String location = view.read();

        Company company = new Company();
        company.setCompanyName(name);
        company.setLocation(location);
        companyDAO.create(company);

        System.err.println("Company created");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
