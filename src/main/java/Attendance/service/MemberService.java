package Attendance.service;

import Attendance.aggregate.Member;
import Attendance.repository.MemberRepository;

import java.util.ArrayList;

/* 설명. 트랜잭션 성공실패 여부 확인 및 회원 관련 비즈니스 로직 처리하는 클래스 */
public class MemberService {
    private final MemberRepository memberRepository = new MemberRepository();

    ArrayList<Member> memberArrayList = memberRepository.selectAllMembers();
    public MemberService() {
    }

    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

    public void selectAllMembers() {
        ArrayList<Member> selectedMembers = memberRepository.selectAllMembers();

        /* 설명. 회원이 한 명도 없어서 조회 결과가 없더라도 ArrayList객체는 넘어온다.(Empty 상태로) */
        if (!selectedMembers.isEmpty()) {       // 회원이 한 명이라도 조회 되었다면
            for(Member m: selectedMembers) {
                System.out.println(m);
            }
            return;
        }

        /* 설명. 조건이 맞지 않아(회원이 조회되지 않아) 출력을 하는 구문 */
        System.out.println("아직 가입한 회원이 없습니다...");
    }

    /* 설명. 전달된 회원 번호를 활용해 repository에 있는 memberList로부터 해당 회원 찾아 반환 받기 */
    public void selectMember(int memberNo) {
        Member selectedMember = memberRepository.selectMember(memberNo);

        if (selectedMember == null) {
            System.out.println("해당 회원이 존재하지 않습니다.");
            return;
        }
        System.out.print("조회된 회원은 : " + selectedMember);
    }

    /* 설명. 입력받아 넘어온 회원이 가질 번호를 만들고 추가 후 repository로 전달 후 결과 확인 */
    public void registerMember(Member member) {
        int lastNumberNo = memberRepository.selectLastMemberNo();
        member.setMemberNo(lastNumberNo + 1);

        int result = memberRepository.registMember(member);
        if (result == 1) {
            System.out.println(member.getId()+"님의 회원 가입이 성공하였습니다.");
        }
    }

//    public ArrayList<Member> toGetClass(String input) {
//        ArrayList<Member> students = new ArrayList<>();
//        for (Member member: memberArrayList){
//            if (input.equals(member.getClassroom())){
//                students.add(member);
//            }
//        }
//        return students;
//    }
}