package ru.intervale.courses.fp;

public class ImperativeTasksTest extends AbstractTasksTest {
    @Override
    protected Tasks getTasks() {
        return new ImperativeTasksImpl();
    }
}
