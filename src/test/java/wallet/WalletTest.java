package wallet;

import com.wallets.model.entity.Wallet;
import com.wallets.service.WalletService;
import org.junit.jupiter.api.Test;

public class WalletTest {

    public static WalletService walletService;

    void testOperations(){
         Wallet wallet = new Wallet("wallet1", 100L);

    }

}
