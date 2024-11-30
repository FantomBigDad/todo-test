
import org.example.todo.api.HttpClient;
import org.example.todo.api.TestService;
import org.example.todo.config.Config;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.junit.jupiter.api.*;
import org.json.JSONObject;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


public class PostTests {


    @ParameterizedTest
    @CsvSource({
            "12, true",
            "s, false",
            "@@@@, false",
            "true, true"
    })
    @DisplayName("Positive: Создать элемент")
    public void createTodoPositiveTest(String desc, boolean type) {
        TestService testService = new TestService(Config.get("BASE_URL"));
        Random random = new Random();
        int randomId = random.nextInt(1000);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", randomId);
        jsonObject.put("text", desc);
        jsonObject.put("completed", type);


        var response = testService
                .postController().postTodo("/todos", jsonObject);

        assertEquals(201, response.code(), "Expected status code is 201");
    }

    @ParameterizedTest
    @CsvSource({
            "1.2 , 1, true",
            "-1,ssssss, false",
            "s, , false",
            "ы,true, true"
    })
    @DisplayName("Negative: Создание элемента по негативным данным")
    public void createTodoNegativeTest(String id) {
        TestService testService = new TestService(Config.get("BASE_URL"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("text", "test");
        jsonObject.put("completed", false);


        var response = testService
                .postController().postTodoInvalid("/todos", jsonObject);

        assertEquals(400, response.getCode(), "Expected status code is 400");
        assertTrue(response.getWrapperBody().getBody().contains("Request body deserialize error: invalid type:"));
    }


    @ParameterizedTest
    @CsvSource({
            "10",
            "100",
            "1000"
    })
    @DisplayName("Performance: Измерение времени выполнения POST /todos")
    public void measurePostTodoPerformanceTest(int requestCount) {
        TestService testService = new TestService(Config.get("BASE_URL"));
        Random random = new Random();

        long totalTime = 0;
        int successfulRequests = 0;

        for (int i = 0; i < requestCount; i++) {
            int randomId = random.nextInt(1000);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", randomId);
            jsonObject.put("text", "Performance test " + randomId);
            jsonObject.put("completed", random.nextBoolean());

            long startTime = System.currentTimeMillis();

            var response = testService.postController().postTodo("/todos", jsonObject);

            long elapsedTime = System.currentTimeMillis() - startTime;
            totalTime += elapsedTime;

            if (response.code() == 201) {
                successfulRequests++;
            }
        }

        double averageTime = (double) totalTime / requestCount;

        System.out.printf("Сводка теста:%n");
        System.out.printf("Количество запросов: %d%n", requestCount);
        System.out.printf("Среднее время отклика: %.2f мс%n", averageTime);
        System.out.printf("Успешных запросов (код 201): %d%n", successfulRequests);

        assertTrue(averageTime < 500, "Среднее время отклика превышает 500 мс");
    }

}

