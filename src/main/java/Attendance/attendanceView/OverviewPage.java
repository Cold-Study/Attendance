package Attendance.attendanceView;

import Attendance.service.AttendanceService;
import Attendance.service.OverviewService;

import java.text.ParseException;
import java.util.Scanner;

public class OverviewPage {
//    public static AttendanceService attendanceService = new AttendanceService();

    public static OverviewService overviewService = new OverviewService();
    /* 설명. 출석 조회 페이지 */
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
                    // Memo. 전체 기간 조회 구현 완료
                    totalPeriodPage();
                    break;
                case 2:
                    // Memo. 날짜 별 조회 이동 구현 완료
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

    /* 설명. 날짜별 조회 페이지 */
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
                    // 설명. 출석 인원 조회 구현 위치
                    overviewService.attendanceStudent(true);
                    break;
                case 2:
                    // 설명. 결석 인원 조회 구현 위치
                    overviewService.attendanceStudent(false);
                    break;
                case 3:
                    // 설명. 반별 조회 구현 위치
                    break;
                case 9:
                    System.out.println("출석 조회 페이지로 돌아갑니다.");
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
                    // 설명. 전체 학생 출석 조회 구현 위치
                    overviewService.allStudentInfo();
                    break;
                case 2:
                    // 설명. 반별 학생 출석 조회 구현 위치
                    break;
                case 9:
                    System.out.println("출석 조회 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }
}
