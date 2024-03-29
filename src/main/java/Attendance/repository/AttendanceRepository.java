package Attendance.repository;

import Attendance.aggregate.Attendance;
import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;

import java.io.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;

public class AttendanceRepository {
    private final String DB_PATH = "src/main/java/Attendance/db/attendanceDB.dat";
    private static ArrayList<Attendance> attendanceList = new ArrayList<>();

    public AttendanceRepository() {

        File file = new File(DB_PATH);
        if (!file.exists()) {ArrayList<Attendance> attendances = new ArrayList<>();
            attendances.add(new Attendance(1, 1, "홍길동", true,
                    LocalDate.parse("2024-01-02"), Classroom.A));
            attendances.add(new Attendance(2, 2, "김영희", false,
                    LocalDate.parse("2024-01-02"), Classroom.A));
            attendances.add(new Attendance(3, 3, "오철수", true,
                    LocalDate.parse("2024-01-02"), Classroom.B));
            attendances.add(new Attendance(4, 1, "홍길동", true,
                    LocalDate.parse("2024-01-03"),Classroom.B));
            attendances.add(new Attendance(5, 2, "김영희", false,
                    LocalDate.parse("2024-01-03"),Classroom.C));
            attendances.add(new Attendance(6, 3, "오철수", false,
                    LocalDate.parse("2024-01-03"), Classroom.C));

            saveAttendances(attendances);
        }

        loadAttendances();
    }

    public String getEachStudentRate(int memberNo, int month) {
        String rate = getAttendanceRate(memberNo, month);

        return rate;
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

    private void saveAttendances(ArrayList<Attendance> attendances) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(DB_PATH)));

            for (Attendance attendance : attendances) {
                oos.writeObject(attendance);
            }

            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void loadAttendances() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(DB_PATH)));

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

   

    public static ArrayList<Attendance> allStudentInfo() {
        return attendanceList;
    }

    public static ArrayList<Attendance> attendanceStudent(String date) {
        ArrayList<Attendance> attendanceArrayList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getDate().toString().equals(date) ) {
                if (attendance.isAttendanceStatus() == true){
                    attendanceArrayList.add(attendance);
                }
            }
        }
        return attendanceArrayList;
    }

    public static ArrayList<Attendance> absentStudent(String date) {
        ArrayList<Attendance> attendanceArrayList = new ArrayList<>();
        for (Attendance attendance : attendanceList) {
            if (attendance.getDate().toString().equals(date) ) {
                if (attendance.isAttendanceStatus() == false){
                    attendanceArrayList.add(attendance);
                }
            }
        }
        return attendanceArrayList;
    }


    public ArrayList<Attendance> getAttendanceList() {
        return attendanceList;
    }
}