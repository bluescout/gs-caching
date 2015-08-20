package hello;

public interface BookRepository {

    Integer getCallsCount(String methodName);
    
    Book getBestBook();
    Book getWorstBook();
    
    Book getById(long id);
    Book getByOtherId(long id);
    
    Book getByIsbn(String isbn);
    String getTitleByIsbn(String isbn);

    Book getByArray(int[] id);
    Book getByOtherArray(int[] id);
}
