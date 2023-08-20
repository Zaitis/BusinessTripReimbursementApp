package service;

import com.sun.net.httpserver.HttpExchange;
import config.AdminConfig;
import controller.dto.RateUpdateData;
import model.Type;
import util.AdminAuthenticator;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.EnumMap;

public class AdminService {

    private AdminAuthenticator adminAuthenticator = new AdminAuthenticator(new AdminConfig());

    public boolean checkAuthenticate(String username, String password) throws IOException {
        return adminAuthenticator.authenticate(username, password);
    }
}
