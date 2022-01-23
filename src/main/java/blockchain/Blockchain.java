package blockchain;

import java.util.*;

public class Blockchain {
    public static Block genesis = new Block(
            "The is the genesis Block.",
            "0",
            new Date().getTime());
    public static List<Block> blockchain = new ArrayList<>(Collections.singletonList(genesis));

    public static void main(String[] args) {

    }

}
