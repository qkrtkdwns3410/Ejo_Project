import java.io.*;
import java.util.Scanner;

/*
 
 */
public class InNout {
    String path = "C:\\Temp\\Hiring\\";    //시스템에 따라서 경로변경필요

    FileReader fr = null;
    BufferedReader br = null;

    BufferedWriter bw = null;
    FileWriter fw = null;
    
    void fileSave() throws IOException { //이메일-지원자정보 저장, 공고코드-공고명
        File txtfile = new File(path + "jobList.txt");
        
        try {
            for (Job j : Support.jobList) {
                String[] arr2 = j.getAllJob().split(" ");
                
                BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile, true));
                for (String str : arr2) {
                    bw.write(str + " ");
                    //객체 닫기
                }
                bw.write("\n");
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
    void fileSaveMap() {
        try {
            File file = new File(path + "candiMap.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            
            if (file.isFile()) {
                for (String key : Support.candiMap.keySet()) {
                    bw.write(key + " " + Support.candiMap.get(key).toString() + "\n");
                }
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    void filesave2() throws IOException {
        File txtfile = new File(path + "members.txt");
        
        try {
            for (Member m : Support.members) {
                String[] arr2 = m.toString().split(" ");
                
                BufferedWriter bw = new BufferedWriter(new FileWriter(txtfile, true));
                for (String str : arr2) {
                    bw.write(str + " ");
                    
                    //객체 닫기
                }
                bw.write("\n");
                bw.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void fileDelete(String path) throws IOException {        //.txt파일 지우는것
//        String filepath;
//        filepath = (this.path + filename + ".txt");
//        String cmp_data3;
//        String[] txt_data3;
//        String line;
        
        File deletefile = new File(path);
        
        if (deletefile.exists()) { //파일이고 그 파일이 존재하는지 물음
//            System.gc();
//            System.runFinalization();
            //주의해야할 코드
            System.out.println(path + "의 파일을 삭제하겠습니다.");
            
            deletefile.delete();
            
        }
        //존재한다면 지워야함 filename과 동일한 친구 지우면됨.
    }
    void fileLoad(String filePath) throws Exception {
        try {
            File f = new File(filePath);
            if (f.exists() && f.isFile()) {
                System.out.println(filePath + "의 파일을 불러오겠습니다.");
            } else {
                System.out.println("파일이 존재하지 않습니다.");
                return;
            }
            br = new BufferedReader(new FileReader(filePath));
            
            while (true) {
                String text = br.readLine();            //텍스트 파일 내 데이터를 한 줄 씪 읽음
                
                //무한 반복 현상을 막기 위해 텍스트 내 데이터가 없으면 끝내도록
                
                if (text == null) {
                    break;
                }
                
                String[] strArray = text.split(" ");
                //comma를 기준으로 자르기: 잘라야 원하는 데이터 추출이 가능하니까
//                System.out.println("strArray 값내 첫번째 값 확인: "+strArray[0]);
//                System.out.println("strArray 값내 두번째 값 확인: "+strArray[1]);
//                System.out.println("strArray 값내 세번째 값 확인: "+strArray[2]);    //데이터가 없으므로 ArrayIndexOutOfBoundsException 발생
                
                //split으로 자른 데이터 arraylist에 담기(이 작업에서 헤맸다! 반드시 재확인)
                //1. phonelist를 그냥 쓸 수 있는 이유 필드에서 static으로 지정
                //2. arraylist 형식의 phonelist 변수에 넣기 위해 add 함수 사용
                //3. add 함수 내에 PhoneItem 인스턴스를 생성해야 가져다 사용할 수 있다는 점도 포인트다
                if (filePath.contains("members")) {
                    String[] jobCodeArr = {strArray[5], strArray[6], strArray[7], strArray[8], strArray[9]};
                    
                    Member m = new Member();
                    m.setName(strArray[0]);
                    m.setEmailAddress(strArray[1]);
                    m.setPassWord(strArray[2]);
                    m.setPhoneNumber(strArray[3]);
                    m.setBirthDate(strArray[4]);
                    m.setAppliedJobCode(jobCodeArr);
                    Support.members.add(m);
                    
                } else if (filePath.contains("jobList")) {
                    Job j = new Job();
                    j.setJobCode(strArray[0]);
                    j.setDepartment(strArray[1]);
                    j.setJobName(strArray[2]);
                    j.setPostingDate(strArray[3]);
                    Support.jobList.add(j);
                } else if (filePath.contains("candiMap")) {
                    String[] jobCodeArr = {strArray[6], strArray[7], strArray[8], strArray[9], strArray[10]};
                    
                    Member m = new Member();
                    m.setName(strArray[1]);
                    m.setEmailAddress(strArray[2]);
                    m.setPassWord(strArray[3]);
                    m.setPhoneNumber(strArray[4]);
                    m.setBirthDate(strArray[5]);
                    m.setAppliedJobCode(jobCodeArr);
                    Support.candiMap.put(strArray[0], m);
                } else {
                    System.out.println("올바른 경로가 아닙니다.");
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
