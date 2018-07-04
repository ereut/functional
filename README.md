# [Intervale Java Courses](https://events.dev.by/kursy-java-v-gomele-s-tselyu-dalneyshego-trudoustroystva): Functional Programming

## Задание

* Форкнуть к себе заготовку проекта https://gitlab.com/vkopichenko/java-courses-functional-programming
* Реализовать методы интефейса `ru.intervale.courses.fp.Tasks` в функциональном и императивном стиле
* Можно добавлять свои показательные задачи по ещё не раскрытым здесь методам и нюансам. За бонус )
* Написать к ним тесты в `ru.intervale.courses.fp.AbstractTasksTest` 
* Можно выборочно сделать сравнительные замеры производительности в `ru.intervale.courses.fp.JmhBenchmarkTest`
* ??????
* PROFIT

Задания могут дополняться со временем. Так что выгоднее не затягивать. )

## "Конспект" лекции со ссылками

* Императивный и функциональный стиль
* Чистые функции: детерменированные и без побочных эффектов
* ФП растёт из математических абстракций и становится актуальнее с ростом числа ядер в CPU 
* mutable/immutable
* Хвостовая рекурсия
* Функции высших порядков


* Лямбды
* [@FunctionalInterface](https://docs.oracle.com/javase/8/docs/api/java/lang/FunctionalInterface.html) - не обязателен, да
* method references
* Function/BiFunction/Predicate/Supplier
* [Streams](https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html): 
* intermediate operations: filter, skip, limit, map, flatMap, sorted
* final operation: forEach, sum, count, min, max, reduce (aka fold), Collectors.collect* 
* IntStream.iterate: https://stackoverflow.com/questions/22483554/how-to-create-an-infinite-stream-with-java-8
* IntStream.generate: https://stackoverflow.com/questions/30595844/java-8-lambda-expressions-for-solving-fibonacci-non-recursive-way
* IntStream.range: https://stackoverflow.com/questions/47520654/using-intstream-to-generate-infinite-fibonacci-sequence
* parallel
* [Spliterator](https://docs.oracle.com/javase/8/docs/api/index.html?java/util/Spliterator.html)
* Внутреннее устройство: https://stackoverflow.com/questions/32936082/how-streams-pipeline-works-in-java-like-intpipeline
* https://www.ibm.com/developerworks/library/j-java-streams-3-brian-goetz/index.html
* https://stackoverflow.com/questions/30685623/how-to-implement-a-java-stream
* https://codereview.stackexchange.com/questions/52050/spliterator-implementation
* https://stackoverflow.com/questions/21163108/custom-thread-pool-in-java-8-parallel-stream
* https://softwareengineering.stackexchange.com/questions/250169/is-the-fork-join-framework-a-bad-match-for-the-java-8-streams-api
* Наброс про производительность: https://blog.takipi.com/benchmark-how-java-8-lambdas-and-streams-can-make-your-code-5-times-slower/
* Ещё замер: https://blog.codefx.org/java/stream-performance/


* Optional

#### Полезные ссылки:
* Streams javadoc: https://docs.oracle.com/javase/8/docs/api/java/util/stream/package-summary.html
* https://www.tutorialspoint.com/java8/java8_streams.htm
* [An Introduction to Functional Programming in Java 8](https://flyingbytes.github.io/programming/java8/functional/part0/2017/01/16/Java8-Part0.html)
* [Функторы, аппликативные функторы и монады в картинках](https://habr.com/post/183150/)

#### Кругозор:
* [Функциональное программирование на языке Haskell](https://stepik.org/course/75/)
* [Functional Programming Principles in Scala](https://www.coursera.org/learn/progfun1)
* Функциональные библиотеки для Java:
* https://github.com/xgrommx/awesome-functional-programming#java
* https://habr.com/company/jugru/blog/307938/
* https://habr.com/post/262139/