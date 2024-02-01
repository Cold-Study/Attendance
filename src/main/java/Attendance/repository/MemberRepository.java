package Attendance.repository;

import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.stream.MyObjectOutput;

import java.io.*;
import java.util.ArrayList;

/* 설명. 데이터와 입출력을 위해 만들어지며 성공 실패 결과를 반환하는 클래스 */
public class MemberRepository {
    private final String DB_PATH = "src/main/java/Attendance/db/memberDB.dat";
    private ArrayList<Member> memberList = new ArrayList<>();

    /* 설명. 프로그램이 켜지자 마자(MemberRepository()가 실행되자마자) 파일에 dummy 데이터 추가 및 입력받기 */
    public MemberRepository() {

        /* 설명. 회원가입 기능 추가 후 이제는 파일이 기존에 존재하려면(처음이 아니므로) 회원 3 명으로 초기화하기를 하지 않는다. */
        File file = new File(DB_PATH);
        if (!file.exists()) {
            ArrayList<Member> members = new ArrayList<>();
            members.add(new Member(1, "user01", "pass01", "홍길동", 20,
                    Classroom.A));
            members.add(new Member(2, "user02", "pass02", "김영희", 10,
                    Classroom.B));
            members.add(new Member(3, "user03", "pass03", "오철수", 15,
                    Classroom.C));
            saveMembers(members);
        }

        loadMembers();

//        System.out.println("============ repository에서 회원정보 다 읽어왔는지 확인 ============");
//        for(Member member: memberList) {
//            System.out.println(member);
//        }
    }

    public ArrayList<Member> getMemberList() {
        return memberList;
    }

    /* 설명. 회원이 담긴 ArrayList를 던지면 파일에 출력하는 기능 */
    private void saveMembers(ArrayList<Member> members) {
        ObjectOutputStream oos = null;

        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(DB_PATH)));

            /* 설명. 넘어온 회원 수만큼 객체 출력하기 */
            for (Member member : members) {
                oos.writeObject(member);
            }

            oos.flush();                // 출력 시에는 flush 해 줄것
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /* 설명. 파일로부터 회원 객체들을 입력받아 memberList에 쌓기 */
    private void loadMembers() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(DB_PATH)));

            /* 설명. 파일로부터 모든 회원 정보를 읽어 memberList에 추가(add) */
            while (true) {
                memberList.add((Member) (ois.readObject()));
            }

        } catch (EOFException e) {
            System.out.println("회원 정보 모두 로딩됨...");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ArrayList<Member> selectAllMembers() {
        return memberList;
    }

    public Member selectMember(int memberNo) {
        for (Member member : memberList) {
            if (member.getMemberNo() == memberNo) {
                return member;
            }
        }
        return null;
    }

    public int selectLastMemberNo() {
        Member latestMember = memberList.get(memberList.size() - 1);        // 가장 최근에 가입한 회원

        return memberList.get(memberList.size() - 1).getMemberNo();              // 그 회원의 번호
    }

    /* 설명. 기존 회원(객체)에 이어서 파일을 출력하고 추가한 객체의 수를 반환(feat.DML 작업의 결과는 int) */
    public int registMember(Member member) {
        return saveMember(member);
    }

    /* 설명. 객체 출력을 할 예정인데 기존 ObjectOutputStream 대신 새로 정의한 스트림으로 회원 한 명 출력하기(feat.이어쓰기) */
    private int saveMember(Member member) {
        MyObjectOutput moo = null;
        try {
            moo = new MyObjectOutput(new BufferedOutputStream(new FileOutputStream(DB_PATH, true)));

            /* 설명. 파일로 객체 하나 출력하기 */
            moo.writeObject(member);

            /* 설명. repository의 memberList 추가 */
            memberList.add(member);

            moo.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (moo != null) moo.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        return 1;
    }
}