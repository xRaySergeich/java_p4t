package ru.p4t.rest;

import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

import static io.restassured.RestAssured.*;

public class SuperTest {

    @BeforeClass
    public void init() {
        authentication = basic("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    boolean isIssueOpen(int issueId) {
        IssueResponse json = get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)).as(IssueResponse.class);
        if (json.issues.get(0).getState_name().equals("Resolved")) {
            return false;
        }
        return true;
    }

    public void skipIfNotFixed(int issueId) {
        if (isIssueOpen(issueId)) {
            throw new SkipException("Ignored because of issue " + issueId);
        }
    }


}
