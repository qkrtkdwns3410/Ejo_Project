import com.sun.org.apache.xpath.internal.operations.Bool;
import com.sun.xml.internal.ws.util.QNameMap;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

/*

 */
public class Support {
    InNout inOut = new InNout();

    Scanner sc = new Scanner(System.in);
    static ArrayList<Member> members = new ArrayList<Member>();
    static HashMap<String, Member> candiMap = new HashMap<String, Member>();
    static ArrayList<Job> jobList = new ArrayList<Job>();

    String reg_Pwd = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
    String reg_Name = "^[가-힣a-zA-Z]+$";
    String reg_PhoneNumber = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    String reg_BirthDate = "^(19[0-9][0-9]|20[0-9][0-9])-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
    String reg_email = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    String reg_jobCode = "^EZ_[1-9][0-9][0-9][0-9]$";

    //TEST용
//    static {
//        Job j = new Job();
//        j.setDepartment("HR");
//        j.setPostingDate("2021-07-21");
//        j.setJobCode("EZ_1000");
//        j.setJobName("qwe");
//        jobList.add(j);
//        Job j1 = new Job();
//        j1.setDepartment("IT");
//        j1.setPostingDate("2021-07-21");
//        j1.setJobCode("EZ_1001");
//        j1.setJobName("qwe");
//        jobList.add(j1);
//        Job j2 = new Job();
//        j2.setDepartment("FA");
//        j2.setPostingDate("2021-07-21");
//        j2.setJobCode("EZ_1002");
//        j2.setJobName("qwe");
//        jobList.add(j2);
//        Job j3 = new Job();
//        j3.setDepartment("GS");
//        j3.setPostingDate("2021-07-21");
//        j3.setJobCode("EZ_1003");
//        j3.setJobName("qwe");
//        jobList.add(j3);
//        Job j4 = new Job();
//        j4.setDepartment("MK");
//        j4.setPostingDate("2021-07-21");
//        j4.setJobCode("EZ_1004");
//        j4.setJobName("qwe");
//        jobList.add(j4);
//        Job j5 = new Job();
//        j5.setDepartment("HR");
//        j5.setPostingDate("2021-07-21");
//        j5.setJobCode("EZ_1005");
//        j5.setJobName("qwe");
//        jobList.add(j5);
//        Job j6 = new Job();
//        j6.setDepartment("MD");
//        j6.setPostingDate("2021-07-21");
//        j6.setJobCode("EZ_1006");
//        j6.setJobName("qwe");
//        jobList.add(j6);
//
//        Member m = new Member();
//        Member m2 = new Member();
//        m.setPhoneNumber("010-2520-4929");
//        m.setPassWord("1q2w3e4r!@");
//        m.setName("qwe");
//        m.setEmailAddress("qweqweqwe@naver.com");
//        m.setBirthDate("1995-11-03");
//        m.setAppliedJobCode(new String[]{"EZ_1000","EZ_1001","EZ_1002","EZ_1003","EZ_1004"});
//        m2.setPhoneNumber("010-3333-4929");
//        m2.setPassWord("1q2w3e4r!@");
//        m2.setName("qwe2");
//        m2.setEmailAddress("qweqwe@naver.com");
//        m2.setBirthDate("1995-11-03");
//
//        members.add(m);
//        members.add(m2);
//
//        candiMap.put("qweqweqwe@naver.com", members.get(0));
//        candiMap.put("qweqwe@naver.com", members.get(1));
//
//    }

    //=========================================================================
    private int selectTypeNum() {
        System.out.println("**일반 사용자라면 사용자 모드로 접근해주십시오.**");
        System.out.println("====================================");
        System.out.println(" 1. 사용자 모드    2. 관리자 모드    3.프로그램 종료");
        System.out.println("====================================");
        System.out.println();
        int num = Integer.parseInt(getStrInput(""));

        return num;
    }

    public void selectType() {

        while (true) {
            switch (selectTypeNum()) {
                case 1:
                    System.out.println("사용자 모드를 선택하셨습니다.");
                    //유저안의 userSelect 를 띄워야합니다.
                    User user = new User();
                    user.select_First();
                    break;
                case 2:
                    System.out.println("관리자 모드를 선택하셨습니다.");
                    //관리자 모드의 adminCheck로 넘어가야함
                    Admin admin = new Admin();
                    admin.adminCheck();
                    break;
                case 3:
                    System.out.println("프로그램을 종료합니다.");
                    exit();
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }
        }
    }

    //=========================================================================
    public void fileLoadingPrevSelectType() {
        try {
            inOut.folderMake();
            inOut.membersLoad();
            inOut.jobListLoad();
//            inOut.fileLoad("C:\\Temp\\Hiring\\members.txt");
//            inOut.fileLoad("C:\\Temp\\Hiring\\jobList.txt");
            inOut.fileLoad("C:\\Temp\\Hiring\\candiMap.txt");
//            inOut.fileDelete("C:\\Temp\\Hiring\\members.txt");
//            inOut.fileDelete("C:\\Temp\\Hiring\\jobList.txt");
            inOut.fileDelete("C:\\Temp\\Hiring\\candiMap.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
        selectType();
    }

    //=========================================================================
    String getStrInput(String msg) {
//        System.out.print("");
        while (true) {
            try {
                System.out.print(msg);
                String str = sc.nextLine();
                if (str.isEmpty()) {
                    System.out.println("올바른 값을 입력해주세요.");
                    continue;
                }
                return str;
            } catch (Exception e) {
                System.out.println("올바른 형식으로 입력해주세요");
            }
        }
    }

    String getStrInput(String msg, String reg) {    //Overload_정규식 확인 추가
        while (true) {
            System.out.print(msg);
            String str = sc.nextLine();
            if (checkReg(reg, str)) {
                //형식이 맞는 경우
                return str;
            } else {
                System.out.println("**잘못 입력하셨습니다 !**");
            }
        }
    }

    private boolean checkReg(String reg, String data) {
        boolean result = Pattern.matches(reg, data);
        return result;
    }

    //=========================================================================
    Boolean confirmMessage(String subject){    //안내용
        boolean check = false;

        while(true){
            try {
                System.out.printf("%s 을(를) 진행하시겠습니까?\n", subject);
                System.out.println("1. 확인   |   2. 취소");
                int choice = Integer.parseInt(getStrInput(""));

                switch (choice){
                    case 1:
                        System.out.printf("%s 을(를) 완료하였습니다.\n", subject);
                        check = true;
                        break;
                    case 2:
                        System.out.printf("%s 을(를) 진행하지 않습니다.\n", subject);
                        break;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                }
                return check;
            }catch (NumberFormatException e) {
                System.out.println("올바른 값을 입력해주세요.\n");
            }
        }
    }

    
    
    //=========================================================================
    void showJobList(){    //jobList 꺼내서 보여주기

        System.out.println("======Now Hiring======");
//        System.out.println(jobList);

//        for(Job job : jobList){
//            System.out.println(job);
//        }

        for (int i = 0; i < jobList.size(); i++) {
            if (jobList.get(i) == null) {
                System.out.println("현재 채용중인 공고가 없습니다.");
                break;
            } else {
                System.out.println((i+1)+ ". " +jobList.get(i));
            }
        }
        System.out.println("======================");
    }

    //=========================================================================
    void exit(){    //**File저장관련 기능 추가해 줘야합니다 :)
        System.out.println("프로그램을 종료합니다.");
        try {
            inOut.folderMake();
            inOut.jobfileSave();
            inOut.memfileSave();
            inOut.fileSave();
            inOut.filesave2();
            inOut.fileSaveMap();
        
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(0);
        sc.close();
    }
}
