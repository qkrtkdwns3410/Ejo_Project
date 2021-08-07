import java.util.ArrayList;
import java.util.Map;

/*

 */
public class User extends Funtions {
    ArrayList<Member> members = new ArrayList<Member>();

   public ArrayList<Member> join(){       //**회원정보 저장여부 논의 필요 >>fileSave()
        Member member = new Member();

        System.out.println("이름을 입력하세요.");
        member.setName(sc.nextLine());
        System.out.println("이메일 주소를 입력하세요.");
        member.setEmailAddress(sc.nextLine());
        System.out.println("핸드폰 번호를 입력하세요.");
        member.setPhoneNumber(sc.nextLine());
        System.out.println("생년월일을 입력하세요.");
        member.setBirthDate(sc.nextLine());
       System.out.println("비밀번호를 입력하세요");
       member.setPassWord(sc.nextLine());
       //정규표현식 확인필요 - 핸드폰번호, 이메일주소, 생년월일, 비밀번호
       //비밀번호 더블체크 기능 add  Please :)

        members.add(member);
        return members;
    }

    public void login(){

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
