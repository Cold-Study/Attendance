package Attendance.service;

import Attendance.repository.AttendanceRepository;

/* 설명. 트랜잭션 성공실패 여부 확인 및 회원 관련 비즈니스 로직 처리하는 클래스 */
public class AttendanceService {
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();

    public AttendanceService() {
    }
}
