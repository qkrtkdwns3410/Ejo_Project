import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

/*

 */
public class User extends Support {
    ArrayList<Member> members = new ArrayList<Member>();

    String reg_email = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
    String reg_Pwd = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
    String reg_Name = "^[가-힣a-zA-Z]+$";
    String reg_PhoneNumber = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$";
    String reg_BirthDate = "^(19[0-9][0-9]|20[0-9][0-9])-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
    String reg_jobCode = "^EZ_[1-9][0-9][0-9][0-9]$";


   public ArrayList<Member> join(){       //**회원정보 저장여부 논의 필요 >>fileSave()
       Member member = new Member();


       System.out.println("======회원정보입력======");
       String name = getStrInput("이름 :", reg_Name);
       String email = emailCrossCheck(reg_email);
       String pwd = pwdCrossCheck(reg_Pwd);
       String phoneNumber = getStrInput("휴대전화번호(ex. 010-1234-5678) :", reg_PhoneNumber);
       String birthdate = getStrInput("생년월일(ex. 1999-08-15) : ", reg_BirthDate);

       System.out.println("\n[ "+name + " ] 님, 회원가입을 축하드립니다!");

       setMember(member, name, email, pwd, phoneNumber, birthdate);
        members.add(member);
        return members;
    }

    private void setMember(Member member, String name, String email, String pwd, String phoneNumber, String birthdate) {
        member.setName(name);
        member.setPassWord(pwd);
        member.setEmailAddress(email);
        member.setPhoneNumber(phoneNumber);
        member.setBirthDate(birthdate);
    }

    private String pwdCrossCheck(String reg) {
        while (true) {
            String pw = getStrInput("비밀번호 입력(특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내) : ", reg);
            String pw2 = getStrInput("비밀번호 확인 : ", reg);
            if (pw.equals(pw2)) {
                System.out.println("비밀번호가 일치합니다!");
                return pw;
            } else {
                System.out.println("비밀번호가 일치하지 않습니다!");
            }
        }
    }

    private String emailCrossCheck(String reg) {
        while (true) {
            String email = getStrInput("이메일 주소(ex. bitcamp@naver.com) : ", reg);
            if (emailCheck(email)) {
                System.out.println("이미 가입된 이메일 주소 입니다.");
            }
            return email;
        }
    }

    private boolean emailCheck(String email) {
        boolean check = true;
        Member member = findById(email, members);
        if (member == null) {
            check = false;
        }
        return check;
    }

    private boolean checkReg(String reg, String data) {
        boolean result = Pattern.matches(reg, data);
        return result;
    }

    String getStrInput(String msg, String reg) {    //Support에 있는 함수 Overload
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


    public void login(){
        String email = getStrInput("   Email : ", reg_email);
        String pw = getStrInput("PassWord : ");

        Member member = findById(email, members);
        if (member == null) {
            System.out.println("등록되지 않은 이메일 입니다.");
        } else if (member.haveData(pw)) {
            System.out.println("[" + member.getName() + "]님께서 로그인 하셨습니다.");
            selectMenu_U();
        } else {
            System.out.println("비밀번호가 틀렸습니다.");
        }
    }

    Member findById(String email, ArrayList<Member> members) {
        for (Member member : members) {
            if (member.haveData(email)) {
                return member;
            }
        }
        return null;
    }

    private void jobApply(){
        Member candiMember = new Member();
        String jobCode, candiEmail;
        Boolean check = false;


        try {
            jobCode = getStrInput("지원할 공고의 코드를 입력하세요. (ex. EZ_1000)\n", reg_jobCode);

            //JobList에 있는지 여부 확인
            for (Job j : jobList) {
                if (j.getJobCode().equals(jobCode)) {
                    System.out.println("=======지원할 공고=======");
                    System.out.println(j);
                    System.out.println("========================");
                    check = true;
                }
            }
            if (check.equals(false)) {
                System.out.println("입력하신 공고코드는 현재 채용중인 공고가 아닙니다.");
                jobApply();
            }

            while(true) {
                candiEmail = getStrInput("이메일 주소를 입력하세요.\n", reg_email);

                check = false;
                for (Member member : members) {
                    if (member.haveData(candiEmail)) {
                        candiMember = member;   //members리스트에 담겨있는 member 불러오기
                        candiMember.appliedJCodes(jobCode);    //member에 jobCode 추가
                        check = true;
                    }
                }
                if(check.equals(false)) {
                    System.out.println("입력하신 이메일 주소로 가입된 회원 정보가 없습니다.");
                }else{
                    break;
                }
            }

            check = confirmMessage("지원");

            if (check.equals(true)) {
                candiMap.put(candiEmail, candiMember);    //지원자 정보 HashMap저장
            } else {
                for (Member member : members) {
                    if (member.haveData(candiEmail)) {
                        candiMember.deleteJCodes(jobCode);    //member에 저장된 appliedJobCode 지우기
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void applyCheck() {    //candiMap에 저장된 member 불러오기

        String findEmail = getStrInput("이메일 주소를 입력하세요.\n", reg_email);

        Boolean check = false;
        for(Member m : members){
            if(m.haveData(findEmail)){
                check = true;
            }
        }
        if(!check){
            System.out.println("입력하신 이메일 주소로 가입된 회원 정보가 없습니다.");
            applyCheck();
        }

        System.out.println("======지원 목록======");

        for (Map.Entry<String, Member> entry : candiMap.entrySet()) {
            if (entry.getValue().haveData(findEmail)) {
                String[] jobCodes = entry.getValue().getAppliedJobCode();

                for (Job job : jobList) {
                    for (String jobCode : jobCodes) {
                        if (jobList.contains(jobCode)) {
                            System.out.println(job);
                        }
                    }
                }
            }
        }
    }

    private void applyCancel(){     //jobApply()와 매우 흡사 - 반대되는 결과만 도출
        Member candiMember = new Member();
        String jobCode, candiEmail;
        Boolean check = false;

        try {
            jobCode = getStrInput("지원한 공고의 코드를 입력하세요. (ex. EZ_1000)\n", reg_jobCode);

            //JobList에 있는지 여부 확인
            for (Job j : jobList) {
                if (j.getJobCode().equals(jobCode)) {
                    System.out.println("=======지원한 공고=======");
                    System.out.println(j);
                    System.out.println("========================");
                    check = true;
                }
            }
            if (check.equals(false)) {
                System.out.println("입력하신 공고코드는 현재 채용중인 공고가 아닙니다.");
                applyCancel();
            }

            while(true) {
                candiEmail = getStrInput("이메일 주소를 입력하세요.\n", reg_email);

                check = false;
                for (Member member : members) {
                    if (member.haveData(candiEmail)) {
                        candiMember = member;   //members리스트에 담겨있는 member 불러오기
                        member.deleteJCodes(jobCode);    //member에 jobCode 추가
                        check = true;
                    }
                }
                if(check.equals(false)) {
                    System.out.println("입력하신 이메일 주소로 가입된 회원 정보가 없습니다.");
                }else{
                    break;
                }
            }

            check = confirmMessage("지원 취소");

            if (check.equals(true)) {
                candiMap.remove(candiEmail, candiMember);    //지원자 정보 HashMap에서 삭제
            } else {
                for (Member member : members) {
                    if (member.haveData(candiEmail)) {
                        member.appliedJCodes(jobCode);    //member에 저장된 appliedJobCode 지우기
                    }
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }



//        jobCode = getStrInput("지원한 공고의 코드를 입력하세요.\n");
//        candiEmail = getStrInput("이메일 주소를 입력하세요.\n");

//        for (Member member : members) {
//            if (member.haveData(candiEmail)) {
//                candiMember = member;   //members리스트에 담겨있는 member 불러오기
//                member.deleteJCodes(jobCode);
//            }
//        }
//
//        Boolean check = confirmMessage("지원취소");
//
//        if(check.equals(true)){
//            candiMap.remove(candiMember.getEmailAddress(), candiMember);    //지원자 정보 HashMap에서 삭제
//        }else{
//            for (Member member : members) {
//                if (member.haveData(candiEmail)) {
//                    member.appliedJCodes(jobCode);
//                }
//            }
//        }
    }


    private int select_First_Num() {
        System.out.println("==================");
        System.out.println("1. 회원가입");
        System.out.println("2. 로그인");
        System.out.println("3. 초기화면으로 돌아가기");
        System.out.println("==================");
        int num = Integer.parseInt(sc.nextLine());
        return num;
    }

    public void select_First() {
        while (true) {
            switch (select_First_Num()) {
                case 1:                    join();                    break;
                case 2:                    login();                    break;
                case 3:                    selectType();                    break;
                default:
                    System.out.println("잘못된 번호를 입력하셨습니다.");
            }
        }
    }

    private int showMenu_U(){
        System.out.println("======================");
        System.out.println("1. 채용중인 공고 확인");
        System.out.println("2. 지원하기");
        System.out.println("3. 지원여부 확인");
        System.out.println("4. 지원취소");
        System.out.println("5. 초기화면으로 돌아가기");
        System.out.println("6. 프로그램 종료");
        System.out.println("======================");

        int choice = Integer.parseInt(sc.nextLine());
        return choice;
    }

    public void selectMenu_U(){

        while (true) {
            switch (showMenu_U()) {
                case 1:                    showJobList();                    break;
                case 2:                    jobApply();                    break;
                case 3:                    applyCheck();                    break;
                case 4:                    applyCancel();                    break;
                case 5:                     selectType();                   break;
                case 6:                    exit();
                default:
                    System.out.println("잘못 입력 하셨습니다.");
            }
        }
    }
}
