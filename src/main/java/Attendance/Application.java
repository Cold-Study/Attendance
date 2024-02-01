package Attendance;


import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.attendanceView.ManagementPage;
import Attendance.attendanceView.OverviewPage;
import Attendance.attendanceView.RatePage;
import Attendance.service.AttendanceService;
import Attendance.service.MemberService;

import java.util.Scanner;

/* 설명. 프로그램 실행 및 메뉴 출력과 사용자의 입력을 받을 View에 해당하는 클래스 */
public class Application {
    private static final MemberService memberService = new MemberService();
//    private static final AttendanceService attendanceService = new AttendanceService();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 메인 페이지 ==============");
            System.out.println("1. 회원 페이지");
            System.out.println("2. 출석 페이지");
            System.out.println("9. 프로그램 종료");
            System.out.print("메뉴 입력 : ");

            int input = sc.nextInt();
            switch (input) {
                case 1:
                    // 설명. 회원 페이지
                    userPage();
                    break;
                case 2:
                    // 설명. 출석 페이지
                    attendancePage();
                    break;
                case 9:
                    System.out.println("프로그램을 종료합니다...");
                    sc.close();
                    return;
            }
        }
    }

    private static void attendancePage() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 출석 관리 프로그램 ==============");
            System.out.println("1. 출결 관리");
            System.out.println("2. 출석 조회");
            System.out.println("3. 출석률 조회");
            System.out.println("9. 메인 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    // 설명. 출석 관리 페이지
                    ManagementPage.attendanceManagementPage();
                    break;
                case 2:
                    // 설명. 출석 조회 페이지
                    OverviewPage.attendanceOverviewPage();
                    break;
                case 3:
                    // 설명. 출석률 조회 페이지
                    RatePage.attendanceRatePage();
                    break;
                case 9:
                    System.out.println("메인 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }

    /* 설명. 회원 관리 페이지 */
    private static void userPage() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n============== 회원 관리 프로그램 ==============");
            System.out.println("1. 모든 회원 정보 보기");
            System.out.println("2. 회원 찾기");
            System.out.println("3. 회원 가입");
            System.out.println("9. 메인 페이지로 돌아가기");
            System.out.print("메뉴를 선택해주세요 : ");
            int input = sc.nextInt();

            switch (input) {
                case 1:
                    // 설명. 모든 회원 정보 보기
                    memberService.selectAllMembers();
                    break;
                case 2:
                    // 설명. 회원 찾기
                    memberService.selectMember(chooseMemberNo());
                    break;
                case 3:
                    // 설명. 회원 가입
                    memberService.registerMember(signUp());
                    break;
                case 9:
                    System.out.println("메인 페이지로 돌아갑니다.");
                    return;
                default:
                    System.out.println("잘못된 번호입니다. 확인 후 다시 입력해주시길 바랍니다.");
            }
        }
    }

    /* 설명. 사용자로부터 회원번호를 제외한 정보를 입력받아(회원가입용 정보) Mmeber 타입으로 반환하는 메소드(파싱 및 가공처리) */
    private static Member signUp() {
        Member newInfo = null;

        Scanner sc = new Scanner(System.in);
        System.out.print("아이디를 입력하세요 : ");
        String id = sc.nextLine();

        System.out.print("비밀번호를 입력하세요 : ");
        String pwd = sc.nextLine();

        System.out.print("이름 : ");
        String name = sc.nextLine();

        System.out.print("나이를 입력하세요 : ");
        int age = sc.nextInt();

        sc.nextLine();      // nextInt 개행 지우기용

        System.out.print("반(Class)을 입력하세요(A, B, C) : ");
        String classroom = sc.nextLine().toUpperCase();

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

        newInfo = new Member(id, pwd, name, age);

        /* 필기.
         *  회원으로부터 회원가입을 위한 정보를 입력받아 Member 타입객체 하나로 가공 처리할 때, 방법이 두 가지 있다.
         *   1. 생성자 방식(장점 : 한 줄 코딩 가능, 단점 : 따로 생성자 추가)
         *   2. setter 방식(장점 : 초기화할 갯수 수정 용이, 단점 : 코딩 줄 수 늘어날 수 있음)
         */
        newInfo.setClassroom(bt);

        return newInfo;
    }

    /* 설명. 회원 한 명 조회를 위해 해당 회원 번호를 입력받아 반환하는 메소드 */
    private static int chooseMemberNo() {
        Scanner sc = new Scanner(System.in);
        System.out.print("회원 번호를 입력하세요 : ");

        return sc.nextInt();
    }
}