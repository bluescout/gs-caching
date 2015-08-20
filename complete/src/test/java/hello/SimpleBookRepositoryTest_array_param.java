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
 * <code>@Cacheable(value = "books", key = "{#root.methodName,#id}")</code> does
 * not cache as well as the cacheManager we use here is based on ConcurrentMap
 * and uses equals() and/or hashCode() to compare keys, that show the array
 * arguments different despite identical values.
 * <p>
 * I see no simple workaround for methods with array or vararg parameters.
 * <p>
 * The tests cases are not isolated as they all use the same cache and book
 * repository instances.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationContext.class)
public class SimpleBookRepositoryTest_array_param {

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
    public void test_getByArray_cached() {
        repository.getByArray(new int[] { 1, 2 });
        repository.getByArray(new int[] { 1, 2 });

        Integer calls = repository.getCallsCount("getByArray");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the method is cached, thus called once.
     */
    @Test
    public void test_getByOtherArray_cached() {
        repository.getByOtherArray(new int[] { 1, 2 });
        repository.getByOtherArray(new int[] { 1, 2 });

        Integer calls = repository.getCallsCount("getByOtherArray");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the methods are cached to different entries.
     */
    @Test
    public void test_getByArray_getByOtherArray_separated() {
        Book book1 = repository.getByArray(new int[] { 1, 2 });
        Book book2 = repository.getByOtherArray(new int[] { 1, 2 });

        assertNotEquals(book1, book2);
    }

}
