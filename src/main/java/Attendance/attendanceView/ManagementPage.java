package Attendance.attendanceView;

import java.util.Scanner;

public class ManagementPage {

    /* 설명. 출석 관리 페이지 */
    public static void attendanceManagementPage() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 출결 관리 ==============");
            System.out.println("1. 출석부 추가");
            System.out.println("2. 출석부 수정");
            System.out.println("9. 출석 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    // 설명. 출석부 추가
                    addAttendancePage();
                    break;
                case 2:
                    // 설명. 출석부 수정
                    updateAttendancePage();
                    break;
                case 9:
                    System.out.println("출석 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }

    private static void updateAttendancePage() {
        // 설명. 출석부 수정 구현 위치
    }

    private static void addAttendancePage() {
        // 설명. 출석부 추가 구현 위치
    }
}
