package Billboard;

import Manager.IManager;
import Manager.Security.RMISSLServerSocketFactory;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;

public class Billboard implements IBillboard {

    public IManager iManager;
    private int id;
    private ArrayList<BillboardsOrder> ordersQueue = new ArrayList<BillboardsOrder>();
    private Duration displayInterval;
    private PBillboard panel;
    boolean ifRunning = false;
    private int[] capacity = new int[2];
    private Thread t;

    public static void main(String []args) throws RemoteException, NotBoundException {
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            RMISSLServerSocketFactory factory = new RMISSLServerSocketFactory();
            RMISocketFactory.setSocketFactory(factory);
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));
            IManager manager = (IManager) registry.lookup(args[2]);
            Billboard b = new Billboard(manager);
            b.panel.setText("");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public Billboard(IManager manager) {
        this.iManager = manager;
        capacity[0] = 10;
        capacity[1] = 1260;
        panel = new PBillboard(this);
        panel.setTitle("Billboard");
        panel.setDefaultCloseOperation(3);
        panel.setVisible(true);
        try {
            id = iManager.bindBillboard((IBillboard) UnicastRemoteObject.exportObject(this, 0));
            panel.setTitle("Billboard: "+id);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        t = new Thread(() -> {
            try {
                while (true) {
                    if(ordersQueue.size()>0){
                        if (ifRunning){
                            ArrayList<BillboardsOrder> ordersToRemove = new ArrayList<>();
                            for(BillboardsOrder order : ordersQueue){
                                if(order.getLeftDurationDisplay().toSeconds() >= displayInterval.toSeconds()){
                                    Duration duration = order.getLeftDurationDisplay().minus(displayInterval);
                                    order.setLeftDurationDisplay(duration);
                                    panel.setText(order.getAdvertText());
                                    Thread.sleep(displayInterval.toMillis());
                                }
                                else{
                                    ordersToRemove.add(order);
                                }
                            }
                            for(BillboardsOrder order : ordersToRemove){
                                ordersQueue.remove(order);
                            }
                        }
                    }

                    else{
                        panel.setText("");
                    }

                }
            } catch (InterruptedException ignored) { }
        });
        t.start();
    }

    public IManager getiManager() {
        return iManager;
    }

    public int getId() {
        return id;
    }

    public Thread getT() {
        return t;
    }

    @Override
    public boolean addAdvertisement(String advertText, Duration displayPeriod, int orderId) throws RemoteException {
        BillboardsOrder order = new BillboardsOrder(orderId, advertText, displayPeriod);
        ordersQueue.add(order);
        if(ordersQueue.indexOf(order)==-1)
            return false;
        else{
            capacity[0]--;
            return true;
        }

    }

    @Override
    public boolean removeAdvertisement(int orderId) throws RemoteException {
        for(BillboardsOrder order : ordersQueue){
            if(order.getId() == orderId){
                ordersQueue.remove(order);
                return true;
            }
        }
        return false;
    }

    @Override
    public int[] getCapacity() throws RemoteException {
        return capacity;
    }

    @Override
    public void setDisplayInterval(Duration displayInterval) throws RemoteException {
        this.displayInterval = displayInterval;
    }

    @Override
    public boolean start() throws RemoteException {
        if(ifRunning == true)
            return false;
        else{
            ifRunning = true;
            return true;
        }
    }

    @Override
    public boolean stop() throws RemoteException {
        if(ifRunning == false)
            return false;
        else{
            ifRunning = false;
            return true;
        }
    }
}
