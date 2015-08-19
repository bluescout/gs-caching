package hello;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {

    @Override
    @Cacheable(value="books", key="{#root.methodName,#root.args}")
    public Book getByIsbn(String isbn) {
        return new Book(isbn, "Book title " + isbn);
    }

    @Override
    @Cacheable(value="books", key="{#root.methodName,#root.args}")
    public String getTitleByIsbn(String isbn) {
        return "Book title " + isbn;
    }

    @Override
    @Cacheable(value="books", key="{#root.methodName,#root.args}")
    public Book getBestBook() {
        return new Book("isbn-BEST", "The Best Book Ever");
    }

    @Override
    @Cacheable(value="books", key="{#root.methodName,#root.args}")
    public Book getWorstBook() {
        return new Book("isbn-WORST", "The Worst Book Ever");
    }

    @Override
    @Cacheable(value="books", key="{#root.methodName,#root.args}")
    public Book getById(long id) {
        return new Book(Long.toString(id), "Book With 'long' id " + id);
    }

    @Override
    @Cacheable(value="books", key="{#root.methodName,#root.args}")
    public Book getById(String id) {
        return new Book(id, "Book title 'String' id " + id);
    }

}
