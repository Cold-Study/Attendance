package Attendance.service;

import Attendance.aggregate.Attendance;
import Attendance.repository.OverviewRepository;

import java.util.ArrayList;

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

}
