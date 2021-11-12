package condo.service;

import condo.model.User;
import condo.model.UserList;

import java.io.*;

public class AdminFileDataSource implements UserFileDataSource {

    private final String fileDirectoryName;
    private final String fileName;
    private UserList adminList;
    private User admin;

    public AdminFileDataSource(String fileDirectoryName, String fileName) {
        this.fileDirectoryName = fileDirectoryName;
        this.fileName = fileName;
        checkFileIsExisted();
    }
    private void checkFileIsExisted() {
        File file = new File(fileDirectoryName);
        if (!file.exists()) {
            file.mkdirs();
        }
        String filePath = fileDirectoryName + File.separator + fileName;
        file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create " + filePath);
            }
        }
    }
    private void readData() throws IOException {
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileReader fileReader = new FileReader(file);
        BufferedReader reader = new BufferedReader(fileReader);
        String line;
        while ((line = reader.readLine()) != null) {
            String[] data = line.split(",");
            admin = new User(data[0],data[1],data[2]);
            adminList.add(admin);
        }
        reader.close();
    }

    @Override
    public User checkLoginAccount(String username,String password) throws IOException{
        adminList = new UserList();
        readData();
        return adminList.checkUsernameAndPassword(username, password);
    }
    @Override
    public boolean changePassword(Object obj, String currentPassword, String newPassword, String confirmNewPassword) throws RuntimeException,IOException {
        admin = (User) obj;
        adminList = new UserList();
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter;
        String line;
        if (admin.checkChangePassword(currentPassword,newPassword,confirmNewPassword)){
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (User a : adminList.toList()) {
                if (a.getUsername().equals(admin.getUsername())) {
                    line = a.getUsername()+","+
                                    a.getName()+","+
                                    newPassword;
                }
                else {
                    line = a.getUsername()+","+
                            a.getName()+","+
                            a.getPassword();
                }
                writer.append(line);
                writer.newLine();
            }
            writer.close();
        return true;
        }
    return false;
    }
}
