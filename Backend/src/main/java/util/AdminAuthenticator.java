package util;

import config.AdminConfig;

public class AdminAuthenticator {

    private final AdminConfig adminConfig;

    public AdminAuthenticator(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
    }

    public boolean authenticate(String username, String password) {
        String configuredUsername = adminConfig.getName();
        String configuredPassword = adminConfig.getPassword();

        return configuredUsername.equals(username) && configuredPassword.equals(password);
    }
}