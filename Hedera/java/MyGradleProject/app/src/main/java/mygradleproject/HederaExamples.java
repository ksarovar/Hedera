import com.hedera.hashgraph.sdk.*;

import io.github.cdimascio.dotenv.Dotenv;

import java.util.concurrent.TimeoutException;

public class HederaExamples {

    public static void main(String[] args) {
        try {
            // Grab your Hedera testnet account ID and private key
            AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
            PrivateKey myPrivateKey = PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

            // Create your connection to the Hedera network
            Client client = Client.forTestnet();

            // Set your account as the client's operator
            client.setOperator(myAccountId, myPrivateKey);

            // Set default max transaction fee & max query payment
            client.setMaxTransactionFee(new Hbar(100));
            client.setMaxQueryPayment(new Hbar(50));

            // Generate a new key pair
            PrivateKey newAccountPrivateKey = PrivateKey.generateEd25519();
            PublicKey newAccountPublicKey = newAccountPrivateKey.getPublicKey();

            // Create a new account and assign the public key
            TransactionResponse newAccountResponse = new AccountCreateTransaction()
                    .setKey(newAccountPublicKey)
                    .setInitialBalance(Hbar.fromTinybars(1000))
                    .execute(client);

            // Get the new account ID from the transaction response
            AccountId newAccountId = newAccountResponse.getReceipt(client).accountId;

            System.out.println("\nNew account ID: " + newAccountId);

            // Check the new account's balance
            AccountBalance accountBalance = new AccountBalanceQuery()
                    .setAccountId(newAccountId)
                    .execute(client);

            // Transfer HBAR
            TransactionResponse sendHbarResponse = new TransferTransaction()
                    .addHbarTransfer(myAccountId, Hbar.fromTinybars(-1000))
                    .addHbarTransfer(newAccountId, Hbar.fromTinybars(1000))
                    .execute(client);

            System.out.println("The transfer transaction status: " + sendHbarResponse.getReceipt(client).status);

            // Request the cost of the query
            Hbar queryCost = new AccountBalanceQuery()
                    .setAccountId(newAccountId)
                    .getCost(client);

            System.out.println("The cost of this query: " + queryCost);

            // Check the new account's balance again
            AccountBalance accountBalanceNew = new AccountBalanceQuery()
                    .setAccountId(newAccountId)
                    .execute(client);

            System.out.println("The new account balance: " + accountBalanceNew.hbars);

        } catch (TimeoutException | HederaPreCheckStatusException | HederaReceiptStatusException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
