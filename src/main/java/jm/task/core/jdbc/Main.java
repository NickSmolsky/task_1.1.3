package jm.task.core.jdbc;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        User user1 = new User("Robert", "Plant", (byte) 73);
        User user2 = new User("Jimmy", "Page", (byte) 77);
        User user3 = new User("John", "Bonham", (byte) 32);
        User user4 = new User("John_Paul", "Jones", (byte) 75);
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser(user1.getName(), user1.getLastName(), user1.getAge());
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
        userService.removeUserById(1L);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        Util.shutdown();
    }
}
