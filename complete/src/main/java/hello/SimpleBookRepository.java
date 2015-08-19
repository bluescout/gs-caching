package hello;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {

    @Override
    @Cacheable("books")
    public Book getByIsbn(String isbn) {
        return new Book(isbn, "Book title " + isbn);
    }

    @Override
    @Cacheable("books")
    public String getTitleByIsbn(String isbn) {
        return "Book title " + isbn;
    }

}
