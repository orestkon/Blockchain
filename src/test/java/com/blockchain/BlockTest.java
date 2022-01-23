package com.blockchain;

import blockchain.Block;
import blockchain.Blockchain;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.List;


class BlockTest {
    List<Block> blockchain = Blockchain.blockchain;
    int prefix = 4;
    String prefixString = new String(new char[prefix]).replace('\0', '0');

    @Test
    public void givenBlockchain_whenNewBlockAdded_thenSuccess() {
        Block newBlock = new Block(
                "The is a New Block.",
                blockchain.get(blockchain.size() - 1).getHash(),
                new Date().getTime());
        newBlock.mine();
        assertEquals(prefixString, newBlock.getHash().substring(0, prefix));
        blockchain.add(newBlock);
    }

    @Test
    public void givenBlockchain_whenValidated_thenSuccess() {
        for (Block block : blockchain) {
            System.out.println(block.getHash());
            if (block != Blockchain.genesis) {
                assertTrue(block.validate());
            }
        }
    }

}