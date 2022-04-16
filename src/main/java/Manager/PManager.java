package Manager;

import Resources.Order;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.time.Duration;
import java.util.ArrayList;

public class PManager extends JFrame {

    private JPanel contentPane;
    private JTextField txtRunningBillboards;
    private JTextField txtPausedBillboards;
    private JTextField txtDisplayInterval;
    private JTextField textField;
    private JTextField txtWaitingOrders;
    private JTextField txtActieOrders;
    private JTextField txtChooseBillboard;
    private ArrayList<Order> waitingOrders = new ArrayList<>();
    private ArrayList<Integer> runningBillboards = new ArrayList<>();
    private ArrayList<Order> activeOrders = new ArrayList<>();
    private ArrayList<Integer> pausedBillboards = new ArrayList<>();

    private DefaultListModel<String> activeOrdersList = new DefaultListModel<>();
    private DefaultListModel<String> waitingOrdersList = new DefaultListModel<>();
    private DefaultListModel<String> runningBillboardsList = new DefaultListModel<>();
    private DefaultListModel<String> pausedBillboardsList = new DefaultListModel<>();

    private Manager manager;

    public PManager(Manager manager) {
        this.manager = manager;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 500);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 5, 573, 430);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("Billboards", null, panel, null);
        panel.setLayout(null);

        txtRunningBillboards = new JTextField();
        txtRunningBillboards.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtRunningBillboards.setEditable(false);
        txtRunningBillboards.setText("Running billboards");
        txtRunningBillboards.setBounds(276, 10, 238, 23);
        panel.add(txtRunningBillboards);
        txtRunningBillboards.setColumns(10);

        txtPausedBillboards = new JTextField();
        txtPausedBillboards.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtPausedBillboards.setText("Paused billboards");
        txtPausedBillboards.setEditable(false);
        txtPausedBillboards.setColumns(10);
        txtPausedBillboards.setBounds(10, 10, 227, 23);
        panel.add(txtPausedBillboards);

        JList list = new JList(pausedBillboardsList);
        list.setBounds(10, 39, 227, 261);
        panel.add(list);

        JList list_1 = new JList(runningBillboardsList);
        list_1.setBounds(276, 43, 238, 261);
        panel.add(list_1);

        JButton btnNewButton = new JButton("Start");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(manager.getBillboards().get(pausedBillboards.get(list.getSelectedIndex())).start()){
                        runningBillboards.add(pausedBillboards.get(list.getSelectedIndex()));
                        runningBillboardsList.addElement(pausedBillboards.get(list.getSelectedIndex()).toString());
                        pausedBillboards.remove(list.getSelectedIndex());
                        pausedBillboardsList.remove(list.getSelectedIndex());
                        JOptionPane.showMessageDialog(contentPane, "Billboard started");
                    }

                    else{
                        JOptionPane.showMessageDialog(contentPane, "Billboard hasn't started. Something went wrong. :(");

                    }
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton.setBounds(87, 370, 85, 21);
        panel.add(btnNewButton);

        txtDisplayInterval = new JTextField();
        txtDisplayInterval.setEditable(false);
        txtDisplayInterval.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtDisplayInterval.setText("Display interval");
        txtDisplayInterval.setBounds(10, 310, 109, 19);
        panel.add(txtDisplayInterval);
        txtDisplayInterval.setColumns(10);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setBounds(129, 310, 108, 19);
        panel.add(textField);
        textField.setColumns(10);

        JButton btnSet = new JButton("Set");
        btnSet.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Duration d = Duration.ofSeconds(Integer.parseInt(textField.getText()));
                try {
                    manager.getBillboards().get(pausedBillboards.get(list.getSelectedIndex())).setDisplayInterval(d);
                    JOptionPane.showMessageDialog(contentPane, "Display time was set");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }

            }
        });
        btnSet.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnSet.setBounds(87, 339, 85, 21);
        panel.add(btnSet);

        JButton btnStop = new JButton("Stop");
        btnStop.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    if(manager.getBillboards().get(runningBillboards.get(list_1.getSelectedIndex())).stop()){
                        pausedBillboards.add(runningBillboards.get(list_1.getSelectedIndex()));
                        pausedBillboardsList.addElement(runningBillboards.get(list_1.getSelectedIndex()).toString());
                        runningBillboards.remove(list_1.getSelectedIndex());
                        runningBillboardsList.remove(list_1.getSelectedIndex());
                        JOptionPane.showMessageDialog(contentPane, "Billboard is stopped");
                    }

                    else{
                        JOptionPane.showMessageDialog(contentPane, "Billboard hasn't been stopped. Something went wrong. :(");
                    }

                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnStop.setBounds(344, 314, 102, 41);
        panel.add(btnStop);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Orders", null, panel_1, null);
        panel_1.setLayout(null);

        txtWaitingOrders = new JTextField();
        txtWaitingOrders.setEditable(false);
        txtWaitingOrders.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtWaitingOrders.setText("Waiting orders");
        txtWaitingOrders.setBounds(10, 10, 285, 23);
        panel_1.add(txtWaitingOrders);
        txtWaitingOrders.setColumns(10);

        JList list_2 = new JList(waitingOrdersList);
        list_2.setBounds(10, 47, 285, 140);
        panel_1.add(list_2);

        txtActieOrders = new JTextField();
        txtActieOrders.setEditable(false);
        txtActieOrders.setText("Active orders");
        txtActieOrders.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtActieOrders.setColumns(10);
        txtActieOrders.setBounds(10, 207, 285, 23);
        panel_1.add(txtActieOrders);

        JList list_2_1 = new JList(activeOrdersList);
        list_2_1.setBounds(10, 240, 285, 153);
        panel_1.add(list_2_1);

        JList list_3 = new JList(pausedBillboardsList);
        list_3.setBounds(338, 47, 126, 136);
        panel_1.add(list_3);

        JButton btnNewButton_1 = new JButton("Place order");
        btnNewButton_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(list_2.getSelectedIndex()==-1 || list_3.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(contentPane, "Choose order and billboard");
                }

                else{
                    try {
                        int [] capacity = manager.getBillboards().get(pausedBillboards.get(list_3.getSelectedIndex())).getCapacity();
                        if(capacity[0] == 0){
                            JOptionPane.showMessageDialog(contentPane, "Billboard is full");
                        }

                        else if(capacity[1] < waitingOrders.get(list_2.getSelectedIndex()).advertText.length()){
                            JOptionPane.showMessageDialog(contentPane, "Too long text");
                        }

                        else{
                            for(Integer x : manager.getOrders().keySet()){
                                if(manager.getOrders().get(x).equals(waitingOrders.get(list_2.getSelectedIndex()))){
                                    manager.getBillboards().get(pausedBillboards.get(list_3.getSelectedIndex())).addAdvertisement(waitingOrders.get(list_2.getSelectedIndex()).advertText, waitingOrders.get(list_2.getSelectedIndex()).displayPeriod, x);
                                    manager.getOrdersOnBillboards().put(x, pausedBillboards.get(list_3.getSelectedIndex()));
                                    activeOrders.add(waitingOrders.get(list_2.getSelectedIndex()));
                                    activeOrdersList.addElement(waitingOrdersList.get(list_2.getSelectedIndex()));
                                    waitingOrders.remove(list_2.getSelectedIndex());
                                    waitingOrdersList.remove(list_2.getSelectedIndex());
                                    JOptionPane.showMessageDialog(contentPane, "Order placed");
                                    break;
                                }

                                else{
                                    JOptionPane.showMessageDialog(contentPane, "Order hasn't been placed. Something went wrong. :(");
                                }
                            }
                        }
                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton_1.setBounds(338, 193, 126, 37);
        panel_1.add(btnNewButton_1);

        txtChooseBillboard = new JTextField();
        txtChooseBillboard.setEditable(false);
        txtChooseBillboard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtChooseBillboard.setText("Choose billboard");
        txtChooseBillboard.setBounds(338, 10, 126, 23);
        panel_1.add(txtChooseBillboard);
        txtChooseBillboard.setColumns(10);

    }

    public void addOrder(Order order){
        waitingOrders.add(order);
        waitingOrdersList.addElement(order.advertText);
    }

    public void removeOrder(Order order){
        for(Order i : waitingOrders){
            if(i.equals(order)){
                waitingOrdersList.remove(waitingOrders.indexOf(i));
                waitingOrders.remove(i);
                return;
            }
        }

        for(Order i : activeOrders){
            if(i.equals(order)){
                activeOrdersList.remove(activeOrders.indexOf(i));
                activeOrders.remove(i);
                return;
            }
        }
    }

    public void addBillboard(int id){
        pausedBillboards.add(id);
        pausedBillboardsList.addElement(Integer.toString(id));
    }

    public void removeBillboard(int id){
        if(runningBillboards.indexOf(id) == -1){
            pausedBillboards.remove(pausedBillboards.indexOf(id));
            pausedBillboardsList.remove(pausedBillboardsList.indexOf(Integer.toString(id)));
        }
        else{
            runningBillboards.remove(runningBillboards.indexOf(id));
            runningBillboardsList.remove(runningBillboardsList.indexOf(Integer.toString(id)));
        }
    }
}
