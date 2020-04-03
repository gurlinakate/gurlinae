import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.security.Timestamp;
import java.util.*;
import java.rmi.registry.*;
import java.rmi.server.*;

public class Author implements Serializable {//author
    private static final long serialVersionUID = 1L;
    private List<Book> books = new ArrayList<>();
    private String author;

    public Author() {
        this.books = new ArrayList<>();
        this.author = new String();
    }

    public Author(List<Book> boooook) {
        this.books = boooook;
        this.author = new String();
    }


    public Author(String authorname) {
        this.books = new ArrayList<>();
        this.author = author;
    }



    public void lengthOfMassive() throws RemoteException {
        int size = books.size();
        System.out.println("The are " + size + " books in the library");
    }

    public void AddBook() throws IOException, ClassNotFoundException {
        Book b1 = newbook();
        boolean wasFound = false;
        if (!wasFound) {
            books.add(b1);
        }

        System.out.println("New author added");
        System.out.println();
    }

    public void add(Book dish) {
        this.books.add(dish);
    }

    public void add(List<Book> dishes) {
        this.books.addAll(dishes);
    }


    public void FindBook() throws RemoteException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        ArrayList<Book> found = new ArrayList<>();
        String name;
        String title;

        while (true) {
            try {
                System.out.println("Enter author's name");
                name = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Invalid author's name");
            }
        }

        while (true) {
            try {
                System.out.println("Enter title");
                title = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Invalid book title");
            }
        }
        boolean wasFound = false;
        for (Book b : books) {
            if (b.getTitle().toLowerCase().equals(title.toLowerCase())) {
                found.add(b);
                wasFound = true;
            }
        }
        if (wasFound) {
            System.out.println("Here is what we managed to find, sir: ");
            for (Book b : found) {
                System.out.println("Title: " + b.getTitle()
                        + "/ Year: " + b.getYear() + "/ Number of pages: " + b.getPage() + "/ Price: "
                        + b.getPrice() + " Rub.");
                System.out.println();
            }
        }
        if (!wasFound) {
            System.out.println("No such book in store");
            System.out.println();
        }
    }


    public void RemoveBook() throws IOException, ClassNotFoundException {
        Book b1 = newbook();
        Iterator<Book> bookIterator = books.iterator();

        boolean wasFound = false;
        while (bookIterator.hasNext()) {
            Book b = bookIterator.next();
            if (b.getTitle().toLowerCase().equals(b1.getTitle().toLowerCase())
                    && (b.getYear() == b1.getYear()) && (b.getPage() == b1.getPage())
                    && (b.getPrice() == b1.getPrice())) {
                wasFound = true;
                //getBooks().remove(books);
                bookIterator.remove();
            }
        }
        if (wasFound) {
            //getBooks().remove(books);
            System.out.println("Book(s) removed successfully");
            System.out.println();
        }
        if (!wasFound) {
            System.out.println("No such book in store, sir");
            System.out.println();
        }
    }



    public List<Book> getBooks() {
        return books;
    }

    public ArrayList<Book> FillLibrary() throws IOException, ClassNotFoundException {
        Book book1 = new Book("Korporatsia_geniev", 2019, 344, 644);
        Book book2 = new Book( "Teremok", 2020, 900, 1400);
        Book book3 = new Book("Last_hunter", 2020, 600, 499);
        Book book4 = new Book( "Postscript", 2020, 256, 359);


        return new ArrayList<>((Arrays.asList(book1, book2, book3, book4)));
    }




    public  Book newbook() throws IOException, ClassNotFoundException {
        String name;
        String title2;
        int publishYear2;
        int numOfPages2;
        int price2;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println("Enter author's name");
                name = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Invalid author's name");
            }
        }
        while (true) {
            try {
                System.out.println("Enter author's title");
                title2 = reader.readLine();
                break;
            } catch (IOException e) {
                System.out.println("Invalid author's title");
            }
        }
        while (true) {
            try {
                System.out.println("Enter the year of publishing");
                publishYear2 = Integer.parseInt(reader.readLine());
                if (publishYear2 <= 0 || publishYear2 > Calendar.getInstance().get(Calendar.YEAR)) {
                    System.out.println("Invalid year");
                } else break;
            } catch (IOException e) {
                System.out.println("Invalid year");
            }
        }
        while (true) {
            try {
                System.out.println("Enter number of pages");
                numOfPages2 = Integer.parseInt(reader.readLine());
                if (numOfPages2 <= 5) {
                    System.out.println("Minimum number of pages is 5");
                } else break;
            } catch (IOException e) {
                System.out.println("Invalid number of pages");
            }
        }

        while (true) {
            try {
                System.out.println("Enter pricetag");
                price2 = Integer.parseInt(reader.readLine());
                if (price2 <= 0) {
                    System.out.println("Minimum price is 1 Rub.");
                } else break;
            } catch (IOException e) {
                System.out.println("Invalid pricetag");
            }
        }

        return (new Book(title2, publishYear2, numOfPages2, price2));
    }

    public void printLibrary(){
        for (Book b : getBooks()) {
            System.out.println("Title: " + b.getTitle() + "/ Year: " + b.getYear() +
                    "/ Number of pages: " + b.getPage() + "/ Price: "
                    + b.getPrice());
        }
    }
}
