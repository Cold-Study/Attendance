package Attendance.attendanceView;

import Attendance.aggregate.Classroom;
import Attendance.service.AttendanceService;

import java.util.ArrayList;
import java.util.Scanner;

public class RatePage {

    private static final AttendanceService rateService = new AttendanceService();

    /* 설명. 출석률 조회하는 페이지 */
    public static void attendanceRatePage() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 출석률 조회 ==============");
            System.out.println("1. 학생별 출석률 조회");
            System.out.println("2. 전체 학생 출석률 조회");
            System.out.println("3. 반별 학생 출석률 조회");
            System.out.println("9. 출석 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    // 설명. 학생별 출석률 조회
                    eachStudentAttendanceRatePage();
                    break;
                case 2:
                    // 설명. 전체 학생 출석률 조회
                    totalStudentAttendanceRatePage();
                    break;
                case 3:
                    // 설명. 반별 학생 출석률 조회
                    classStudentAttendanceRatePage();
                    break;
                case 9:
                    System.out.println("출석 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }

    private static void eachStudentAttendanceRatePage() {
        Scanner sc = new Scanner(System.in);
        System.out.print("학생 번호를 입력하세요: ");
        int memberNo = sc.nextInt();
        int month = selectMonth();

        System.out.println(rateService.eachStudentAttendanceRate(memberNo, month));
    }

    private static int selectMonth() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("조회할 월을 입력하세요: ");
            int month = sc.nextInt();
            if (month > 0 && month < 13) {
                return month;
            }
            System.out.println("잘못된 입력입니다.");
        }
    }

    private static void totalStudentAttendanceRatePage() {
        int month = selectMonth();
        ArrayList<String> totalAttendanceRate = rateService.totalAttendanceRate(month);

        System.out.println("\n-------------- <<전체 조회 결과>> --------------");
        for (String result : totalAttendanceRate) {
            System.out.println(result);
        }
    }

    private static void classStudentAttendanceRatePage() {
        Scanner scanner = new Scanner(System.in);

        int month = selectMonth();

        System.out.print("반을 입력하세요.(A, B, C) : ");
        String classroom = scanner.nextLine();

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

        ArrayList<String> classAttendanceRate = rateService.classAttendanceRate(bt, month);

        System.out.println("\n-------------- <<반별 조회 결과>> --------------");
        for (String result : classAttendanceRate) {
            System.out.println(result);
        }
    }
}