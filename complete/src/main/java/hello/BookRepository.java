package hello;

public interface BookRepository {

    Book getByIsbn(String isbn);
    String getTitleByIsbn(String isbn);

}
