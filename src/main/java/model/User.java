package model;

public class User {
    //

    private int id;
    private String username;
    private String password;
    private Role role;

public enum Role {
        ADMIN, END_USER
    }
}

