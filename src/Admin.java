import java.util.ArrayList;
import java.util.Map;

/*

 */
public class Admin extends Support {
    private String adminPWD;
    private int num;
    private Job job;

    public Admin() {
        this.num = 1000;
        this.adminPWD = "Ejo";
    }

    public Boolean adminCheck() {   //비밀번호 1개 설정 후, 이용
        while (true) {
            System.out.println("**초기화면으로 돌아가려면 0 (숫자 영)을 입력하세요**");
            System.out.println("관리자 비밀번호를 입력해주세요.");
            String str = getStrInput("        PassWord : "); //관리자 번호를 입력후 return ..

            if (str.equals("0")) {
                System.out.println("초기화면으로 돌아갑니다.\n");
                selectType();

            } else if(!str.equals(adminPWD)){
                System.out.println("비밀번호가 틀립니다.\n");

            }else {
                System.out.println("\n======관리자 모드======");
                selectMenu_A();
                return false;
            }
        }
    }

    private void jobOpening() {
        job = new Job();

        job.setJobCode("EZ_" + (num++)); //jobCode는 자동설정(1000~9999)
        if (num > 9999) {
            System.out.println("더 이상의 공고를 게시할 수 없습니다. 시스템 관리자에게 문의 하세요.");
            selectMenu_A();
        }   //9999 초과하는 공고 게시 불가 -> 메뉴화면으로 돌아감

        job.setDepartment(getStrInput("채용할 부서를 입력하세요. (ex. HR, IT ...)\n"));
        job.setJobName(getStrInput("게시할 공고의 이름을 입력하세요\n"));
        job.setPostingDate(getStrInput("게시 날짜를 입력하세요. (ex. 2021-08-07)\n"));

        Boolean check = confirmMessage("공고 업로드");

        if (check.equals(true)) {
            jobList.add(job);
            System.out.printf("[%s]    채용부서 : %s    공고명 : %s    게시일자 : %s\n",
                    job.getJobCode(), job.getDepartment(), job.getJobName(), job.getPostingDate());
            selectMenu_A();
        } else {
            num--;
            selectMenu_A();
        }
    }

    private void jobChange() {
        int indexOfCorrectJobList = 0;
        boolean check = false;

        try {
            System.out.println("\n수정할 공고 코드를 입력해주세요. (ex. EZ_1000)");
            String inputJobCode = sc.nextLine();
            for (int i = 0; i < jobList.size(); i++) {
                if (jobList.get(i).getJobCode().equals(inputJobCode)) {
                    check = true;
                    indexOfCorrectJobList = i;
                    System.out.println("======수정할 공고=======");
                    System.out.println(jobList.get(i));
                    System.out.println("======================");
                    break;
                }
            }
            if (!check) {
                System.out.println("해당하는 공고 코드가 없습니다.");
                System.out.println("관리자 메뉴로 돌아갑니다.");
            }

            while (true) {
                switch (selectJobList()) {
                    case 1:
                        System.out.println("*******채용부서 수정*******");
                        System.out.printf("현재 채용부서는 %s입니다.\n", jobList.get(indexOfCorrectJobList).getDepartment());    //**가독성에 따라 삭제여부 논의 필요

                        String strDepartment = getStrInput("변경할 채용부서 : ");
                        jobList.get(indexOfCorrectJobList).setDepartment(strDepartment);
                        break;
                    case 2:
                        System.out.println("*******공고명 수정*******");
                        System.out.printf("현재 공고명은 %s입니다.\n", jobList.get(indexOfCorrectJobList).getJobName());

                        String strName = getStrInput("변경할 공고명 : ");
                        jobList.get(indexOfCorrectJobList).setJobName(strName);
                        break;
                    case 3:
                        System.out.println("관리자 메뉴로 돌아갑니다.");
                        selectMenu_A();
                        break;
                    default:
                        System.out.println("잘못된 번호를 입력하셨습니다.");
                }
            }

        } catch (Exception e) {
            System.out.println("에러메시지 : " + e.getMessage());
        }
    }

    private int selectJobList() {

        System.out.println("수정할 항목을 선택 해 주세요.");
        System.out.println("======================");
        System.out.println("1. 채용 부서");
        System.out.println("2. 공고 이름");
        System.out.println("3. 관리자 메뉴로 돌아가기");
        System.out.println("======================");
        int num = Integer.parseInt(sc.nextLine());

        return num;
    }

    private void jobDelete() {
        System.out.println("삭제할 공고코드를 입력해주세요");
        String jobCode = sc.nextLine();
        ArrayList<String> jobCodes = new ArrayList<String>();

        try {
            for (int i = 0; i < jobList.size(); i++) {
                jobCodes.add(jobList.get(i).getJobCode());

                if (jobList.get(i).getJobCode().equals(jobCode)) {
                    job = jobList.get(i);

                    Boolean check = confirmMessage("공고 삭제");

                    if (check.equals(true)) {
                        jobList.remove(job);
                    }
                }
            }
            if (!jobCodes.contains(jobCode)){
                System.out.println("입력하신 코드는 등록되지 않은 공고코드 입니다.");
            }
        }catch (Exception e){
            System.out.println("에러메세지 : "+ e.getMessage());
        }
    }

    private void candiList() {    //공고별 출력
        int count = 1;
        Boolean check = false;

        System.out.println("출력할 지원자리스트의 공고 코드를 입력하세요");
        String findCode = sc.nextLine();

        for(Job j : jobList){    //findCode를 가지고 있는 공고 출력
            if(j.getJobCode().equals(findCode)){
                System.out.println(j);
                System.out.println("************************");
            }
        }

        for (int i = 0; i < candiMap.size(); i++) {    //지원한 사람 출력
            for (Map.Entry<String, Member> entry : candiMap.entrySet()) {
                if (entry.getValue().haveData(findCode)) {
                    System.out.println(count + ". "+ entry.getValue());
                    count++;
                    check = true;
                }
            }
        }

        if (check.equals(false)) {
            System.out.println("해당 공고에 지원한 지원자가 없습니다.");
        }
    }


    private int showMenu_A() {
        System.out.println("======================");
        System.out.println("1. 공고 올리기");
        System.out.println("2. 공고 수정");
        System.out.println("3. 공고 확인");
        System.out.println("4. 공고 삭제");
        System.out.println("5. 지원자 확인");
        System.out.println("6. 초기화면으로 돌아가기");
        System.out.println("7. 프로그램 종료");
        System.out.println("======================");

        int choice = Integer.parseInt(getStrInput(""));
        return choice;
    }

    public void selectMenu_A() {

        while (true) {
            try {
                switch (showMenu_A()) {
                    case 1:                    jobOpening();                    break;
                    case 2:                    jobChange();                    break;
                    case 3:                    showJobList();                    break;
                    case 4:                    jobDelete();                    break;
                    case 5:                    candiList();                    break;
                    case 6:                    selectType();                    break;
                    case 7:                    exit();
                    default:
                        System.out.println("알맞은 메뉴 번호를 입력해주세요.");
                }
            }catch (Exception e){
                System.out.println("잘못 입력하셨습니다.");
            }


        }
    }
}
