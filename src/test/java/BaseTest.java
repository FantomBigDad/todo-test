import org.example.todo.api.TestService;
import org.example.todo.config.Config;
import org.example.todo.model.ResponseWrapper;
import org.example.todo.model.Todo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class BaseTest {

    @AfterEach
    public void deleteAll() throws IOException {
        TestService testService = new TestService(Config.get("BASE_URL"));
        ResponseWrapper<List<Todo>> checkResp = testService.getController().takeTodo("/todos");


        List<Todo> todos = checkResp.getWrapperBody().getBody();

        if (todos != null && todos.size() > 10) {
            todos.sort(Comparator.comparing(Todo::getId));

            List<Todo> toDelete = todos.subList(0, todos.size() - 10);
            for (Todo todo : toDelete) {
                int id = (int) todo.getId();
                testService
                        .deleteController()
                        .deleteTodo("/todos", id);
            }
        }
    }

}
