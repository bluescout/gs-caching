package hello;

public interface BookRepository {

    Book getByIsbn(String isbn);

    String getTitleByIsbn(String isbn);

    Book getBestBook();

    Book getWorstBook();
    
    Book getById(long id);
    
    Book getById(String id);

}
