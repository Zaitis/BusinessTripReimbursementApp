package service;

import config.AdminConfig;
import util.AdminAuthenticator;

import java.io.IOException;

public class AdminService {

    private final AdminAuthenticator adminAuthenticator;

    public AdminService(AdminAuthenticator adminAuthenticator) {
        this.adminAuthenticator = adminAuthenticator;
    }

    public boolean checkAuthenticate(String username, String password) {
        return adminAuthenticator.authenticate(username, password);
    }

    public AdminService() {
        this.adminAuthenticator = new AdminAuthenticator(new AdminConfig());
    }
}