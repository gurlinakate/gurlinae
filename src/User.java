import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.*;
import java.sql.DriverManager;
import java.sql.Driver;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class User {
    private int id;
    private String name;
    private String login;
    private String password;
    private List<Author> book = new ArrayList<>();

    User(String login, String password, String name, int id) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.name = name;
        this.book = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Author> getBooks() {
        return book;
    }

    public void addBook(Author book) throws IOException, ClassNotFoundException {
        this.book.add(book);
    }

    public void deleteBook(Author book) throws IOException, ClassNotFoundException {
        this.book.remove(book);
    }

    public int book_size(){
        return this.book.size();
    }
}
