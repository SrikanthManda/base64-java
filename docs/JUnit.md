## JUnit

JUnit test classes are set-up once for each `@Test` method, by default. Due to this, it is not advisable to have ordered tests or interdependent tests. Accordingly, `@Test` methods canNOT be `static`. For the same reason, `@BeforeEach` and `@AfterEach`, canNOT be `static`.


`@BeforeAll` method has to be `static` as it runs before the test class is instantiated. But it can be not `static` if the JUnit test class' `@TestInstance` is PER_CLASS


By convention, test classes and their members are `package-private` (the default, and hence no access modifier).


* __assume*__ methods enable conditional execution of tests.
* `assertAll` can run a set of asserts.
* `@Disabled` disables running the annotated test. (This annotation does not affect non-test methods.)
* `@RepeatedTest` repeats a test the number of times passed as the annotation argument.
* `@DisplayName` can be used to label tests.
* `@Tag` can be used tag tests, which can be used include or exclude tests using supported runners.


`TestInfo` interface can be used to access the `Tag` and `DisplayName` properties.


`TestReporter` interface can be used to redirect messages to JUnit console.