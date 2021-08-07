import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

/*

 */
public class User extends Funtions {
    ArrayList<Member> members;
//    ArrayList<jobApplyPaper> jobApplyPapers;

    public User() {
        this.members = new ArrayList<Member>();
//        jobApplyPapers = new ArrayList<>();
    }


   public ArrayList<Member> join(){       //**회원정보 저장여부 논의 필요 >>fileSave()
       Member member = new Member();

       String reg_email = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
       String reg_Pwd = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";
       String reg_Name = "^[가-힣a-zA-Z]+$";
       String reg_PhoneNumber = "^01(?:0|1|[6-9])-(?:\\\\d{3}|\\\\d{4})-\\\\d{4}$";
       String reg_BirthDate = "^(19[0-9][0-9]|20{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";


       System.out.println("======회원정보입력======");
       String name = getStrInput("            Name :", reg_Name);
       String email = emailCrossCheck(reg_email);
       String pwd = pwdCrossCheck(reg_Pwd);
       String phoneNumber = getStrInput("phonenumber :", reg_PhoneNumber);
       String birthdate = getStrInput("    birthdate : ", reg_BirthDate);
       //정규표현식 확인필요 - 핸드폰번호, 이메일주소, 생년월일, 비밀번호
       //비밀번호 더블체크 기능 add  Please :)

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
            String pw = getStrInput("         PassWord : ", reg);
            String pw2 = getStrInput("Password Confirm : ", reg);
            if (pw.equals(pw2)) {
                System.out.println("패스워드가 일치합니다!");
                return pw;
            } else {
                System.out.println("패스워드가 일치하지 않습니다.!");
            }
        }
    }

    private String emailCrossCheck(String reg) {
        while (true) {
            String email = getStrInput("              Email : ", reg);
            if (emailCheck(email)) {
                System.out.println("중복된 Email입니다.");
            }
            return email;
        }
    }

    String getStrInput(String msg, String reg) {
        while (true) {
            System.out.println(msg);
            String str = sc.nextLine();
            if (checkReg(reg, str)) {
                //형식이 맞음
                return str;
            } else {
                System.out.println("형식이 틀립니다 !");
                System.out.println("이메일  예시: bitcamp@naver.com");
                System.out.println("비밀번호 : 특수문자 / 문자 / 숫자 포함 형태의 8~15자리 이내");
                System.out.println("핸드폰번호 예시 : 010-2520-4929");
                System.out.println("생년월일 예시 : 19951103");
            }
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



    public void login(){
        String email = getStrInput("   Email : ");
        String pw = getStrInput("PassWord : ");

        Member member = findById(email, members);
        if (member == null) {
            System.out.println("등록되지 않은 이메일 입니다.");
        } else if (member.getPassWord().equals(pw)) {
            System.out.println("[" + member.getName() + "]님께서 로그인 하셨습니다.");
        } else {
            System.out.println("비밀번호가 틀렸습니다.");
        }
    }

    Member findById(String email, ArrayList<Member> members) {
        for (Member member : members) {
            if (member.getEmailAddress().equals(email)) {
                return member;
            }
        }
        return null;
    }

    private void jobApply(){
        Member candiMember = new Member();
        String jobCode, candiName, candiEmail;

        System.out.println("지원할 공고의 코드를 입력하세요.");
        jobCode = sc.nextLine();
        System.out.println("이름을 입력하세요.");
        candiName = sc.nextLine();
        System.out.println("이메일 주소를 입력하세요.");
        candiEmail = sc.nextLine();

        for (Member member : members) {
            if (member.haveData(candiEmail)) {
                candiMember = member;   //members리스트에 담겨있는 member 불러오기
                member.appliedJCodes(jobCode);
            }
        }
//        for(int i = 0; i < members.size(); i++){
//            if(candiMember.haveData(candiEmail)){
//                candiMember = members.get(i);   //members리스트에 담겨있는 member 불러오기
//                members.get(i).memberJobCodes(jobCode);
//            }
//        }

        Boolean check = confirmMessage("지원");

        if(check.equals(true)){
            candiMap.put(candiEmail, candiMember);    //지원자 정보 HashMap저장
        }else{
            for (Member member : members) {
                if (member.haveData(candiEmail)) {
                    member.deleteJCodes(jobCode);    //member에 저장된 appliedJobCode 지우기
                }
            }
            selectMenu_U();
        }
    }

    private void applyCheck(){    //candiMap에 저장된 member 불러오기

       System.out.println("이메일 주소를 입력하세요.");
        String findEmail = sc.nextLine();

        System.out.println("======지원 목록======");

        for(Map.Entry<String, Member> entry : candiMap.entrySet()){
            if(entry.getValue().haveData(findEmail)){
                String[] jobCodes = entry.getValue().getAppliedJobCode();

                for(Job job : jobList){
                    for (String jobCode : jobCodes) {
                        if (jobList.contains(jobCode)) {
                            System.out.println(job);
                        }
                    }
                }
            }
        }

//        System.out.println("이메일 주소를 입력하세요.");
//        String email = sc.nextLine();
//
//        System.out.println("====지원한 지원서 목록====");
//        for (int i = 0; i < jobApplyPapers.size(); i++) {
//            String str = jobApplyPapers.get(i).getCandiEmail();
//            if (str.equals(email)) {
//                //여기서 지원자리스트에서의 이메일과 입력한 이메일이 동일하다면 출력합니다.
//                //그대신 지원서 리스트를 출력해야합니다.
//                System.out.println(jobApplyPapers.get(i));
//            }
//        }
    }

    private void applyCancel(){     //jobApply()와 매우 흡사 - 반대되는 결과만 도출
        Member candiMember = new Member();
        String jobCode, candiName, candiEmail;

        System.out.println("지원한 공고의 코드를 입력하세요.");
        jobCode = sc.nextLine();
        System.out.println("이름을 입력하세요.");
        candiName = sc.nextLine();
        System.out.println("이메일 주소를 입력하세요.");
        candiEmail = sc.nextLine();

        for (Member member : members) {
            if (member.haveData(candiEmail)) {
                candiMember = member;   //members리스트에 담겨있는 member 불러오기
                member.deleteJCodes(jobCode);
            }
        }

        Boolean check = confirmMessage("지원취소");

        if(check.equals(true)){
            candiMap.remove(jobCode, candiMember);    //지원자 정보 HashMap에서 삭제
        }else{
            for (Member member : members) {
                if (member.haveData(candiEmail)) {
                    member.appliedJCodes(jobCode);
                }
            }
            selectMenu_U();
        }
    }


    public void select_First() {
        while (true) {
            switch (select_First_Num()) {
                case 1: //회원가입
                    join();
                    break;
                case 2: //로그인:
                    login();
                    break;
                case 3: //초기화면으로:
                    selectType();
                    break;
                default:
                    System.out.println("잘못된 번호를 입력하셨습니다.");
            }
        }
    }

    private int select_First_Num() {
        System.out.println("==================");
        System.out.println("        1. 회원가입");
        System.out.println("        2. 로그인");
        System.out.println("        3. 초기화면으로");
        System.out.println("==================");
        int num = Integer.parseInt(sc.nextLine());
        return num;
    }

    private void showMenu_U(){
        System.out.println("======================");
        System.out.println("1. 채용중인 공고 확인");
        System.out.println("2. 지원하기");
        System.out.println("3. 지원여부 확인");
        System.out.println("4. 지원취소");
        System.out.println("5. 프로그램 종료");
        System.out.println("======================");
    }

    public void selectMenu_U(){     //default에서 오류발생하면 -> if문 시도
       showMenu_U();

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice){
            case 1 : showJobList(); break;
            case 2: jobApply(); break;
            case 3 : applyCheck(); break;
            case 4 : applyCancel(); break;
            case 5: exit();
            default:
                System.out.println("잘못 입력 하셨습니다.");
                selectMenu_U();
        }
    }
}
