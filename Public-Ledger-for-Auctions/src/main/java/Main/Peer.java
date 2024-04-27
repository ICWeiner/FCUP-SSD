package Main;

import Kademlia.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Peer {

    private static final Logger logger = Logger.getLogger(Peer.class.getName());
    String ip;
    int port;


    Peer(String ip, int port){
       this.ip = ip;
       this.port = port;
    }


    public static void main(String[] args) { //TODO colocar mais do que um bootstrap node
        if (args.length < 1 || args.length > 2) {
            System.err.println("Error: Wrong number of arguments. Usage: java Main.java <port> [BootstrapNodeIps]");
            System.exit(1);
        }

        Peer myself = new Peer(Utils.getAddress(), Integer.parseInt(args[0]));
        System.out.println(myself);

        try {

            //System.setProperty("filename", port + ".log");
            Node node = new Node(new NodeInfo(myself.ip, myself.port));
            Kademlia kademlia = new Kademlia();

            if (args.length == 2) { // Node TODO code inside this if statement should probably be removed
                String bootstrapIp = args[1];


                /*
                NodeInfo bootstrapNodeInfo = new NodeInfo(bootstrapIp, myself.port);
                node.updateRoutingTable(bootstrapNodeInfo);
                kademlia.joinNetwork(node, bootstrapNodeInfo);
                Scanner in = new Scanner(System.in);
                System.out.println("Store? 'y' or 'n'");
                String n = in.nextLine();
                if(n.equals("y")) {
                    //mudar id e value
                    //kademlia.store(node, "b9b98f4de460b9da7db4547e4b2ca68d84b97a17", "gaaaaaas");
                    kademlia.store(node, "174be1b78723cf02995324f3985134a4227c920d", "gaaaaaas");
                }*/
            }

            try {
                new Thread(new Server(myself.port, node)).start();
                logger.log(Level.FINE,"Kademlia server running!");
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error starting the server", e);
            }
            //TODO join network automatically somewhere on this file, and launch client aftewards
            try {
                new Thread(new PeerMainMenu()).start();
                logger.log(Level.FINE,"Kademlia client running!");
            }catch (Exception e){
                logger.log(Level.SEVERE, "Error starting the client", e);
            }

        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Invalid port number", e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return  "----------------------------------" + '\n' +
                "Peer Info " + '\n' +
                "ip = " + ip + '\n' +
                "port = " + port + '\n' +
                "----------------------------------";
    }
}
