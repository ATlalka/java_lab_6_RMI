package Client;

import Manager.IManager;
import Manager.Security.RMISSLServerSocketFactory;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;

public class Client implements IClient{

    private IManager iManager;
    private PClient panel;

    public static void main(String []args) throws RemoteException, NotBoundException {

        //Policy.setPolicy(new MyPolicy());

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
        try {
            RMISSLServerSocketFactory factory = new RMISSLServerSocketFactory();
            RMISocketFactory.setSocketFactory(factory);
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            IManager manager = (IManager) registry.lookup(args[2]);
            Client c = new Client(manager);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Client(IManager manager) throws RemoteException {
        this.iManager = manager;
        panel = new PClient(this);
        panel.setTitle("Client Panel");
        panel.setDefaultCloseOperation(3);
        panel.setVisible(true);
        panel.setRemote((IClient) UnicastRemoteObject.exportObject(this, 0));
    }

    @Override
    public void setOrderId(int orderId) throws RemoteException {
        panel.addAd(orderId);
    }

    public IManager getiManager() {
        return iManager;
    }
}
