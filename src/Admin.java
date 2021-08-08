import java.util.Map;

/*

 */
public class Admin extends Fucntions {
    private String adminPWD;
    private int num;

    public Admin() {
        this.num = 1000;
        this.adminPWD = "Ejo";
    }

    public Boolean adminCheck() {   //비밀번호 1개 설정 후, 이용
        while (true) {
            System.out.println("관리자 비밀번호를 입력해주세요 .");
            String str = getStrInput("        PassWord : "); //관리자 번호를 입력후 return ..
            if (!str.equals(adminPWD)) {
                System.out.println("비밀번호가 틀립니다.");
                System.out.println("초기화면으로 돌아가시겠습니까?");    //**초기 선택지로 돌아가는 추가함수필요
                int sw2 = sc.nextInt();
                if (sw2 == 0) {selectType();}
            } else {
                System.out.println("======관리자 모드======");
                selectMenu_A();
                return false;
            }
        }
    }

    private void jobOpening() {
        Job job = new Job();

        job.setJobCode("EZ_" + (num++)); //jobCode는 자동설정(1000~9999)
        if (num > 9999) {
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

        if (check.equals(true)) {
            jobList.add(job);
            System.out.printf("[%s]    채용부서 : %s.    %s.    [%s]\n",
                    job.getJobCode(), job.getDepartment(), job.getJobName(), job.getPostingDate());
            selectMenu_A();
        } else {
            selectMenu_A();
        }
    }

    private void jobChange() {

        try {
            System.out.println("수정할 글 번호를 입력해주세요.");
            int num = Integer.parseInt(sc.nextLine());
            System.out.println("======수정할 글=======");
            System.out.println(jobList.get(num + 1));
            System.out.println("====================");

        } catch (Exception e) {
            System.out.println("에러메시지 : " + e.getMessage());
        }

        while (true) {
            switch (selectJobList()) {
                case 1:
                    System.out.println("department를 수정합니다.");
                    System.out.printf("현재 department는 %s입니다.", jobList.get(num + 1).getDepartment());

                    String strDepartment = getStrInput("수정할 department를 입력해주세요.");
                    jobList.get(num + 1).setDepartment(strDepartment);
                case 2:
                    System.out.println("jobName을 수정합니다.");
                    System.out.printf("현재 jobName은 %s입니다.", jobList.get(num + 1).getJobName());

                    String strName = getStrInput("수정할 jobName을 입력해주세요.");
                    jobList.get(num + 1).setJobName(strName);
                case 3:
                    System.out.println("관리자 메뉴로 돌아갑니다.");
                    selectMenu_A();
                default:
                    System.out.println("잘못된 번호를 입력하셨습니다.");

            }
        }
    }

    private int selectJobList() {

        System.out.println("수정할 목록을 입력해주세요.");
        System.out.println("======================");
        System.out.println("1. 채용 부서");
        System.out.println("2. 공고 이름");
        System.out.println("3. 관리자 메뉴로 돌아가기");
        System.out.println("======================");
        int num = Integer.parseInt(sc.nextLine());

        return num;
    }

    private void jobDelete() {

    }

    private void candiList() {    //공고별 출력
        int count = 1;

        System.out.println("출력할 지원자리스트의 공고 코드를 입력하세요");
        String findCode = sc.nextLine();

        for (int i = 0; i < candiMap.size(); i++) {
            for (Map.Entry<String, Member> entry : candiMap.entrySet()) {
                if (entry.getKey().equals(findCode)) {
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
        showMenu_A();
        int choice = Integer.parseInt(sc.nextLine());

        while (true) {
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

}
