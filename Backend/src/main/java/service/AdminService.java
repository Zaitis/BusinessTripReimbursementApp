package service;

import config.AdminConfig;
import util.AdminAuthenticator;

import java.io.IOException;

public class AdminService {

    private AdminAuthenticator adminAuthenticator = new AdminAuthenticator(new AdminConfig());

    public boolean checkAuthenticate(String username, String password) throws IOException {
        return adminAuthenticator.authenticate(username, password);
    }
}
