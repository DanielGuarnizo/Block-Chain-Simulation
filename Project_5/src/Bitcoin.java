import java.util.concurrent.ConcurrentHashMap;


public class Bitcoin {

    static ConcurrentHashMap<String, Blockchain> blockchains = new ConcurrentHashMap<>(); // this hash is thread safe made to multithreading
        /* the blockchain field represent a mapping from strings names (the names of the nodes that could be agents or miners)
        *  to blockchains, each node has it own personal blockchain, this is done to take in to account all the possible blockchains
        * that could exist and then decided in the future which to choose
        */

    static ConcurrentHashMap<String, Node> nodes = new ConcurrentHashMap<>();
    /* this fields is a mapping from string (the names of the nodes) to the nodes objects, this is done to track
    * how may objects nodes there is the network and it will help in the random process of choose which nodes is a sender and receiver
    * */
    static int overallBalance = 0; // to track the overall number of bitcoins
}


