import java.util.Map;

/*

 */
public class Admin extends Funtions {
    private int num = 1000;

    public void adminCheck() {   //비밀번호 1개 설정 후, 이용

    }

    private void jobOpening() {
        Job job = new Job();

        job.setJobCode("EZ_" + (num++)); //jobCode는 자동설정(1000~9999)
        if(num > 9999){
            System.out.println("더 이상의 공고를 게시할 수 없습니다. 시스템 관리자에게 문의 하세요.");
            selectMenu_A();
        }   //9999 초과하는 공고 게시 불가 -> 메뉴화면으로 돌아감

        System.out.println("채용할 부서를 입력하세요.");
        job.setDepartment(sc.nextLine());
        System.out.println("게시할 공고의 이름을 입력하세요");
        job.setJobName(sc.nextLine());
        System.out.println("게시 날짜를 입력하세요. (ex. 2021-08-07)");
        job.setPostingDate(sc.nextLine());

        Boolean check = confirmMessage("공고 업로드");

        if(check.equals(true)){
            jobList.add(job);
            System.out.printf("[%s]    채용부서 : %s.    %s.    [%s]\n",
                    job.getJobCode(), job.getDepartment(), job.getJobName(), job.getPostingDate());
        }else {
            selectMenu_A();
        }
    }

    private void jobChange() {    //jobCode, postingDate는 고정

    }

    private void jobDelete() {

    }

    private void candiList() {    //공고별 출력
        int count = 1;

        System.out.println("출력할 지원자리스트의 공고 코드를 입력하세요");
        String findCode = sc.nextLine();

        for(int i = 0; i < candiMap.size(); i++){
            for(Map.Entry<String, Member> entry : candiMap.entrySet()){
                if(entry.getKey().equals(findCode)){
                    System.out.println(count + ". [" + entry.getKey() + "]    " + entry.getValue());
                    count++;
                }
            }
        }
    }


    private void showMenu_A() {
        System.out.println("======================");
        System.out.println("1. 공고 올리기");
        System.out.println("2. 공고 수정");
        System.out.println("3. 공고 확인");
        System.out.println("4. 공고 삭제");
        System.out.println("5. 지원자 확인");
        System.out.println("6. 프로그램 종료");
        System.out.println("======================");
    }

    public void selectMenu_A() {
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1:
                jobOpening();
                break;
            case 2:
                jobChange();
                break;
            case 3:
                showJobList();
                break;
            case 4:
                jobDelete();
                break;
            case 5:
                candiList();
                break;
            case 6:
                exit();
            default:
                System.out.println("잘못 입력 하셨습니다.");
                selectMenu_A();
        }
    }

}
