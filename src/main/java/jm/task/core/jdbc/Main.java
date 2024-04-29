package jm.task.core.jdbc;


import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        UserServiceImpl usi = new UserServiceImpl();

        usi.createUsersTable();

        usi.saveUser("Ivan", "Ivanov", (byte) 25);
        usi.saveUser("Oleg", "Petrov", (byte) 20);
        usi.saveUser("Olga", "Popova", (byte) 23);
        usi.saveUser("Yuliya", "Frolova", (byte) 27);

        usi.getAllUsers();
        usi.cleanUsersTable();
        usi.dropUsersTable();

    }
}
