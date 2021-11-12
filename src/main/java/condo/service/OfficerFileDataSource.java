package condo.service;

import condo.model.Officer;
import condo.model.OfficerList;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OfficerFileDataSource implements UserFileDataSource {
    private final String fileDirectoryName;
    private final String fileName;
    private OfficerList officerList;
    private Officer officer;

    public OfficerFileDataSource(String fileDirectoryName, String fileName) {
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
            Officer officer = new Officer(data[0],data[1],data[2],data[3],data[4]);
            officerList.add(officer);
        }
        reader.close();
    }
    public boolean createOfficerAccount(String username,String name,String password) throws IOException{
        officerList = new OfficerList();
        readData();
        for (Officer o : officerList.toList()) {
            if (o.getUsername().equals(username)) {
                return false;
            }
        }
            String filePath = fileDirectoryName + File.separator + fileName;
            File file = new File(filePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            String line = username + "," + name + "," + password + "," +"-" + "," + "-" ;
            writer.append(line);
            writer.newLine();
            writer.close();
            return true;
    }
    @Override
    public Officer checkLoginAccount(String username,String password) throws IOException{
        officerList = new OfficerList();
        readData();
        officer = officerList.checkUsernameAndPassword(username,password);
        if (officer != null){
            return officer;
        }
        return null;
    }
    @Override
    public boolean changePassword(Object obj, String currentPassword, String newPassword, String confirmNewPassword) throws RuntimeException,IOException {
        officer = (Officer) obj;
        officerList = new OfficerList();
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter;
        String line;
        if (officer.checkChangePassword(currentPassword,newPassword,confirmNewPassword)){
            fileWriter = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            for (Officer o : officerList.toList()) {
                if (o.getUsername().equals(officer.getUsername())) {
                    line = o.getUsername()+","+
                            o.getName()+","+
                            newPassword+","+
                            o.getLastLoginDate()+","+
                            o.getLastLoginTime();
                }
                else {
                    line = o.getUsername()+","+
                           o.getName()+","+
                           o.getPassword()+","+
                            o.getLastLoginDate()+","+
                            o.getLastLoginTime();
                }
                writer.append(line);
                writer.newLine();
            }
            writer.close();
            return true;
        }
        return false;
    }
    public OfficerList getOfficerData() {
        try {
            officerList = new OfficerList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(this.fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return officerList;
    }

    public void writeLoginTime(Officer officer) throws IOException {
        LocalDateTime loginTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy,HH:mm:ss");
        String formattedTime = loginTime.format(dateTimeFormat);
        String filePath = fileDirectoryName + File.separator + fileName;
        String line;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        for (Officer o : officerList.toList()){
            if (o.getUsername().equals(officer.getUsername())){
                line = o.getUsername() + "," + o.getName() + "," + o.getPassword() +"," + formattedTime;
            }
            else{
                line = o.getUsername() + "," + o.getName() + "," + o.getPassword() + "," +
                        o.getLastLoginDate() + "," + o.getLastLoginTime();
            }
            writer.append(line);
            writer.newLine();
        }
        writer.close();
    }
}
