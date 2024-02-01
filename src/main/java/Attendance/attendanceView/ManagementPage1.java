package Attendance.attendanceView;

import Attendance.aggregate.Attendance;
import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.repository.MemberRepository;
import Attendance.service.ManagementService;


import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class ManagementPage1 {
    private static List<Attendance> classrooms = null;
    static Scanner sc = new Scanner(System.in);
    static Classroom ci=null;
    static LocalDate date=null;
//    static ManagementRepository managementRepository = new ManagementRepository();
    static ManagementService managementService = new ManagementService();

    /* 설명. 출석 관리 페이지 */
    public static void attendanceManagementPage() {

            System.out.println("\n============== 출결 관리 ==============");
            System.out.print("해당 반을 입력해주세요.(A, B, C)");
            char classroom = sc.nextLine().toUpperCase().charAt(0);
            switch (classroom){
                case 'A': ci = Classroom.A; break;
                case 'B': ci = Classroom.B; break;
                case 'C': ci = Classroom.C; break;
                default:
                    System.out.println("해당 반을 찾을 수 없습니다.");
            }

        while (true) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


            System.out.print("날짜를 입력하세요(숫자만입력!): ");

            String userInput = sc.nextLine();

            try {
                if (userInput.matches("\\d{8}")) {
                    userInput = userInput.substring(0, 4) + "-" + userInput.substring(4, 6) + "-" + userInput.substring(6);
                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                date = LocalDate.parse(userInput, formatter);

                // 변환된 LocalDate 사용
                System.out.println("입력한 날짜: " + date);
            } catch (Exception e) {
                System.err.println("잘못된 날짜 형식입니다. 다시 시도하세요.");
                // 예외 처리를 통해 잘못된 입력에 대한 대응 가능
            }

           if(managementService.getMembers(ci,date) ==0 ){
               System.out.println("찾지 못함");
               continue;
           }

            System.out.print("결석한 사람을 입력하세요: ");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            StringTokenizer st= null;
            try {
                st = new StringTokenizer(br.readLine());

                managementService.changeMembers(st);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
                return;

        }
    }




    private static void updateAttendancePage() {
        // 설명. 출석부 수정 구현 위치
        //반을 받고, 날짜 받고 해당

    }

    private static void addAttendancePage() {
        // 설명. 출석부 추가 구현 위치

    }

}
