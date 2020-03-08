package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

import java.util.List;
import java.util.stream.Collectors;

public class DeleteCompany implements Command {

    private View view;
    private CompanyDAO companyDAO;

    public DeleteCompany(View view, CompanyDAO companyDAO) {
        this.view = view;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Delete company";
    }

    @Override
    public void process() {

        List<Company> companiesList = companyDAO.getAll();
        List<Integer> idList = companiesList
                .stream()
                .map(Company::getCompanyID)
                .collect(Collectors.toList());
        int companyID;

        do {
            view.write("Choose company id");
            companiesList.forEach(System.out::println);
            companyID = Integer.parseInt(view.read());
        } while (!matchInt(companyID, idList));

        companyDAO.remove(companyID);
        view.redWrite("Company deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
