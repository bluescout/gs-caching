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
 * 
 * The test fails when
 * <code>@Cacheable(value = "books", key = "{#root.methodName,#root.args}")</code>
 * annotation used as it does not cache.
 * <p>
 * <code>@Cacheable(value = "books", key = "{#root.methodName,#id}")</code>
 * (with list of all parameters after methodName in general) resolves the issue.
 * <p>
 * The tests cases are not isolated as they all use the same cache and book
 * repository instances.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationContext.class)
public class SimpleBookRepositoryTest_primitive_param {

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
    public void test_getById_cached() {
        repository.getById(1);
        repository.getById(1);

        Integer calls = repository.getCallsCount("getById");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the method is cached, thus called once.
     */
    @Test
    public void test_getByOtherId_cached() {
        repository.getByOtherId(1);
        repository.getByOtherId(1);

        Integer calls = repository.getCallsCount("getByOtherId");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the methods are cached to different entries.
     */
    @Test
    public void test_getById_getByOtherId_separated() {
        Book bookById = repository.getById(1);
        Book bookByOtherId = repository.getByOtherId(1);

        assertNotEquals(bookById.toString(), bookByOtherId.toString());
    }

}
