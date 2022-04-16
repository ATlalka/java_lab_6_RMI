package Manager.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocketFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.RMISocketFactory;
import java.security.*;
import java.security.cert.CertificateException;

public class RMISSLServerSocketFactory extends RMISocketFactory {

    private SSLServerSocketFactory ssf = null;
    private static final String keyStore = "src/main/resources/testkeys.jks";
    private int serverPort;
    private int clientPort;



    public RMISSLServerSocketFactory(int serverPort, int clientPort) {
        this.clientPort=clientPort;
        this.serverPort=serverPort;
        initServerSocketFactory();
    }

    public RMISSLServerSocketFactory(){
        new RMISSLServerSocketFactory(3000, 3100);
    }

    private void initServerSocketFactory(){
        try{
            SSLContext ctx;
            KeyManagerFactory kmf;
            KeyStore ks;

            char[] passphrase = "pass123".toCharArray();
            ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream(keyStore), passphrase);

            kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, passphrase);

            ctx = SSLContext.getInstance("TLS");
            ctx.init(kmf.getKeyManagers(), null, null);

            ssf = ctx.getServerSocketFactory();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        return factory.createSocket(host, port == 0 ? clientPort-- : port);
    }

    @Override
    public ServerSocket createServerSocket(int port) throws IOException {
        if (ssf == null)
            initServerSocketFactory();
        return ssf.createServerSocket(port == 0 ? serverPort-- : port);

    }

    public int hashCode(){
        return getClass().hashCode();
    }

    public boolean equals(Object obj){
        if (obj == this){
            return true;
        }

        else if (obj==null || getClass() != obj.getClass()){
            return false;
        }

        return true;
    }
}
