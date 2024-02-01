package Attendance.service;

import Attendance.aggregate.Attendance;
import Attendance.repository.OverviewRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class OverviewService {
    //    private final AttendanceRepository attendanceRepository = new AttendanceRepository();
    private final OverviewRepository overviewRepository = new OverviewRepository();
    ArrayList<Attendance> attendanceList = overviewRepository.allStudentInfo();
    public OverviewService() {
    }

    public Attendance allStudentInfo() {
        System.out.println("============== 전체학생 통합 조회 ==============");

        for(Attendance attendance: attendanceList) {
            System.out.println("=== 학생 번호 === 학생 이름 === 출/결 ===== 날짜 =====");
            System.out.println("|     " + attendance.getMemberNo() + "      |   " + attendance.getName() + "    | " + attendance.isAttendanceStatus() + "  | " + attendance.getDate() + " |");
        }

        return null;
    }

    public void attendanceStudent(boolean trueFalse) {
        Scanner sc = new Scanner(System.in);

        System.out.println("============== 날짜별 출석 조회 ==============");
        System.out.print("날짜를 입력해주세요: ");
        String date = sc.nextLine();
        if (trueFalse == true) {
            attendanceList = overviewRepository.attendanceStudent(date);
        }else attendanceList = overviewRepository.absentStudent(date);

        for(Attendance attendance: attendanceList) {
            System.out.println("=== 학생 번호 === 학생 이름 === 출/결 ===== 날짜 =====");
            System.out.println("|     " + attendance.getMemberNo() + "      |   " + attendance.getName() + "    | " + attendance.isAttendanceStatus() + "  | " + attendance.getDate() + " |");
        }


    }
}
