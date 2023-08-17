package util;


import config.AdminConfig;

public class AdminAuthenticator {
    private AdminConfig adminConfig;

    public AdminAuthenticator(AdminConfig adminConfig) {
        this.adminConfig = adminConfig;
    }

    public boolean authenticate(String name, String password) {
        String expectedName = adminConfig.getName();
        String expectedPassword = adminConfig.getPassword();

        return expectedName.equals(name) && expectedPassword.equals(password);
    }
}
