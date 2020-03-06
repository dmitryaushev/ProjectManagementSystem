package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

public class UpdateCompany implements Command {

    private View view;
    private CompanyDAO companyDAO;

    public UpdateCompany(View view, CompanyDAO companyDAO) {
        this.view = view;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Update company";
    }

    @Override
    public void process() {

        view.write("Choose company id");
        companyDAO.getAll().forEach(System.out::println);
        int companyID = Integer.parseInt(view.read());
        view.write("Enter a company name");
        String companyName = view.read();
        view.write("Enter a company location");
        String location = view.read();

        Company company = new Company();
        company.setCompanyID(companyID);
        company.setCompanyName(companyName);
        company.setLocation(location);

        companyDAO.update(company);
        view.redWrite("Company updated");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
