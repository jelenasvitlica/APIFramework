package rest.pojo;
import java.util.List;

public class Courses {
    private List<webAutomation> webAutomation;
    private List<api> api;
    private List<mobile> mobile;

    public List<webAutomation> getWebAutomation() {
        return webAutomation;
    }

    public List<api> getApi() {
        return api;
    }

    public List<mobile> getMobile() {
        return mobile;
    }

    public void setWebAutomation(List<webAutomation> webAutomation) {
        this.webAutomation = webAutomation;
    }

    public void setApi(List<api> api) {
        this.api = api;
    }

    public void setMobile(List<mobile> mobile) {
        this.mobile = mobile;
    }
}
