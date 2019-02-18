package rocks.zipcode.atm.bank;

import rocks.zipcode.atm.ActionResult;
import rocks.zipcode.atm.CashMachineApp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ZipCodeWilmington
 */
public class Bank {

    private Map<Integer, Account> accounts = new HashMap<>();

    public Bank() {
        accounts.put(1000, new BasicAccount(new AccountData(
                1000, "Example 1", "example1@gmail.com", 500
        )));

        accounts.put(2000, new PremiumAccount(new AccountData(
                2000, "Example 2", "example2@gmail.com", 200
        )));

        accounts.put(3000, new PremiumAccount(new AccountData(
                3000, "Example 3", "example3@gmail.com", 1000
        )));

        accounts.put(4000, new PremiumAccount(new AccountData(
                4000, "Example 4", "example4@gmail.com", 10000
        )));

        accounts.put(5000, new BasicAccount(new AccountData(
                5000, "Example 5", "example5@gmail.com", 2500
        )));

    }

    public ActionResult<AccountData> getAccountById(int id) {
        Account account = accounts.get(id);

        if (account != null) {
            CashMachineApp.btnDeposit.setDisable(false);
            CashMachineApp.btnWithdraw.setDisable(false);
            CashMachineApp.btnExit.setDisable(false);
            return ActionResult.success(account.getAccountData());
        } else {
            return ActionResult.fail("No account with id: " + id + "\nPlease login with an existing account id.");
        }
    }

    public ActionResult<AccountData> deposit(AccountData accountData, double amount) {
        Account account = accounts.get(accountData.getId());
        account.deposit(amount);

        return ActionResult.success(account.getAccountData());
    }

    public ActionResult<AccountData> withdraw(AccountData accountData, double amount) {
        Account account = accounts.get(accountData.getId());
        boolean ok = account.withdraw(amount);

        if (ok) {
            return ActionResult.success(account.getAccountData());
        } else {

            return ActionResult.fail("Withdraw failed: " + amount + ". Account has: " + account.getBalance());
        }
    }
}
