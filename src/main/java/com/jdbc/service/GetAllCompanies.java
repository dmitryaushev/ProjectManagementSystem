package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.dao.CompanyDAO;

public class GetAllCompanies implements Command {

    private CompanyDAO companyDAO;

    public GetAllCompanies(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    @Override
    public String command() {
        return "Get all companies";
    }

    @Override
    public void process() {

        companyDAO.getAll().forEach(System.err::println);
        sleep();
    }
}
