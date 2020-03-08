package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;
import com.jdbc.model.Company;

import java.util.List;
import java.util.stream.Collectors;

public class GetCompany implements Command {

    private View view;
    private CompanyDAO companyDAO;

    public GetCompany(View view, CompanyDAO companyDAO) {
        this.view = view;
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Get company";
    }

    @Override
    public void process() {

        List<Integer> idList = companyDAO.getAll()
                .stream()
                .map(Company::getCompanyID)
                .collect(Collectors.toList());
        int companyID;

        do {
            view.write("Choose company id");
            idList.forEach(System.out::println);
            companyID = Integer.parseInt(view.read());
        } while (!matchInt(companyID, idList));

        view.redWrite(companyDAO.getByID(companyID).toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
