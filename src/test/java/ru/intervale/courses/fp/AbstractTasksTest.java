package ru.intervale.courses.fp;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public abstract class AbstractTasksTest {

    private Tasks tasks = getTasks();

    protected abstract Tasks getTasks();

    @Test
    public void sum() {
        assertEquals(0, tasks.sum(new int[]{}));
        assertEquals(42, tasks.sum(new int[]{42}));
        assertEquals(-2, tasks.sum(new int[]{-5, 0, 1, 2}));
    }

    // TODO: приготовить свои тесты для остальных методов
}
