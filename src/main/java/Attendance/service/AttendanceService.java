package Attendance.service;

import Attendance.aggregate.Attendance;
import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.repository.AttendanceRepository;
import Attendance.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

/* 설명. 트랜잭션 성공실패 여부 확인 및 회원 관련 비즈니스 로직 처리하는 클래스 */
public class AttendanceService {
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();

    MemberService memberService = new MemberService();
    MemberRepository memberRepository = memberService.getMemberRepository();
    private final ArrayList<Member> memberList = memberRepository.getMemberList();

    ArrayList<Attendance> attendanceList = AttendanceRepository.allStudentInfo();

    public AttendanceService() {
    }
    public String eachStudentAttendanceRate(int memberNo, int month) {
        String rate = attendanceRepository.getEachStudentRate(memberNo, month);
        String name = attendanceRepository.getMemberName(memberNo);

        String result = name + "님의 출석률은 " + rate + "%입니다.";

        return result;
    }

    public ArrayList<String> totalAttendanceRate(int month) {
        StringTokenizer st;
        ArrayList<String> attendanceRateList = attendanceRepository.totalAttendanceRate(memberList, month);

        ArrayList<String> result = new ArrayList<>();

        for (String eachResult : attendanceRateList) {
            st = new StringTokenizer(eachResult, " ");
            result.add(st.nextToken() + " : " + st.nextToken() + "%");
        }

        return result;
    }

    public ArrayList<String> classAttendanceRate(Classroom bt, int month) {
        ArrayList<Member> classMemberList = getClassMember(bt);
        ArrayList<String> attendanceRateList = attendanceRepository.totalAttendanceRate(classMemberList, month);

        return attendanceRateList;
    }

    private ArrayList<Member> getClassMember(Classroom bt) {
        ArrayList<Member> result = new ArrayList<>();
        for (Member member : memberList) {
            if (member.getClassroom().equals(bt)) {
                result.add(member);
            }
        }

        return result;
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
            attendanceList = AttendanceRepository.attendanceStudent(date);
        }else attendanceList = AttendanceRepository.absentStudent(date);

        for(Attendance attendance: attendanceList) {
            System.out.println("=== 학생 번호 === 학생 이름 === 출/결 ===== 날짜 =====");
            System.out.println("|     " + attendance.getMemberNo() + "      |   " + attendance.getName() + "    | " + attendance.isAttendanceStatus() + "  | " + attendance.getDate() + " |");
        }

    }
    public void classStudentAttendance() {
        Scanner sc = new Scanner(System.in);
        System.out.print("조회하실 반을 입력해주세요: ");
        String classroom = sc.nextLine().toUpperCase();

        Classroom bt = null;
        switch (classroom) {
            case "A":
                bt = Classroom.A;
                break;
            case "B":
                bt = Classroom.B;
                break;
            case "C":
                bt = Classroom.C;
                break;
        }

        ArrayList<Attendance> attendanceArrayList = attendanceRepository.getAttendanceList();
        for (Member member: getClassMember(bt)) {
            for(Attendance attendance: attendanceArrayList){
                if(member.getMemberNo() == attendance.getMemberNo()){
                    System.out.println("==== 반 ==== 이름 ==== 날짜 ==== 출석/결석 ====");
                    System.out.println("     " + member.getClassroom() + "     " + member.getName() + "   " + attendance.getDate() + "   " + attendance.isAttendanceStatus() );
                }
            }
        }
    }
}