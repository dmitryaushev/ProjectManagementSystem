package com.jdbc.service;

import com.jdbc.dao.DeveloperDAO;
import com.jdbc.dao.ProjectDAO;
import com.jdbc.dao.SkillDAO;
import com.jdbc.model.Developer;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DeveloperService {

    private DeveloperDAO developerDAO;
    private ProjectDAO projectDAO;
    private SkillDAO skillDAO;
    Scanner scanner = new Scanner(System.in);

    public DeveloperService(Connection connection) {
        developerDAO = new DeveloperDAO(connection);
        projectDAO = new ProjectDAO(connection);
        skillDAO = new SkillDAO(connection);
    }

    public void createDeveloper () {

        Developer developer = new Developer();
        String firstName;
        String lastName;
        String gender;
        int age;
        int salary;

        System.out.println("Enter developer first name");
        firstName = scanner.nextLine();
        System.out.println("Enter developer last name");
        lastName = scanner.nextLine();
        System.out.println("Enter developer gender");
        gender = scanner.nextLine();
        System.out.println("Enter developer age");
        age = scanner.nextInt();
        System.out.println("Enter developer salary");
        salary = scanner.nextInt();

        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setGender(gender);
        developer.setAge(age);
        developer.setSalary(salary);

        developerDAO.create(developer);
        linkDeveloper(developer);
    }

    public void deleteDeveloper(int id) {
        developerDAO.remove(id);
    }

    public Developer getDeveloper(int id) {
        return developerDAO.getByID(id);
    }

    public List<Developer> getAll() {
        return developerDAO.getAll();
    }

    private void linkDeveloper(Developer developer) {

        int developerID;
        int projectID;
        int skillID;
        String firstName = developer.getFirstName();
        String lastName = developer.getLastName();
        String projectName;

        developerID = getAll()
                .stream()
                .filter(x -> x.getFirstName().equals(firstName) && x.getLastName().equals(lastName))
                .map(Developer::getDeveloperID)
                .collect(Collectors.toList())
                .get(0);

        while (true) {
            System.out.println("Chose project id");
            projectDAO.getAll().forEach(x -> System.out.printf("%d. %s \n", x.getProjectID(), x.getProjectName()));
            projectID = scanner.nextInt();

            if (projectID == 0)
                break;

            developerDAO.linkDeveloperProject(developerID, projectID);
        }

        while (true) {
            System.out.println("Choose skill id");
            skillDAO.getAll().forEach(System.out::println);
            skillID = scanner.nextInt();

            if (skillID == 0)
                break;

            developerDAO.linkDeveloperSkill(developerID, skillID);
        }
    }
}
