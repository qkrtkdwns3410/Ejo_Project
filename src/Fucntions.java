import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/*

 */
public class Fucntions {
    Scanner sc = new Scanner(System.in);
    HashMap<String, Member> candiMap = new HashMap<String, Member>();
    ArrayList<Job> jobList = new ArrayList<Job>();

//    User user = new User();
//    Admin admin = new Admin();
//    Job job = new Job();

    String path = "C:\\Temp\\Hiring";    //시스템에 따라서 경로변경필요
    String[] txt_data;
    String cmp_data;

    FileReader fr = null;
    BufferedReader br = null;

    FileWriter fw = null;
    BufferedWriter bw = null;


    String getStrInput(String msg) {
        System.out.println(msg);
        return sc.nextLine();
    }

    private void selectTypeNum() {
        System.out.println("일반 사용자라면 사용자 모드로 접근해주십시오.");
        System.out.println();
        System.out.println("=======================");
        System.out.println(" 1. 사용자 모드    2. 관리자 모드");
        System.out.println("=======================");
        System.out.println();
    }

    void selectType() {
        selectTypeNum();
        int num = Integer.parseInt(sc.nextLine());

        while (true) {
            switch (num) {
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

    void fileSave(String str) throws IOException { //이메일-지원자정보 저장, 공고코드-공고명
        File applyemailorjob = new File(path + str + ".txt");   //Admin 공고코드명들고오기
        String[] txt_data2;
        String cmp_data2;

        if(!applyemailorjob.exists()){
            applyemailorjob.mkdir();
        }
        else {
            fw = new FileWriter(applyemailorjob);
            bw = new BufferedWriter(fw);

            fr= new FileReader(applyemailorjob);  //파일 읽을 준비
            br= new BufferedReader(fr);
            cmp_data2 = br.readLine(); //읽어온 파일에서 한줄을 cmp_data2에 저장한다.
            txt_data2 = cmp_data2.split(":"); // ':' 로 쪼개 txt_data배열에 저장
            //System.out.println(txt_data.length); //txt_data배열 크기 보기

            br.close(); //닫기
        }
    }

    Boolean fileCheck(String filename) throws IOException {
        boolean check = false;

        File file = new File(path + filename + ".txt");

        if (file.exists()) {     //이메일이 존재할 경우
            check = true;
            System.out.println("파일이 존재합니다.");
        } else {
            System.out.println("파일이 존재하지 않습니다.");
        }
        return check;
    }

    void fileDelete(String filename) throws IOException {        //.txt파일 지우는것
        String filepath;
        filepath = (this.path + filename + ".txt");
        String cmp_data3;
        String[] txt_data3;
        String line;

        File deletefile = new File(filepath);

        //줄단위로 읽어서 filename과 같으면 그 줄 지운다.

        fr = new FileReader(deletefile);
        br = new BufferedReader(fr);
        cmp_data3 = br.readLine();

        while(cmp_data3 != null) {
            if (cmp_data3.equals(filename)) {   // path의 폴더안에 있는 파일중 str을 포함하면 지운다.
                cmp_data3 = "";
                System.out.println("파일을 삭제하였습니다.");
                break;
            } else {
                System.out.println("파일이 존재하지 않습니다.");
                break;
            }
        }
    }

    String[] fileLoad(String name) throws Exception {
        //공고정보를 업로드, 읽어서 출력한다.,, jobOpening에서 올린 공고 ,, .txt파일에서 불러온다.
        //candimap, joblist, member까지 모두 upload

        File loadFile = new File(path + name + ".txt"); //근데 파일이름은 공고명으로 되어있는데...? >>jobOpening.txt / user.txt / applier.txt로 나눠짐.

        fr = new FileReader(loadFile);
        br = new BufferedReader(fr);
        String[] arr = br.readLine().split(":");

        return arr;
    }

    void showJobList(){    //jobList 꺼내서 보여주기

        System.out.println("======Now Hiring======");
        System.out.println(jobList);

//        for(Job job : jobList){
//            System.out.println(job);
//        }

//        for (int i = 0; i < jobList.size(); i++) {
//            if (jobList.get(i) == null) {
//                break;
//            } else {
//                System.out.println((i+1)+ ". " +jobList.get(i));
//            }
//        }
        System.out.println("======================");

    }

    void exit(){
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);
        sc.close();
    }
}
