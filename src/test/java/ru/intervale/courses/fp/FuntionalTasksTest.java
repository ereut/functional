package ru.intervale.courses.fp;

public class FuntionalTasksTest extends AbstractTasksTest {
    @Override
    protected Tasks getTasks() {
        return new FunctionalTasksImpl();
    }
}
