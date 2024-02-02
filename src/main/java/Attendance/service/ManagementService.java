package Attendance.service;

import Attendance.aggregate.Classroom;
import Attendance.repository.ManagementRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.StringTokenizer;

/* 설명. 트랜잭션 성공실패 여부 확인 및 회원 관련 비즈니스 로직 처리하는 클래스 */
public class ManagementService {
    private final ManagementRepository managementRepository= new ManagementRepository();

    /**@getMembers: 맴버들의 정보를 불러오기 위한 메서드이다. */
    public int getMembers(Classroom ci, LocalDate date){
        return managementRepository.getMembers(ci, date);
    }

    /**@changeMembers: 출석부에 결석처리를 하기 위한 메서드이다.*/
    public void changeMembers(StringTokenizer st){
        try {
            managementRepository.changeMembers(st);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
