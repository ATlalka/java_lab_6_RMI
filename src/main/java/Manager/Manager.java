package Manager;

import Billboard.IBillboard;
import Client.IClient;

import Manager.Security.RMISSLServerSocketFactory;
import Resources.Order;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.security.Policy;
import java.util.HashMap;

public class Manager implements IManager{

    private HashMap<Integer, IClient> clients = new HashMap<>();
    private HashMap<Integer, Integer> ordersOnBillboards = new HashMap<>();
    private HashMap<Integer, IBillboard> billboards = new HashMap<>();
    private HashMap<Integer, Order> orders = new HashMap<>();
    private static Integer clientNr = 1;
    private static Integer billboardNr = 1;
    private static Integer orderNr = 1;

    private PManager panel;

    public static void main(String []args){

        //Policy.setPolicy(new MyPolicy());

        if(System.getSecurityManager() == null){
            System.setSecurityManager(new SecurityManager());
            System.out.println("New security manager has been set.");
        }

        try {
            RMISSLServerSocketFactory factory = new RMISSLServerSocketFactory();
            RMISocketFactory.setSocketFactory(factory);
            Registry reg = LocateRegistry.createRegistry(3000);
            Manager manager = new Manager();
            reg.rebind("Manager", UnicastRemoteObject.exportObject(manager, 0));
            System.out.println("Manager is ready");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Manager() {
        panel = new PManager(this);
        panel.setTitle("Manager Panel");
        panel.setDefaultCloseOperation(3);
        panel.setVisible(true);
    }

    @Override
    public int bindBillboard(IBillboard billboard) throws RemoteException {
        billboards.put(billboardNr, billboard);
        billboardNr++;
        panel.addBillboard(billboardNr-1);
        return billboardNr-1;
    }

    @Override
    public boolean unbindBillboard(int billboardId) throws RemoteException {
        if(billboards.get(billboardId)!=null){
            billboards.remove(billboardId);
            panel.removeBillboard(billboardId);
            return true;
        }
        else
            return false;
    }

    @Override
    public boolean placeOrder(Order order) throws RemoteException {
        orders.put(orderNr, order);
        orderNr++;
        order.client.setOrderId(orderNr-1);
        panel.addOrder(order);
        return true;
    }

    @Override
    public boolean withdrawOrder(int orderId) throws RemoteException {
        if(orders.get(orderId)==null)
            return false;
        else{

            if(billboards.get(ordersOnBillboards.get(orderId)).removeAdvertisement(orderId)){
                panel.removeOrder(orders.get(orderId));
                orders.remove(orderId);
                ordersOnBillboards.remove(orderId);
                return true;
            }

            else{
                return false;
            }
        }
    }

    public HashMap<Integer, IBillboard> getBillboards() {
        return billboards;
    }

    public HashMap<Integer, Integer> getOrdersOnBillboards() {
        return ordersOnBillboards;
    }

    public HashMap<Integer, Order> getOrders() {
        return orders;
    }
}
