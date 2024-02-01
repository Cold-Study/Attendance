package Attendance.service;

import Attendance.aggregate.Classroom;
import Attendance.repository.ManagementRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.StringTokenizer;

/* 설명. 트랜잭션 성공실패 여부 확인 및 회원 관련 비즈니스 로직 처리하는 클래스 */
public class ManagementService {
    private final ManagementRepository managementRepository= new ManagementRepository();


    public int getMembers(Classroom ci, LocalDate date){
        return managementRepository.getMembers(ci, date);
    }

    public void changeMembers(StringTokenizer st){
        try {
            managementRepository.changeMembers(st);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
