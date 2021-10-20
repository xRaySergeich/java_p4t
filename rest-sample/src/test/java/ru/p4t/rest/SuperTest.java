package ru.p4t.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.testng.SkipException;
import org.testng.annotations.BeforeClass;

import java.util.Set;

import static io.restassured.RestAssured.*;

public class SuperTest {

    @BeforeClass
    public void init() {
        authentication = basic("288f44776e7bec4bf44fdfeb1e646490", "");
    }

    boolean isIssueOpen(int issueId) {
        String json = get(String.format("https://bugify.stqa.ru/api/issues/%s.json", issueId)).asString();
        JsonElement parsed = JsonParser.parseString(json);
        JsonElement issues = parsed.getAsJsonObject().get("issues");
        Set<Issue> issueSet = new Gson().fromJson(issues, new TypeToken<Set<Issue>>(){}.getType());
        String status = issueSet.iterator().next().getState_name();
        if (status.equals("Resolved")) {
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
