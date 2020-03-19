package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

import java.util.List;
import java.util.stream.Collectors;

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

        view.write("Enter a company id");
        int companyID = Integer.parseInt(view.read());
        Company company = companyDAO.getByID(companyID);

        if (company == null)
            throw new IllegalArgumentException(String.format("Company with id %d not exist", companyID));

        view.write("Update company? Y|N");
        view.write(company.toString());
        switch (view.read()) {
            case "Y":
                break;
            case "N":
                return;
            default:
                throw new IllegalArgumentException("Wrong input");
        }

        view.write("Enter a company name");
        String companyName = view.read();
        view.write("Enter a company location");
        String location = view.read();

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
