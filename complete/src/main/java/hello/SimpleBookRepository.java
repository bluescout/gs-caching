package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {

    @Autowired
    private CallsCounter counter;

    @Override
    public Integer getCallsCount(String methodName) {
        return counter.getCalls(methodName);
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName}")
    public Book getBestBook() {
        counter.addCall(getMethodName(new Object() {
        }));
        return new Book("isbn-BEST", "The Best Book Ever");
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName}")
    public Book getWorstBook() {
        counter.addCall(getMethodName(new Object() {
        }));
        return new Book("isbn-WORST", "The Worst Book Ever");
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName,#id}")
    public Book getById(long id) {
        counter.addCall(getMethodName(new Object() {
        }));
        return new Book("id-" + Long.toString(id), "Book With id " + id);
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName,#otherId}")
    public Book getByOtherId(long otherId) {
        counter.addCall(getMethodName(new Object() {
        }));
        return new Book("otherId-" + Long.toString(otherId), "Book With other id " + otherId);
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName,#isbn}")
    public Book getByIsbn(String isbn) {
        counter.addCall(getMethodName(new Object() {
        }));
        return new Book(isbn, "Book title " + isbn);
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName,#isbn}")
    public String getTitleByIsbn(String isbn) {
        counter.addCall(getMethodName(new Object() {
        }));
        return "Book title " + isbn;
    }

    
    @Override
    @Cacheable(value = "books", key = "{#root.methodName,#id}")
    public Book getByArray(int[] id) {
        counter.addCall(getMethodName(new Object() {
        }));
        String idStr = join(id);
        return new Book("id-" + idStr, "Book With id " + idStr);
    }

    @Override
    @Cacheable(value = "books", key = "{#root.methodName,#id}")
    public Book getByOtherArray(int[] id) {
        counter.addCall(getMethodName(new Object() {
        }));
        String idStr = join(id);
        return new Book("otherId-" + idStr, "Book With other id " + idStr);
    }

    
    private static String join(int[] numbers) {
        List<CharSequence> strings = new ArrayList<CharSequence>(numbers.length);
        for (long number : numbers) {
            strings.add(Long.toString(number));
        }
        
        return String.join("-", strings);
    }

    private static String getMethodName(Object innerClass) {
        return innerClass.getClass().getEnclosingMethod().getName();
    }

}
