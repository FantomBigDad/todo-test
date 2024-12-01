import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.todo.api.HttpClient;
import org.example.todo.api.TestService;
import org.example.todo.config.Config;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.example.todo.utils.JsonUtils;
import org.junit.jupiter.api.*;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PutTests {

    @ParameterizedTest
    @CsvSource({
            "test change, true",
            "test change, false",
    })
    @DisplayName("Positive: Обновление данные элемента")
    public void updateTodoPositiveTest(String desc, boolean type) throws JsonProcessingException {
        TestService testService = new TestService(Config.get("BASE_URL"));
        Random random = new Random();
        int randomId = random.nextInt(1000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", randomId);
        jsonObject.put("text", "test");
        jsonObject.put("completed", true);

        testService
                .postController()
                .postTodo("/todos", jsonObject);

        int newRandomId = random.nextInt(1000);
        JSONObject newJsonObject = new JSONObject();
        newJsonObject.put("id", newRandomId);
        newJsonObject.put("text", desc);
        newJsonObject.put("completed", type);

        var response = testService
                .putController()
                .putTodo("/todos/" + randomId, newJsonObject);

        assertEquals(200, response.code(), "Expected status code is 200");

        ResponseWrapper<List<Todo>> checkResp = testService
                .getController()
                .takeTodo("/todos");

        assertTrue(new JsonUtils().checkJson(checkResp, newRandomId, desc, type), "Не найденно тело с его изменениями");
    }


    @ParameterizedTest
    @CsvSource({
            "-1, test change, kek",
            "@@@, , false",
    })
    @DisplayName("Negative: Попытка выставить невалидные параметры")
    public void updateTodoNegativeTest(String id, String desc, String type) throws JsonProcessingException {
        TestService testService = new TestService(Config.get("BASE_URL"));
        Random random = new Random();
        int randomId = random.nextInt(1000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", randomId);
        jsonObject.put("text", "test");
        jsonObject.put("completed", true);

        testService
                .postController().postTodo("/todos", jsonObject);

        JSONObject newJsonObject = new JSONObject();
        newJsonObject.put("id", id);
        newJsonObject.put("text", desc);
        newJsonObject.put("completed", type);

        var response = testService
                .putController()
                .putTodoInvalid("/todos/" + randomId, newJsonObject);

        assertEquals(401, response.getCode(), "Expected status code is 401");
    }

    @Test
    @DisplayName("Negative: Обновление данные элемента")
    public void updateTodoWichWrongIdNegativeTest() throws JsonProcessingException {
        TestService testService = new TestService(Config.get("BASE_URL"));
        Random random = new Random();
        int randomId = random.nextInt(1000);
        ResponseWrapper<List<Todo>> response = testService
                .getController()
                .takeTodo("/todos");

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", randomId);
        jsonObject.put("text", "test");
        jsonObject.put("completed", true);
        testService
                .postController()
                .postTodo("/todos", jsonObject);

        JSONObject newJsonObject = new JSONObject();
        newJsonObject.put("id", response.getWrapperBody().getBody().get(0).getId());
        newJsonObject.put("text", "desc");
        newJsonObject.put("completed", false);

        var resp = testService
                .putController()
                .putTodo("/todos/" + randomId, newJsonObject);

        assertEquals(400, resp.code(), "Expected status code is 400");
    }
}
