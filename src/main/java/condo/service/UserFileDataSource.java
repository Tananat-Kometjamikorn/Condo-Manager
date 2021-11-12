package condo.service;

import java.io.IOException;

public interface UserFileDataSource {

    Object checkLoginAccount(String username, String password) throws IOException;
    boolean changePassword(Object obj, String currentPassword, String newPassword, String confirmNewPassword) throws IOException;
}
