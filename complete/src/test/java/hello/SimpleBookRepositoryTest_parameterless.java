package hello;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * The test fails when default <code>@Cacheable("books")</code> annotation used
 * on both methods as the second parameterless method returns cached value of
 * the first one despite method names.
 * <p>
 * The test fails as the method is not cached when
 * <code>@Cacheable(value = "books", key = "{#root.methodName,#root.args}")</code>
 * annotation used.
 * <p>
 * <code>@Cacheable(value = "books", key = "{#root.methodName}")</code> applied
 * to both methods resolves the issue.
 * <p>
 * The tests cases are not isolated as they all use the same cache and book
 * repository instances.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationContext.class)
public class SimpleBookRepositoryTest_parameterless {

    @Autowired
    @Qualifier("simpleBookRepository")
    private BookRepository repository;

    @Before
    public void setUp() {
    }

    /**
     * Check if the method is cached, thus called once.
     */
    @Test
    public void test_getBestBook_cached() {
        repository.getBestBook();
        repository.getBestBook();

        Integer calls = repository.getCallsCount("getBestBook");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the method is cached, thus called once.
     */
    @Test
    public void test_getWorstBook_cached() {
        repository.getWorstBook();
        repository.getWorstBook();

        Integer calls = repository.getCallsCount("getWorstBook");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the methods are cached to different entries.
     */
    @Test
    public void test_getBestBook_getWorstBook_separated() {
        Book worstBook = repository.getWorstBook();
        Book bestBook = repository.getBestBook();

        assertNotEquals(worstBook.toString(), bestBook.toString());
    }

}
