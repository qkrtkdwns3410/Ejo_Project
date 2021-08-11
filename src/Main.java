import sun.security.pkcs11.wrapper.Functions;

/*

 */
public class Main {
    public static void main(String[] args) {
        Support spt = new Support();


//        try {
//            spt.fileSave();
//            spt.filesave2();
//        }catch (Exception e){
//            System.out.println("에러메세지 : "+e.getMessage());
//        }
        try {
            spt.fileLoadingPrevSelectType();
        
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

























