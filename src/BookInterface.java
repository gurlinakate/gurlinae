import java.io.File;
import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.rmi.*;

public interface BookInterface extends Remote {

    public Author SortBooks(String s, Author book) throws RemoteException;

}
