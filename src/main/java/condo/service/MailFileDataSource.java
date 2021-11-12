package condo.service;

import condo.model.Mail;
import condo.model.MailList;
import condo.model.Room;
import condo.model.RoomList;

import java.io.*;

public class MailFileDataSource implements MailBoxFileDataSource {
    private final String fileDirectoryName;
    private final String fileName;
    private Mail mail;
    private MailList mailList;

    public MailFileDataSource(String fileDirectoryName, String fileName) {
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
            mail = new Mail(data[0], data[1], data[2], data[3], data[4],
                                data[5], data[6]);
            mailList.add(mail);
        }
        reader.close();
    }

    public boolean addMailInfo(String staff, String receiver, String sender, String size, String time, String date,
                            String roomNumber, RoomList roomList) throws IOException{
        mailList = new MailList();
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        if (roomList.checkRoomMatch(roomNumber,receiver)) {
            String line = staff + "," + receiver + "," + sender + "," + size + "," + time + "," + date + ","
                    + roomNumber;
            writer.append(line);
            writer.newLine();
            writer.close();
            return true;
        }
        return false;
    }

    @Override
    public void removeMail(Mail mail) throws IOException {
        boolean mailRemove = false;
        mailList = new MailList();
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        String line;
        for (Mail m : mailList.toList()) {
            if (m.equals(mail)&&!mailRemove) {
                mailRemove = true;
                continue;
            }
            else {
                line = m.getStaff() + "," + m.getReceiver() + "," + m.getSender() + "," + m.getSize() + "," + m.getTime() + "," +
                        m.getDate() + "," + m.getRoomNumber();
                writer.append(line);
                writer.newLine();
            }
        }
        writer.close();
    }

    @Override
    public MailList getMailData(){
        mailList = new MailList();
        try {
            readData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mailList;
    }

}
