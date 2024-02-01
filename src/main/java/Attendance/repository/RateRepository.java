package Attendance.repository;

import Attendance.aggregate.Attendance;
import Attendance.aggregate.Member;

import java.io.*;
import java.time.YearMonth;
import java.util.ArrayList;

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


    public String getEachStudentRate(int memberNo, int month) {
        String rate = getAttendanceRate(memberNo, month);

        return rate;
    }

    private String getAttendanceRate(int memberNo, int month) {
        int lastDayOfMonth = YearMonth.of(2024, month).lengthOfMonth();
        int count = 0;

        for (Attendance attendance : attendanceList) {
            if (memberNo == attendance.getMemberNo() && attendance.isAttendanceStatus()) {
                count++;
            }
        }

        return String.format("%.2f", (count / (lastDayOfMonth * 10.0)) * 1000.0);
    }

    public String getMemberName(int memberNo) {
        for (Attendance attendance : attendanceList) {
            if (memberNo == attendance.getMemberNo()) {
                return attendance.getName();
            }
        }

        return null;
    }

    public ArrayList<String> totalAttendanceRate(ArrayList<Member> memberList, int month) {
        ArrayList<String> totalAttendanceRate = new ArrayList<>();
        for (Member member : memberList) {
            String result;
            result = member.getName() + " " + getAttendanceRate(member.getMemberNo(), month);
            totalAttendanceRate.add(result);
        }

        return totalAttendanceRate;
    }
}