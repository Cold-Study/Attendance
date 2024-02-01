package Attendance.aggregate;

import java.io.Serializable;
import java.time.LocalDate;

public class Attendance implements Serializable {
    private int attendanceNo;   // 출석 pk
    private int memberNo;       // 멤버번호 fk
    private String name;        // 멤버 이름
    private boolean attendanceStatus;    // 출석여부(출석:true, 결석:false)
    private LocalDate date;     // 출결 날짜

    public Attendance(int attendanceNo, int memberNo, String name, boolean attendanceStatus, LocalDate date) {
        this.attendanceNo = attendanceNo;
        this.memberNo = memberNo;
        this.name = name;
        this.attendanceStatus = attendanceStatus;
        this.date = date;
    }

    public int getAttendanceNo() {
        return attendanceNo;
    }

    public int getMemberNo() {
        return memberNo;
    }

    public String getName() {
        return name;
    }

    public boolean isAttendanceStatus() {
        return attendanceStatus;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return "Attendance{" +
                "attendanceNo=" + attendanceNo +
                ", memberNo=" + memberNo +
                ", name='" + name + '\'' +
                ", attendanceStatus=" + attendanceStatus +
                ", date=" + date +
                '}';
    }
}
