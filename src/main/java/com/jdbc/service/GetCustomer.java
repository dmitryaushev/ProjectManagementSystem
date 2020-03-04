package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;

public class GetCustomer implements Command {

    private View view;
    private CustomerDAO customerDAO;

    public GetCustomer(View view, CustomerDAO customerDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Get customer";
    }

    @Override
    public void process() {

        view.write("Choose customer id");
        customerDAO.getAll().forEach(x -> System.out.println(x.getCustomerID()));
        int id = Integer.parseInt(view.read());
        System.out.println(customerDAO.getByID(id).toString());
    }
}
