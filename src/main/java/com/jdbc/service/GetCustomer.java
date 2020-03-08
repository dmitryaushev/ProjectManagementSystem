package com.jdbc.service;

import com.jdbc.config.Command;
import com.jdbc.config.View;
import com.jdbc.dao.CustomerDAO;
import com.jdbc.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

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

        List<Integer> idList = customerDAO.getAll()
                .stream()
                .map(Customer::getCustomerID)
                .collect(Collectors.toList());
        int customerID;

        do {
            view.write("Choose customer id");
            idList.forEach(System.out::println);
            customerID = Integer.parseInt(view.read());
        } while (!matchInt(customerID, idList));

        view.redWrite(customerDAO.getByID(customerID).toString());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
