import java.io.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;


public class BookClient {
    static String localhost = "127.0.0.1";
    static String RMI_HOSTNAME = "java.rmi.server.hostname";
    static String SERVICE_PATH = "rmi://localhost/BookInterface";
    private File shopBookDepository = new File("Shop");
    private File shopBooks = new File(shopBookDepository, "Library.txt");
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



    public BufferedReader getReader() {
        return reader;
    }

    public File getShopBookDepository(){
        return shopBookDepository;
    }
    public File getShopBooks() {
        return shopBooks;
    }

    public void FillLibrary(Author book) throws IOException, ClassNotFoundException {
        Book book1 = new Book("Korporatsia_geniev", 2019, 344, 644);
        Book book2 = new Book("Teremok", 2020, 900, 1400);
        Book book3 = new Book("Last_hunter", 2020, 600, 499);
        Book book4 = new Book( "Postscript", 2020, 256, 359);
        book.getBooks().add(book1);
        book.getBooks().add(book2);
        book.getBooks().add(book3);
        book.getBooks().add(book4);
    }

    public void WriteFile(Author book) throws IOException {
        File file = new File("Library.txt");
        ObjectOutputStream oos = null;
        FileOutputStream fos = new FileOutputStream(file);
        oos = new ObjectOutputStream(fos);
        oos.writeObject(book.getBooks());
        oos.close();
    }

    public ArrayList<Book> ReadFile() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream("Library.txt");
        ObjectInputStream ois = new ObjectInputStream(fis);
        @SuppressWarnings("unchecked")
        ArrayList<Book> list = (ArrayList<Book>) ois.readObject();
        return list;
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try {
            // устанавливаем соединение
            System.setProperty(RMI_HOSTNAME, localhost);
            // URL удаленного объекта
            String objectName = SERVICE_PATH;

            BookInterface bs = (BookInterface) Naming.lookup(objectName);
            System.out.println("Welcome to Black Books, how may I be of service?");
            System.out.println("Type \"commands\" for commands list");
            System.out.println();

            BookClient bookClient = new BookClient();
            Author book = new Author();
            bookClient.FillLibrary(book);

            if (!bookClient.getShopBookDepository().exists()) {
                bookClient.getShopBookDepository().mkdir();
            }
            if (!bookClient.getShopBooks().exists()) {
                bookClient.getShopBooks().createNewFile();
            }
            // блок команд
            while (true) {
                System.out.println("Input command");

                String command = bookClient.getReader().readLine();
                switch (command.toLowerCase()) {
                    case "commands":
                        System.out.println("Show books — shows the list of all books in store");
                        System.out.println("Add book — adds new book to shop's depository");
                        System.out.println("Remove book — removes selected book from shop's depository");
                        System.out.println("Sort books — sorts all books by user defined criteria");
                        System.out.println("Find book — searches for a certain book in shop's depository");
                        System.out.println("Write to file — writes an array to a file");
                        System.out.println("Read from file — reads an array from file");
                        System.out.println("Length — the number of books in the library");
                        System.out.println("Find book — finds a book in the library by author and title");
                        System.out.println();
                        break;
                    case "show books":
                        System.out.println("Books currently in store: ");
                        book.printLibrary();
                        break;
                    case "add book": {
                        //bs.AddBook();
                        book.AddBook();
                        book.printLibrary();
                        break;
                    }
                    case "remove book": {
                        book.RemoveBook();
                        break;
                    }
                    case "sort books": {
                        System.out.println("Enter sorting criteria");
                        System.out.println("Author/Title/Publisher/Year/Number of pages/Price/Quantity");
                        String srt = bookClient.getReader().readLine();
                        book = bs.SortBooks(srt, book);
                        book.printLibrary();
                        break;
                    }
                    case "write to file": {
                        bookClient.WriteFile(book);
                        break;
                    }
                    case "read from file": {
                        ArrayList<Book> library = bookClient.ReadFile();
                        for (Book b : library) {
                            System.out.println("Title: " + b.getTitle() + "/ Year: " + b.getYear() +
                                    "/ Number of pages: " + b.getPage() + "/ Price: "
                                    + b.getPrice());
                        }
                        break;
                    }
                    case "length": {
                        book.lengthOfMassive();
                        break;
                    }
                    case "find book": {
                        book.FindBook();
                        break;
                    }
                }
            }


        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    }
