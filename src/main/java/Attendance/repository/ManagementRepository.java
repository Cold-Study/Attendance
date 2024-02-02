package Attendance.repository;

import Attendance.aggregate.Attendance;
import Attendance.aggregate.Classroom;
import Attendance.aggregate.Member;
import Attendance.stream.MyObjectOutput;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class ManagementRepository {
    private static List<Attendance> Allmembers;
    private static List<Attendance> members;
    private List<LocalDate> dates = new ArrayList<>();
    private Set<LocalDate> savedDate = new HashSet<>();
    private static Classroom currentCi = null;
    private static LocalDate currentDate = null;
    private static String path = "src/main/java/Attendance/db/attendanceDB.dat";
    /**/
    public int getMembers(Classroom ci, LocalDate date) {
        initData(ci, date);
        //설명.해당 반(ci)맴버 정보 모두 불러오기
        filterMembers(ci);
        //설명. 맴버정보에 있는 모든 날짜들을 저장
        getDates();
        //설명. 입력받은 요일과 일치하는 저장된 요일 확인
        if(checkDate(date)!=1) return 0;
        printMembers();

        return 1;
    }

    /**@checkDate: 입력받은 날짜가 저장되어 있는 날짜에 있는지 확인하기 위한 메서드이다.*/
    private Integer checkDate(LocalDate date) {
        if(!savedDate.contains(date)){
            System.out.println("날짜 불일치");
            return 0;
        }
        return 1;
    }

    /**@initData: 초기화하기 위한 메서드*/
    private void initData(Classroom ci, LocalDate date) {
        currentCi = ci;
        currentDate = date;
        members = new ArrayList<>();
        dates= new ArrayList<>();
        savedDate = new HashSet<>();
        Allmembers = new ArrayList<>();
    }

    /**@printMembers: 출석부를 출력하기 위한 메서드이다.*/
    private static void printMembers() {
        System.out.println("⭐⭐⭐⭐⭐️출석부 ⭐⭐⭐⭐⭐");
        for (int i = 0; i < members.size(); i++) {
            String attendanceStatus = members.get(i).isAttendanceStatus() ? "(출석)" : "(결석)";
            System.out.println(members.get(i).getName() + " " + attendanceStatus);
        }
    }

    /**@getDates: 저장되어 있는 맴버들의 날짜들을 저장하기 위한 메서드이다.*/
    private void getDates() {
        //설명. 파일에 저장되어 있는 날짜 저장
        for (int i = 0; i < members.size(); i++) {
            if (!savedDate.contains(members.get(i).getDate())){
                dates.add(members.get(i).getDate());
                savedDate.add(members.get(i).getDate());
            }
        }
    }

    /**@filterMembers: 입력받은 반에 해당하는 맴버들을 members에 저장하기 위한 메서드이다.*/
    private void filterMembers(Classroom ci) {

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(path)));
            while (true) {
                Attendance data = (Attendance) ois.readObject();
                if (data.getClassroom() == ci) {
                    members.add(data);              //설명. members에 반이 일치하는 맴버들 저장
                }
                Allmembers.add(data);               //설명. Allmembers에 맴버들 저장
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

    /**@changeMembers: 입력받은 결석자 명단을 처리하기 위한 메서드로 모든 맴버정보를 담고 있는 Allmembers와 특정 반, 날짜에 해당하는 맴버정보를
     * 담고있는 members에 해당하는 결석자를 생성하고 중복을 제거한다. */
    public static void changeMembers(StringTokenizer st) throws IOException {
        while (st.hasMoreTokens()) {
            String x = st.nextToken();
            changeAllMembers(x);
            changeCiMembers(x);
        }
        //설명. 변경사항 저장
        saveMembers(Allmembers);
        //설명. 변경된 맴버정보 출력
        printMembers();
    }
    /**@changeCiMembers: 출석부에 있는 맴버들의 정보를 갱신하기 위한 메서드이다.*/
    private static void changeCiMembers(String x) {
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getName().equals(x) && members.get(i).getDate().equals(currentDate)) {
                Attendance changedAttandance = new Attendance(members.get(i).getAttendanceNo(),
                        members.get(i).getMemberNo(), members.get(i).getName(), false,
                        members.get(i).getDate(), members.get(i).getClassroom());
                members.remove(i);
                members.add(changedAttandance);
                break;
            }
        }
    }

    /**@changeAllMembers: 파일에 저장할 맴버들의 정보를 갱신하기 위한 메서드이다.*/
    private static void changeAllMembers(String x) {
        for (int i = 0; i < Allmembers.size(); i++) {
            if (Allmembers.get(i).getName().equals(x) && Allmembers.get(i).getDate().equals(currentDate) && Allmembers.get(i).getClassroom().equals(currentCi)){
                Attendance changedAttandance = new Attendance(Allmembers.get(i).getAttendanceNo(), Allmembers.get(i).getMemberNo(), Allmembers.get(i).getName(), false,
                                                                Allmembers.get(i).getDate(), Allmembers.get(i).getClassroom());
                Allmembers.remove(i);
                Allmembers.add(changedAttandance);
                break;
            }
        }
    }

    /**@saveMembers: 변경사항이 있는 멤버들의 정보를 db에 있는 attendanceDB.dat파일을 새로 갱산한다.
     * */
    private static void saveMembers(List<Attendance> members) {
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(path)));
            /* 설명. 넘어온 회원 수만큼 객체 출력하기 */
            for(Attendance m: members) {
                oos.writeObject(m);
            }
            oos.flush();                // 출력 시에는 flush 해 줄것
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if(oos != null) oos.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
