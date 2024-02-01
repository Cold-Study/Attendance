package Attendance.repository;

import Attendance.aggregate.Attendance;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Map;

public class RateRepository {
    private final String DB_PATH = "src/main/java/Attendance/db/attendanceDB.dat";
    private ArrayList<Attendance> attendanceList = new ArrayList<>();

    public RateRepository() {
        loadAttendances();
    }

    private void loadAttendances() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(DB_PATH)));

            /* 설명. 파일로부터 모든 출석 정보를 읽어 attendanceList에 추가(add) */
            while (true) {
                attendanceList.add((Attendance) (ois.readObject()));
            }

        } catch (EOFException e) {
            System.out.println("출석 정보 모두 로딩됨...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // -----------------------------------------------------------------



    public double getEachStudentRate(int memberNo, int month) {
        double rate = getAttendanceRate(memberNo, month);

        return rate;
    }

    private double getAttendanceRate(int memberNo, int month) {
        int lastDayOfMonth = YearMonth.of(2024, month).lengthOfMonth();
        int count = 0;

        for (Attendance attendance : attendanceList) {
            if (memberNo == attendance.getMemberNo()) {
                count++;
            }
        }

        return (count / (lastDayOfMonth * 10.0)) * 1000.0;
    }

    public String getMemberName(int memberNo) {
        for (Attendance attendance : attendanceList) {
            if (memberNo == attendance.getMemberNo()) {
                return attendance.getName();
            }
        }

        return null;
    }

    public ArrayList<String> totalAttendanceRate() {

    }
}