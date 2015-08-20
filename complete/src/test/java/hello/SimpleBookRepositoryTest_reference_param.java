package hello;

import static org.junit.Assert.assertEquals;
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
 * <code>@Cacheable(value = "books", key = "{#root.methodName,#isbn}")</code>
 * (with list of all parameters after methodName in general) resolves the issue.
 * <p>
 * The tests cases are not isolated as they all use the same cache and book
 * repository instances.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringApplicationContext.class)
public class SimpleBookRepositoryTest_reference_param {

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
    public void test_getByIsbn_cached() {
        // create different instances of an argument
        String s1 = "1";
        String s2 = new String(s1);
        repository.getByIsbn(s1);
        repository.getByIsbn(s2);
        
        Integer calls = repository.getCallsCount("getByIsbn");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the method is cached, thus called once.
     */
    @Test
    public void test_getTitleByIsbn_cached() {
        repository.getTitleByIsbn("1");
        repository.getTitleByIsbn("1");

        Integer calls = repository.getCallsCount("getTitleByIsbn");
        assertNotNull(calls);
        assertEquals(1, calls.intValue());
    }

    /**
     * Check if the methods are cached to different entries.
     */
    @Test
    @SuppressWarnings("unused")
    public void test_getById_getByOtherId_separated() {
        Book book = repository.getByIsbn("1");
        String title = repository.getTitleByIsbn("1");
        
        /*  No assertions needed as collision will throw
         *  java.lang.ClassCastException: hello.Book cannot be cast to java.lang.String
         */
    }

}
