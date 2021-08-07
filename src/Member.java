import java.util.ArrayList;

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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String[] getAppliedJobCode() {
        return appliedJobCode;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    void appliedJCodes(String jobCode){
        for(int i= 0; i < appliedJobCode.length; i++){
            if(appliedJobCode[i] != null){
                appliedJobCode[i] = jobCode;
                break;
            }
        }
    }

    void deleteJCodes(String jobCode){
        for(int i = 0; i < appliedJobCode.length; i++){
            if(appliedJobCode[i].equals(jobCode)){
                appliedJobCode[i] = null;
            }
        }
    }


    Boolean haveData(String data) {
        Boolean check = false;

        if (this.name.equals(data) || this.emailAddress.equals(data) || this.phoneNumber.equals(data)
                || this.birthDate.equals(data) || this.passWord.equals(data)) {
            check = true;
        }return check;
    }

    @Override
    public String toString() {
        return "이름 : '" + name + ", 이메일주소 : " + emailAddress + ", 전화번호 : " + phoneNumber +", 생년월일 : " + birthDate;
    }

}
