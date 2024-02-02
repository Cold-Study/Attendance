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
    static Scanner sc = new Scanner(System.in);
    static Classroom ci=null;
    static LocalDate date=null;
    static char classroom;
    static ManagementService managementService = new ManagementService();

    /**
         * @출석관리페이지<br>
         * 사용자는 반을 입력하고, 날짜를 입력하여 원하는 출석부를 불러와 해당 출석부의 정보를 조회할 수 있다. <br>
         * 출석부를 조회하여 사용자가 결석 처리로 수정하고 싶은 맴버의 이름들을 입력하여 해당 맴버들을 처리 후 변경사항을 attendanceDB.dat에 갱신한 후 프로그램을 종료한다.
     * */
    public static void attendanceManagementPage() {
        System.out.println("\n============== 출결 관리 ==============");
        inputClassroom();
        inputDate();
    }

    /**@inputDate(): 날짜를 입력받기 위한 메서드이다.*/
    private static void inputDate() {
        while (true) {
            System.out.print("날짜를 입력하세요(숫자만입력): ");
            String userInput = sc.nextLine();
            if (findInputDate(userInput)==0) {
                System.out.println("찾지 못함 다시 입력하세요..");
                continue;
            }
            System.out.print("결석한 사람을 입력하세요: ");
            if(inputAbsentees()){
                System.out.println("성공적으로 처리했습니다. 메뉴로 돌아갑니다.");
                break;
            }
        }
    }
    /**@inputClassroom(): 반을 입력받기 위한 메서드이다.*/
    private static void inputClassroom() {
        while (true) {
            System.out.print("해당 반을 입력해주세요.(A, B, C)");
            classroom = sc.nextLine().toUpperCase().charAt(0);
            if(FindInputClassroom(classroom)) break;
        }
    }

    /**@inputAbsentees: 결석자들을 입력받는 메서드이다. 결석자들을 ' '간격으로 입력받아 해당 StringTokenizer를 managementService에 넘긴다.
     * */
    private static boolean inputAbsentees() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st= null;
        try {
            st = new StringTokenizer(br.readLine());
            managementService.changeMembers(st);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**@FindInputClassroom: 입력한 반에 대하여 일치하는 반이 존재하는지 확인하기 위한 메서드이다. 일치하는 반이 있는 경우 true를 반환하고
     * 만약 일치하는 반이 없다면 "해당 반을 찾을 수 없습니다."를 출력하고 false를 반환한다. */
    private static boolean FindInputClassroom(char classroom) {
        switch (classroom) {
            case 'A':
                ci = Classroom.A;
                break;
            case 'B':
                ci = Classroom.B;
                break;
            case 'C':
                ci = Classroom.C;
                break;
            default:
                System.out.println("해당 반을 찾을 수 없습니다.");
                return false;
        }
        return true;
    }

    /**@findInputDate: 입력받은 날짜가 존재하는지 확인하기 위한 메서드이다. 사용자가 입력한 날짜 정보를 파싱하여 LocalDate date에 담는다.
     * 잘못된 날짜 형식을 입력할 경우 "잘못된 날짜 형식입니다. 다시 시도하세요." 출력을 한 후 메서드를 종료한다. <br>
     * 날짜가 정상적으로 입력되었을 경우 date와 일치하는 date가 있는지 검사하여 있으면 1을 반환하고 없다면 0을 반환한다.*/
    private static int findInputDate(String userInput) {
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
            return 0;
        }
        return 1;
    }

    private static void updateAttendancePage() {
        // 설명. 출석부 수정 구현 위치
        //반을 받고, 날짜 받고 해당

    }

    private static void addAttendancePage() {
        // 설명. 출석부 추가 구현 위치

    }

}
