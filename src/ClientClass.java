import java.sql.*;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

class Constants {
    public static final String authorName = new String(new String("Granzhe"));
    public static final Author name = new Author(authorName);
    public static final User user = new User("qwerty", "12345", "Anna", 1);
    public static final Book book = new Book("Last_hunter", 2020, 600, 499);
}

public class ClientClass {
    String url = "jdbc:mysql://localhost/library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    String username = "root";
    String password = "megapassword";

    private Connection connection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, username, password);
    }

        public void createTables() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
            String sqlCommand = "CREATE TABLE customer (Id INT PRIMARY KEY AUTO_INCREMENT not null, name VARCHAR(20) not null, " +
                    "login VARCHAR(20) not null, password VARCHAR(20) not null)";
            String sqlCommand2 = "CREATE TABLE book (title  VARCHAR(20) PRIMARY KEY not null, year INT, pages INT, price INT)";
            String sqlCommand3 = "CREATE TABLE author (Id INT not null, authorName VARCHAR(20) PRIMARY KEY)";
            String sqlCommand4 = "CREATE TABLE orderBook (authorName  VARCHAR(20) PRIMARY KEY not null, title VARCHAR(20) not null)";
            statement.execute(sqlCommand);
            statement.execute(sqlCommand2);
            statement.execute(sqlCommand3);
            statement.execute(sqlCommand4);
            System.out.println("the tables have been created");
            statement.close();
            connection.close();
        }

        public void filltables()throws SQLException, ClassNotFoundException{
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate("INSERT customer(id, name, login, password) VALUES (1, 'Anna', 'qwerty', '12345')," +
                    "(2, 'Petr', 'qwertyuiop', '098765'), (3, 'Kate', 'asdfgh', '456789')");
        int rows2 = statement.executeUpdate("INSERT book(title, year, pages, price) VALUES ('Last_hunter', 2020, 600, 499 )," +
                        "('Korporatsia_geniev', 2019, 344, 644), ('Teremok', 2020, 900, 1440), " +
                "('Postscript',  2020, 256, 359)");
        int rows3 = statement.executeUpdate("INSERT author(id , authorName) VALUES (1, 'Granzhe')," +
                    "(2, 'Ed Ketmel'), ( 3, 'Tolstoi A.N'), (4, 'Ahern Sesiliya')");
        int rows4 = statement.executeUpdate("INSERT orderBook(authorName, title) VALUES ('Granzhe', 'Last_hunter')," +
                    "('Ed Ketmel','Korporatsia_geniev' ), ('Tolstoi A.N','Teremok' ), ('Ahern Sesiliya','Postscript' )");
        System.out.printf("Added %d rows in table /customer/", rows);
        System.out.printf("\n"+" Added %d rows in table /book/", rows2);
        System.out.printf("\n"+" Added %d rows in table /author/", rows3);
        System.out.printf("\n" +" Added %d rows in table /orderBook/", rows4);
        statement.close();
        connection.close();
        }

        public void deleteBook()throws SQLException, ClassNotFoundException{
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        int rows = statement.executeUpdate("DELETE FROM book WHERE year = 2019");
        //int rows2 = statement.executeUpdate("DELETE FROM book WHERE price > 1000");
        System.out.printf("\n" +"%d row(s) deleted", rows);
        statement.close();
        connection.close();
        }

        public boolean checkUserExistence() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        ResultSet userExist = statement.executeQuery("SELECT * FROM customer WHERE login = '" + Constants.user.getLogin()  +
                "'AND password = '" + Constants.user.getPassword()+ "'");
        return userExist.next();
        }

    public void getBookByAuthorId(int id) throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM book WHERE title IN(SELECT title FROM orderbook WHERE authorName in" +
                "(SELECT authorName FROM " + "author WHERE id = '" + id + "'))" );
        while (resultSet.next()) {
            String title = resultSet.getString(1);
            int year = resultSet.getInt(2);
            int pages = resultSet.getInt(3);
            double price = resultSet.getDouble(4);
            System.out.printf("title:"+String.valueOf(title)+ " "+"\n");
            System.out.printf("publishOfYear:"+String.valueOf(year)+ " "+"\n");
            System.out.printf("pages:"+String.valueOf(pages)+ " "+"\n");
            System.out.printf("price:"+String.valueOf(price)+ " "+"\n");
        }
        statement.close();
        connection.close();
    }

        public void getUsersBook(User user) throws SQLException, ClassNotFoundException {
        int userId = user.getId();
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT id FROM author WHERE id = '" + userId + "'");
        List<Author> books = new LinkedList<>();
        while (resultSet.next()) {
            getBookByAuthorId(resultSet.getInt(1));
        }
        statement.close();
        connection.close();
        }

        public void selectAll() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM book");
        while(resultSet.next()){
            String title = resultSet.getString(1);
            int year = resultSet.getInt(2);
            int pages = resultSet.getInt(3);
            int price = resultSet.getInt(4);
            System.out.printf("title:"+String.valueOf(title)+ " "+"\n");
            System.out.printf("publishOfYear:"+String.valueOf(year)+ " "+"\n");
            System.out.printf("pages:"+String.valueOf(pages)+ " "+"\n");
            System.out.printf("price:"+String.valueOf(price)+ " "+"\n");
            System.out.println();
            }
        }

        public void getUsersByBook() throws SQLException, ClassNotFoundException {
        Connection connection = this.connection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM customer WHERE id IN(SELECT id FROM author WHERE authorName IN(" +
                "SELECT authorName FROM orderBook WHERE title = '" + Constants.book.getTitle()+ "'))");
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String login = resultSet.getString(3);
                String password = resultSet.getString(4);
                System.out.printf("id:"+ String.valueOf(id)+ " " +"\n");
                System.out.printf("name:"+String.valueOf(name)+ " "+"\n");
                System.out.printf("login:"+String.valueOf(login)+ " "+"\n");
                System.out.printf("password:"+String.valueOf(password)+ " "+"\n");
            }
        }

    public static void main(String ...args) throws SQLException, ClassNotFoundException {
        ClientClass manipulator = new ClientClass();
        //manipulator.createTables(); //создание таблиц
        //manipulator.filltables(); // заполнение таблиц
        //manipulator.deleteBook(); // удаление элементов таблицы

        //boolean exist = manipulator.checkUserExistence(); //проверка наличия пользователя
        //System.out.println(exist);

        //manipulator.getUsersBook(Constants.user); // вывод всех книг пользователя
        //manipulator.getBookByAuthorId(Constants.user.getId()); //вывод книг по заданному Id
        //manipulator.selectAll(); // вывод содержимого списка Book
        //manipulator.getUsersByBook(); //вывод пользователя по заданной книге
    }
}
