

import org.example.todo.api.TestService;
import org.example.todo.config.Config;

import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GetTests {

    @Test
    @DisplayName("Positive: Получение заметок")
    public void getTodosPositiveTest() {
        TestService testService = new TestService(Config.get("BASE_URL"));
        ResponseWrapper<List<Todo>> response = testService.getController().takeTodo("/todos");

        assertEquals(200, response.getCode());
        assertFalse(response.getWrapperBody().getBody().isEmpty(), "Список TODO не должен быть пустым");
    }

    @ParameterizedTest
    @CsvSource({
            "0, 10",
            "1000, 10",
            "1, 3",
            "0, 0",
    })
    @DisplayName("Positive: Получение офсетов")
    public void getTodosPositiveOffsetsTest(int offset, int limit) {
        TestService testService = new TestService(Config.get("BASE_URL"));
        ResponseWrapper<List<Todo>> response;
        try {
            response = testService.getController().takeTodo("/todos?offset=" + offset + "&limit=" + limit);
            assertNotNull(response, "Response should not be null for valid offset");
            assertEquals(200, response.getCode(), "Expected status code is 200 for valid offset");

            if (offset >= 1000) {
                assertTrue(response.getWrapperBody().getBody().isEmpty(), "Expected empty list for large offset");
            }
        } catch (Exception e) {
            throw e;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0, !",
            "null, null",
            "s, l",
            "-1, +1000"
    })
    @DisplayName("Negative: Получение офсетов по негативным данным")
    public void getTodosNegativeOffsetsTest(String offset, String limit) {
        TestService testService = new TestService(Config.get("BASE_URL"));
        ResponseWrapper<String> response;
        try {
            response = testService.getController().takeTodoInvalid("/todos?offset=" + offset + "&limit=" + limit);
            assertEquals(400, response.getCode(), "Expected status code is 400");
            assertEquals("Invalid query string", response.getWrapperBody().getBody().toString());
        } catch (Exception e) {
            throw e;
        }
    }

}