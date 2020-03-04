package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CompanyDAO;

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

        view.write("Choose company id");
        companyDAO.getAll().forEach(x -> System.out.println(x.getCompanyID()));
        int id = Integer.parseInt(view.read());
        System.out.println(companyDAO.getByID(id).toString());
    }
}
