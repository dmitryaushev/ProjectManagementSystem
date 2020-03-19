package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

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

        view.write("Enter a customer id");
        int customerID = Integer.parseInt(view.read());
        Customer customer = customerDAO.getByID(customerID);

        if (customer == null)
            throw new IllegalArgumentException(String.format("Customer with id %d not exist", customerID));

        view.write("Delete customer? Y|N");
        view.write(customer.toString());
        switch (view.read()) {
            case "Y":
                break;
            case "N":
                return;
            default:
                throw new IllegalArgumentException("Wrong input");
        }

        customerDAO.unlinkCustomerProject(customerID);
        customerDAO.delete(customerID);
        view.redWrite("Customer deleted");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
