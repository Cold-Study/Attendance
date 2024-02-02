package Attendance.repository;

import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.stream.MyObjectOutput;

import java.io.*;
import java.util.ArrayList;
public class MemberRepository {
    private final String DB_PATH = "src/main/java/Attendance/db/memberDB.dat";
    private ArrayList<Member> memberList = new ArrayList<>();

    public MemberRepository() {

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

    private void loadMembers() {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(DB_PATH)));

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

    public int registMember(Member member) {
        return saveMember(member);
    }

    private int saveMember(Member member) {
        MyObjectOutput moo = null;
        try {
            moo = new MyObjectOutput(new BufferedOutputStream(new FileOutputStream(DB_PATH, true)));
            moo.writeObject(member);
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