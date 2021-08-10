/*

 */
class Member {
    private String name;
    private String emailAddress;
    private String phoneNumber;
    private String birthDate;
    private String passWord;
    private String[] appliedJobCode = new String[5];

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() { return emailAddress; }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public void setPassWord(String passWord) { this.passWord = passWord; }

    public String[] getAppliedJobCode() { return appliedJobCode; }

    public void setAppliedJobCode(String[] appliedJobCode) { this.appliedJobCode = appliedJobCode; }


    Boolean addAppliedJCodes(String jobCode){
        boolean fullCheck = true;

        for(String s : appliedJobCode){
            if(s == null){
                fullCheck = false;
            }
        }
        if(!fullCheck){
            for(int i= 0; i < appliedJobCode.length; i++){
                if(appliedJobCode[i] == null){
                    appliedJobCode[i] = jobCode;
                    break;
                }
            }
        }else {
            System.out.println("이미 5개의 공고에 지원하셨습니다.");
        }
        return fullCheck;
    }

    void deleteJCodes(String jobCode){
        for(int i = 0; i < appliedJobCode.length; i++){
            if(appliedJobCode[i].equals(jobCode)){
                appliedJobCode[i] = null;
                break;
            }
        }
    }

    Boolean haveData(String data) {
        Boolean check = false;

        if (this.name.equals(data) || this.emailAddress.equals(data) || this.phoneNumber.equals(data)
                || this.birthDate.equals(data) || this.passWord.equals(data)) {
            check = true;
        }
        return check;
    }

    @Override
    public String toString() {
        return "이름 : '" + name + ", 이메일주소 : " + emailAddress + ", 전화번호 : " + phoneNumber +", 생년월일 : " + birthDate;
    }

    //File 생성시 사용되는 함수
    public String appliedJobCodePrint() {
        String strArr = "";
        for (int i = 0; i < appliedJobCode.length; i++) {
            if (appliedJobCode[i] == null) {
                strArr += "0 ";
            } else {
                strArr += appliedJobCode[i]+" ";
            }
        }
        return strArr;
    }

    public String getAllMember() {
        return  name + " " + emailAddress +" "+passWord+ " " + phoneNumber +" " + birthDate+" "+appliedJobCodePrint();
    }
}
