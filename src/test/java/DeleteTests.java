import org.example.todo.api.TestService;
import org.example.todo.config.Config;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.example.todo.utils.JsonUtils;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DeleteTests {

    @Test
    @DisplayName("Positive: Удаление элемента")
    public void deleteTodoPositiveTest() throws IOException {
        TestService testService = new TestService(Config.get("BASE_URL"));

        Random random = new Random();
        int randomId = random.nextInt(10000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", randomId);
        jsonObject.put("text", "Test from del");
        jsonObject.put("completed", true);

        testService
                .postController().postTodo("/todos", jsonObject);

        var response = testService
                .deleteController()
                .deleteTodo("/todos", randomId);

        assertEquals(204, response.code());

        ResponseWrapper<List<Todo>> checkResp = testService.getController().takeTodo("/todos");

        assertFalse(new JsonUtils().checkJson(checkResp, randomId, "Test from del", true), "Не найденно тело с его изменениями");
    }

    @Test
    @DisplayName("Positive: Повторное удаление элемента")
    public void deleteTodoAgainPositiveTest() throws IOException {
        TestService testService = new TestService(Config.get("BASE_URL"));

        Random random = new Random();
        int randomId = random.nextInt(1000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", randomId);
        jsonObject.put("text", "Test from del");
        jsonObject.put("completed", true);

        testService
                .postController().postTodo("/todos", jsonObject);

        var response = testService
                .deleteController()
                .deleteTodo("/todos", randomId);
        assertEquals(204, response.code());

        var responseSecond = testService
                .deleteController()
                .deleteTodo("/todos", randomId);
        assertEquals(404, responseSecond.code());
    }

    @ParameterizedTest
    @CsvSource({
            "99999999999999",
            "null",
            "s",
            "-1"
    })
    @DisplayName("Negative: Попытка удалить несуществующий ID")
    public void deleteTodoNegativeIDTest(String id) {
        TestService testService = new TestService(Config.get("BASE_URL"));

        var response = testService
                .deleteController()
                .sendWrongDelete("/todos", id, Config.get("AUTH_CRED"));

        assertEquals(404, response.code());
    }

    @Test
    @DisplayName("Negative: Попытка удалить с кривыми кредами")
    public void deleteTodoNegativeFaildAuthTest() {
        TestService testService = new TestService(Config.get("BASE_URL"));

        var response = testService
                .deleteController()
                .sendWrongDelete("/todos", "0", "faild");

        assertEquals(401, response.code());
    }

}
