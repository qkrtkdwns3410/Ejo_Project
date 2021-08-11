import java.io.Serializable;

/*
 
 */
public class Job implements Serializable {
    private static final long serialVersionUID = -6184044926029805156L;
    private String jobCode;
    private String department;
    private String jobName;
    private String postingDate;
    
    public String getJobCode() {    //EZ_0000
        return jobCode;
    }
    
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public String getJobName() {
        return jobName;
    }
    
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    
    public String getPostingDate() {
        return postingDate;
    }
    
    public void setPostingDate(String postingDate) {
        this.postingDate = postingDate;
    }
    
    
    public String getAllJob() {
        return jobCode + " " + department + " " + jobName + " " + postingDate;
    }
    
    @Override
    public String toString() {
        return "[" + jobCode + "]    채용부서 : " + department + "    공고명 : " + jobName + "    게시일자 : " + postingDate;
    }
    
}
