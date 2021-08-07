import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/*

 */
public class Funtions {
    Scanner sc = new Scanner(System.in);
    HashMap<String, Member> candiMap;
    ArrayList<Job> jobList;

    public Funtions(){
        candiMap = new HashMap<String, Member>();
        jobList = new ArrayList<Job>();
    }

    String getStrInput(String msg) {
        System.out.println(msg);
        return sc.nextLine();
    }

    int selectTypeNum() {
        System.out.println("일반 사용자라면 사용자 모드로 접근해주십시오.");
        System.out.println();
        System.out.println("=======================");
        System.out.println(" 1. 사용자 모드    2. 관리자 모드");
        System.out.println("=======================");
        System.out.println();
        int num = Integer.parseInt(sc.nextLine());
        return num;
    }

    void selectType() {

        while (true) {
            switch (selectTypeNum()) {
                case 1:
                    System.out.println("사용자 모드를 선택하셨습니다.");
                    //유저안의 userSelect 를 띄워야합니다.
                    User user = new User();
                    user.selectMenu_U();
                    break;
                case 2:
                    System.out.println("관리자 모드를 선택하셨습니다.");
                    //관리자 모드의 adminCheck로 넘어가야함
                    Admin admin = new Admin();
                    admin.adminCheck();
                    break;
                default:
                    System.out.println("올바른 번호를 입력해주세요.");
            }
        }

    }

    Boolean confirmMessage(String subject){    //안내용
        Boolean check = false;

        System.out.printf("%s 을(를) 진행하시겠습니까?\n", subject);
        System.out.println("1. 확인   |   2. 취소");
        int choice = Integer.parseInt(sc.nextLine());

        switch (choice){
            case 1:
                System.out.printf("%s 을(를) 완료하였습니다.\n", subject);
                check = true;
                break;
            case 2:
                System.out.printf("%s 을(를) 취소합니다.\n", subject);
                break;
            default:
                System.out.println("잘못 입력하셨습니다.");
                confirmMessage(subject);
        }
        return check;
    }

    void fileSave(String  filename){

    }

    Boolean fileCheck(){
        return null;
    }

    void fileDelete(String filename) {

    }

    void fileLoad(){

    }

    void showJobList(){    //jobList 꺼내서 보여주기
        int count = 1;

        System.out.println("======Now Hiring======");
        for(Job j : jobList){
            System.out.println(count + ". " + j);
            count++;
        }
        System.out.println("======================");
    }

    void exit(){
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);
        sc.close();
    }
}
