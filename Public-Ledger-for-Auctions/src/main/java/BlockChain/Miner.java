package BlockChain;

import java.security.KeyPair;
import java.security.PublicKey;

public class Miner {
    private double reward;

    /*
     * Mines pending transactions and adds a new block to the blockchain.
     *
     * @param minerPublicKey The public key of the miner receiving the reward.
     */
    /*
    public void minePendingTransactions(PublicKey minerPublicKey) {
        Block block = new Block(chain.size(), getLastBlock().getHash(), pendingTransactions);
        block.mineBlock(difficulty);
        chain.add(block);
        pendingTransactions.clear();
        KeyPair rewardKeyPair = Transaction.generateKeyPair();
        Transaction transaction = new Transaction(rewardKeyPair.getPublic(), minerPublicKey, 10);
        transaction.signTransaction(rewardKeyPair.getPrivate());
        addTransaction(transaction);
    }*/

    /**
     * Mines the block with the given difficulty.
     *
     * @param b The most recent block in the chain.
     */
    public boolean PoW(Block b) {
        String target = new String(new char[Constants.DIFICULTY]).replace('\0', '0');
        String hash = b.getHash();

        return !hash.substring(0, Constants.DIFICULTY).equals(target);
    }

    public Block mine(Block b ,Blockchain blockchain ){
        while (PoW(b)){
            b.incrementNonce();
            b.calculateHash();
        }

        //blockchain.addBlock(b);

        reward += Constants.MINER_REWARD;

        return b;
    }

    public double getReward(){
        return reward;
    }


}
