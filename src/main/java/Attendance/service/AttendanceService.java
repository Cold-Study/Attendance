package Attendance.service;

import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.repository.AttendanceRepository;
import Attendance.repository.MemberRepository;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class AttendanceService {
    private final AttendanceRepository attendanceRepository = new AttendanceRepository();



    MemberService memberService = new MemberService();
    MemberRepository memberRepository = memberService.getMemberRepository();
    private final ArrayList<Member> memberList = memberRepository.getMemberList();


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
}