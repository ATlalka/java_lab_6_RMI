package Client;

import Resources.Order;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.time.Duration;
import java.util.ArrayList;

public class PClient extends JFrame {

    private DefaultListModel<String> currentOrdersList = new DefaultListModel<>();
    private ArrayList<ClientsOrder> currentOrders = new ArrayList<>();
    private DefaultListModel<String> finishedOrdersList = new DefaultListModel<>();

    private JPanel contentPane;
    private JTextField txtYourCurrentAdvertisements;
    private JTextField txtNewAdvertisement;
    private JTextField txtAdsText;
    private JTextField txtFinishedAdvertisements;
    private JTextField txtAdsDisplayPeriod;
    private JTextField textField;
    private Client client;
    private String currentOrderName = "";
    private JTextField txtAdsNamenot;
    private JTextField textField_2;
    private JList list_1;
    private IClient remote;

    /**
     * Create the frame.
     */
    public PClient(Client client) throws RemoteException {
        this.client = client;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 622, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JList list = new JList(currentOrdersList);
        list.setFont(new Font("Tahoma", Font.PLAIN, 14));
        list.setBounds(10, 39, 354, 157);
        contentPane.add(list);

        txtYourCurrentAdvertisements = new JTextField();
        txtYourCurrentAdvertisements.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtYourCurrentAdvertisements.setText("Your current advertisements:");
        txtYourCurrentAdvertisements.setEditable(false);
        txtYourCurrentAdvertisements.setBounds(10, 10, 354, 19);
        contentPane.add(txtYourCurrentAdvertisements);
        txtYourCurrentAdvertisements.setColumns(10);

        txtAdsNamenot = new JTextField();
        txtAdsNamenot.setText("Ad's name (not displayed):");
        txtAdsNamenot.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtAdsNamenot.setEditable(false);
        txtAdsNamenot.setColumns(10);
        txtAdsNamenot.setBounds(384, 39, 192, 19);
        contentPane.add(txtAdsNamenot);

        textField_2 = new JTextField();
        textField_2.setBounds(384, 68, 192, 19);
        contentPane.add(textField_2);
        textField_2.setColumns(10);

        list_1 = new JList(finishedOrdersList);
        list_1.setBounds(10, 284, 354, 129);
        contentPane.add(list_1);

        JButton btnNewButton = new JButton("Withdraw");
        btnNewButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(list.getSelectedIndex() == -1){
                    JOptionPane.showMessageDialog(contentPane, "Select order to withdraw");

                }
                else{
                    try {
                        if(client.getiManager().withdrawOrder(currentOrders.get(list.getSelectedIndex()).getId())){
                            finishedOrdersList.addElement(currentOrdersList.get(list.getSelectedIndex()));
                            currentOrders.remove(list.getSelectedIndex());
                            currentOrdersList.remove(list.getSelectedIndex());
                            JOptionPane.showMessageDialog(contentPane, "Order was withdrawn");

                        }
                        else{
                            JOptionPane.showMessageDialog(contentPane, "Order hasn't been withdrawn. Something went wrong. :(");

                        }

                    } catch (RemoteException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnNewButton.setBounds(128, 206, 114, 35);
        contentPane.add(btnNewButton);

        txtNewAdvertisement = new JTextField();
        txtNewAdvertisement.setEditable(false);
        txtNewAdvertisement.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtNewAdvertisement.setText("New advertisement");
        txtNewAdvertisement.setBounds(384, 10, 192, 19);
        contentPane.add(txtNewAdvertisement);
        txtNewAdvertisement.setColumns(10);

        txtAdsText = new JTextField();
        txtAdsText.setText("Ad's text:");
        txtAdsText.setEditable(false);
        txtAdsText.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtAdsText.setBounds(384, 97, 192, 19);
        contentPane.add(txtAdsText);
        txtAdsText.setColumns(10);

        txtFinishedAdvertisements = new JTextField();
        txtFinishedAdvertisements.setText("Finished advertisements:");
        txtFinishedAdvertisements.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtFinishedAdvertisements.setEditable(false);
        txtFinishedAdvertisements.setColumns(10);
        txtFinishedAdvertisements.setBounds(10, 255, 354, 19);
        contentPane.add(txtFinishedAdvertisements);

        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textArea.setLineWrap(true);
        textArea.setBounds(384, 126, 192, 185);
        contentPane.add(textArea);

        txtAdsDisplayPeriod = new JTextField();
        txtAdsDisplayPeriod.setText("Ad's display period in sec:");
        txtAdsDisplayPeriod.setFont(new Font("Tahoma", Font.PLAIN, 14));
        txtAdsDisplayPeriod.setEditable(false);
        txtAdsDisplayPeriod.setColumns(10);
        txtAdsDisplayPeriod.setBounds(384, 321, 192, 19);
        contentPane.add(txtAdsDisplayPeriod);

        textField = new JTextField();
        textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        textField.setBounds(384, 350, 192, 35);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton btnPlace = new JButton("Place");
        btnPlace.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Order o = new Order();
                o.client = remote;
                o.advertText = textArea.getText();
                o.displayPeriod = Duration.ofSeconds(Integer.parseInt(textField.getText()));
                currentOrderName = textField_2.getText();
                try {
                    client.getiManager().placeOrder(o);
                    JOptionPane.showMessageDialog(contentPane, "Order placed");
                } catch (RemoteException ex) {
                    ex.printStackTrace();
                }
            }
        });
        btnPlace.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnPlace.setBounds(423, 395, 114, 35);
        contentPane.add(btnPlace);
    }

    public void addAd(int id) {
        ClientsOrder clientsOrder = new ClientsOrder(id, currentOrderName);
        currentOrders.add(clientsOrder);
        currentOrdersList.addElement(clientsOrder.getName());
        contentPane.repaint();
    }

    public void setRemote(IClient client){
        remote = client;
    }

}
