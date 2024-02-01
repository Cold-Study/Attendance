package Attendance.service;

import Attendance.aggregate.Attendance;
import Attendance.repository.RateRepository;

import java.util.ArrayList;

public class RateService {

    private final RateRepository rateRepository = new RateRepository();

    public String eachStudentAttendanceRate(int memberNo, int month) {
        String rate = String.format("%.2f", rateRepository.getEachStudentRate(memberNo, month));
        String name = rateRepository.getMemberName(memberNo);

        String result = name + "님의 출석률은 " + rate + "%입니다.";

        return result;
    }

    public ArrayList<String> totalAttendanceRate() {
        ArrayList<String> rate = rateRepository.totalAttendanceRate();
        return null;
    }
}