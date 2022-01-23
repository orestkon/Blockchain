package blockchain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Block {
    private String hash;
    private final String previousHash;
    private final String data;
    private final long timeStamp;
    private int nonce;
    static final int PREFIX_LENGTH = 4;
    static final String PREFIX = new String(new char[PREFIX_LENGTH]).replace('\0', '0');

    public Block(String data, String previousHash, long timeStamp) {
        this.data = data;
        this.previousHash = previousHash;
        this.timeStamp = timeStamp;
        this.hash = calculateBlockHash();
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public String getHash() {
        return hash;
    }

    public String calculateBlockHash() {
        String dataToHash = previousHash
                + timeStamp
                + nonce
                + data;
        MessageDigest hashAlgo;
        byte[] bytes = null;
        try {
            hashAlgo = MessageDigest.getInstance("SHA-256");
            bytes = hashAlgo.digest(dataToHash.getBytes(UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            /* TODO: Implement logging */
        }
        StringBuilder buffer = new StringBuilder();
        if (bytes != null) {
            for (byte b : bytes) {
                buffer.append(String.format("%02x", b));
            }
        }
        return buffer.toString();
    }

    public void mine() {
        while (!this.validate()) {
            nonce++;
            this.hash = calculateBlockHash();
        }
    }

    public Block getParent() {
        for (Block block : Blockchain.blockchain) {
            if (this.getPreviousHash().equals(block.getHash())) {
                return block;
            }
        }
        return null;
    }

    public boolean validate(){
        return this.getHash().equals(this.calculateBlockHash())
                    && this.previousHash.equals(this.getParent().getHash())
                    && this.getHash().substring(0, PREFIX_LENGTH).equals(PREFIX);
    }
}
