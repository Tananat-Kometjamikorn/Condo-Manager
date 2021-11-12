package condo.service;

import condo.model.*;

import java.io.*;

public class RoomFileDataSource {
    private final String fileDirectoryName;
    private final String fileName;
    private RoomList roomList;

    public RoomFileDataSource(String fileDirectoryName, String fileName) {
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
            Room room = new Room(data[0],data[1],data[2],data[3],data[4],data[5]);
            roomList.add(room);
        }
        reader.close();
    }

    public boolean createRoom(String condoNum,String roomFloor,String roomNum,String roomType,String res1, String res2) throws IOException {
        roomList = new RoomList();
        readData();
        String filePath = fileDirectoryName + File.separator + fileName;
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, true);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        if (res2.isEmpty()) res2 = "-";
        for (Room r : roomList.toList()){
            if (r.getRoomNumber().equals(roomNum)||res1.isEmpty()){
                return false;
            }
        }
        String line = condoNum + "," + roomFloor + "," + roomNum + "," + roomType + "," + res1 + "," + res2;
        writer.append(line);
        writer.newLine();
        writer.close();
        return true;
    }

    public RoomList getRoomList() {
        try {
            roomList = new RoomList();
            readData();
        } catch (FileNotFoundException e) {
            System.err.println(this.fileName + " not found");
        } catch (IOException e) {
            System.err.println("IOException from reading " + this.fileName);
        }
        return roomList;
    }
}
