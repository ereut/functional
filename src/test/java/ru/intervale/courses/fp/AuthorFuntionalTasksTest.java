package ru.intervale.courses.fp;

public class AuthorFuntionalTasksTest extends AuthorTasksTest {
    @Override
    protected Tasks getTasks() {
        return new FunctionalTasksImpl();
    }
}
