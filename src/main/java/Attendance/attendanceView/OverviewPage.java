package Attendance.attendanceView;

import Attendance.service.OverviewService;

import java.util.Scanner;

public class OverviewPage {
    public static OverviewService overviewService = new OverviewService();
    public static void attendanceOverviewPage() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 출석 조회 ==============");
            System.out.println("1. 전체 기간 조회");
            System.out.println("2. 날짜별 조회");
            System.out.println("9. 출석 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    totalPeriodPage();
                    break;
                case 2:
                    attendanceDatePage();
                    break;
                case 9:
                    System.out.println("출석 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }

    private static void totalPeriodPage() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 전체 기간 조회 ==============");
            System.out.println("1. 통합 조회");
            System.out.println("2. 반별 조회");
            System.out.println("9. 출석 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    overviewService.allStudentInfo();
                    break;
                case 2:
                    overviewService.classStudentAttendance();
                    break;
                case 9:
                    System.out.println("출석 조회 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }

    private static void attendanceDatePage(){
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 날짜별 조회 ==============");
            System.out.println("1. 출석 인원 조회");
            System.out.println("2. 결석 인원 조회");
            System.out.println("3. 반별 조회");
            System.out.println("9. 출석 조회 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    overviewService.attendanceStudent(true);
                    break;
                case 2:
                    overviewService.attendanceStudent(false);
                    break;
                case 3:
                    overviewService.classStudentAttendance();

                    break;
                case 9:
                    System.out.println("출석 조회 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시기 바랍니다.");
            }
        }
    }
}