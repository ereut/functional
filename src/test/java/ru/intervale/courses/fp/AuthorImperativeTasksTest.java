package ru.intervale.courses.fp;

public class AuthorImperativeTasksTest extends AuthorTasksTest {
    @Override
    protected Tasks getTasks() {
        return new ImperativeTasksImpl();
    }
}
