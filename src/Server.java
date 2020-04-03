import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Server implements BookInterface  {

    public static void main(String []args){
        Server server = new Server();
        server.createStubAndBind();
    }

    public void createStubAndBind() {
        try {
            // Создание удаленного RMI объекта
            BookInterface stub = (BookInterface) UnicastRemoteObject.exportObject((Server) this, 0);
            // Определение имени удаленного RMI объекта
            String serviceName = "BookInterface";

            System.out.println("Initializing " + serviceName);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind(serviceName, stub);
            System.out.println("Start " + serviceName);
        } catch (RemoteException e) {
            System.err.println("RemoteException : " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("Exception : " + e.getMessage());
            e.printStackTrace();
            System.exit(2);

        }
    }

    public Author SortBooks(String s, Author book) throws RemoteException {
        switch (s.toLowerCase()) {
            case "title":
                Collections.sort(book.getBooks(), new Comparator<Book>() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                return o1.getTitle().compareTo(o2.getTitle());
                            }
                        }
                );
                System.out.println("Books sorted by " + s);
                break;
            case "year":
                Collections.sort(book.getBooks(), new Comparator<Book>() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                return o1.getYear() - (o2.getYear());
                            }
                        }
                );
                System.out.println("Books sorted by " + s);
                break;
            case "number of pages":
                Collections.sort(book.getBooks(), new Comparator<Book>() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                return o1.getPage() - (o2.getPage());
                            }
                        }
                );
                System.out.println("Books sorted by " + s);
                break;
            case "price":
                Collections.sort(book.getBooks(), new Comparator<Book>() {
                            @Override
                            public int compare(Book o1, Book o2) {
                                return (int) (o1.getPrice() - (o2.getPrice()));
                            }
                        }
                );
                System.out.println("Books sorted by " + s);
                break;
            default:
                System.out.println("Unknown criteria");
        }
        return book;
    }


}
