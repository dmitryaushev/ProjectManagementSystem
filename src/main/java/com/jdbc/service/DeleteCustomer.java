package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;

public class DeleteCustomer implements Command {

    private View view;
    private CustomerDAO customerDAO;

    public DeleteCustomer(View view, CustomerDAO customerDAO) {
        this.view = view;
        this.customerDAO = customerDAO;
    }

    @Override
    public String command() {
        return "Delete customer";
    }

    @Override
    public void process() {

        view.write("Choose customer id");
        customerDAO.getAll().forEach(System.out::println);
        int id = Integer.parseInt(view.read());
        customerDAO.remove(id);
        view.redWrite("Customer deleted");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
