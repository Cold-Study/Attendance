package Attendance.repository;

import Attendance.aggregate.Attendance;
import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.stream.MyObjectOutput;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ManagementRepository {
    private static List<Attendance> members;
    private List<LocalDate> dates = new ArrayList<>();
    private Set<LocalDate> savedDate = new HashSet<>();
    private static List<Attendance> Allmembers= new ArrayList<>();;

    private static Classroom currentCi = null;
    private static LocalDate currentDate = null;
    public ManagementRepository() {
    }

    public int getMembers(Classroom ci, LocalDate date) {
        if(members!=null) {
            printMembers();
            return 1;
        }
        currentCi = ci;
        currentDate = date;
        members = new ArrayList<>();
        dates= new ArrayList<>();
        savedDate = new HashSet<>();
        ObjectInputStream ois = null;
        getMembers(ci, ois);
        findDates();

        if(!savedDate.contains(date)){
            System.out.println("날짜 불일치");
            return 0;
        }
        else{

            printMembers();
        }
        System.out.println(dates.toString());
        return 1;
    }

    private static void printMembers() {
        System.out.println("⭐⭐⭐⭐⭐️출석부 ⭐⭐⭐⭐⭐");
        for (int i = 0; i < members.size(); i++) {
            String attendanceStatus = members.get(i).isAttendanceStatus() ? "(출석)" : "(결석)";
            System.out.println(members.get(i).getName() + " " + attendanceStatus);
        }
    }


    private void findDates() {
        //설명. 파일에 저장되어 있는 날짜 저장
        for (int i = 0; i < members.size(); i++) {
            if (!savedDate.contains(members.get(i).getDate())){
                dates.add(members.get(i).getDate());
                savedDate.add(members.get(i).getDate());
            }
        }
    }

    private void getMembers(Classroom ci, ObjectInputStream ois) {
        //설명. 맴버 데이터 받아옴.
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream("src/main/java/Attendance/db/attendanceDB.dat")));


            while (true) {
                Attendance data = (Attendance) ois.readObject();
                //설명. 데이터 정제하지않고 이건 따로 구분지어두고 전체 맴버 남겨둘 것
                if (data.getClassroom() == ci) {
                    members.add(data);
                }
                Allmembers.add(data);
            }
        } catch (EOFException e) {
            // 파일의 끝에 도달하면 처리
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (ois != null) ois.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void changeMembers(StringTokenizer st) throws IOException {
//        MyObjectOutput moo = new MyObjectOutput(new BufferedOutputStream(new FileOutputStream("src/main/java/Attendance/db/attendanceDB.dat", true)));
//        /* 설명: 파일로 객체 하나 출력하기 */
//        moo.writeObject(st);
//        moo.flush();
//        moo

        while (st.hasMoreTokens()) {

            String x = st.nextToken();
            System.out.println(x+"찾는중....");
            for (int i = 0; i < Allmembers.size(); i++) {
                if (Allmembers.get(i).getName().equals(x) && Allmembers.get(i).getDate()==currentDate && Allmembers.get(i).getClassroom() == currentCi) {
                    System.out.println("found!!!");
                    Attendance changedAttandance = new Attendance(Allmembers.get(i).getAttendanceNo(),
                            Allmembers.get(i).getMemberNo(), Allmembers.get(i).getName(), false,
                            Allmembers.get(i).getDate(), Allmembers.get(i).getClassroom());
                    Allmembers.remove(i);
                    Allmembers.add(changedAttandance);
                }
            }

            for (int i = 0; i < members.size(); i++) {
                if (members.get(i).getName().equals(x) && members.get(i).getDate()==currentDate) {
                    System.out.println("found!!");
                    Attendance changedAttandance = new Attendance(members.get(i).getAttendanceNo(),
                            members.get(i).getMemberNo(), members.get(i).getName(), false,
                            members.get(i).getDate(), members.get(i).getClassroom());
                    members.remove(i);
                    members.add(changedAttandance);
                }
            }
        }
        printMembers();
    }


}
