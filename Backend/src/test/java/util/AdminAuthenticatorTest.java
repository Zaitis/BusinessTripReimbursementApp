package util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import config.AdminConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class AdminAuthenticatorTest {

    @Test
    void shouldCheckAuthenticate() {
        AdminAuthenticator adminAuthenticator = new AdminAuthenticator(new AdminConfig());
        assertFalse(adminAuthenticator.authenticate("Name", "iloveyou"));
        assertFalse(adminAuthenticator.authenticate("Admin", "password"));
        assertFalse(adminAuthenticator.authenticate("admin", "Password"));
        assertTrue(adminAuthenticator.authenticate("admin", "password"));
    }

}

