package rocks.zipcode.atm;

import rocks.zipcode.atm.bank.AccountData;
import rocks.zipcode.atm.bank.Bank;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author ZipCodeWilmington
 */
public class CashMachine {

    private final Bank bank;
    private AccountData accountData = null;

    public CashMachine(Bank bank) {
        this.bank = bank;
    }

    private Consumer<AccountData> update = data -> {
        accountData = data;
    };

    public void login(int id) {
        tryCall(
                () -> bank.getAccountById(id),
                update
        );
    }

    public void deposit(double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.deposit(accountData, amount),
                    update
            );
        }
    }

    public void withdraw(double amount) {
        if (accountData != null) {
            tryCall(
                    () -> bank.withdraw(accountData, amount),
                    update
            );
        }
    }

    public void exit() {
        if (accountData != null) {

            accountData = null;
        }
    }

    public void newAccount(int id, String name, String email, double balance, String type){
            tryCall(
                    () ->
                            bank.newAccount(id,name,email,balance,type),
                    update
            );
    }

    @Override
    public String toString() {
        return accountData != null ? accountData.toString() : "Please try logging in with an existing account id.";
    }

    public String withdrawToString(Double amount) {
        if(accountData != null){
            if(accountData.getBalance() > amount){
                return accountData.toString();
            } else {

                return "Your withdrawal amount exceeds your current balance.\nCurrent balance is " + String.format("%.2f",accountData.getBalance());
            }
        }
        else {return "Please try logging in with an existing account id.";}
    }

    public String exitString() {
        return "Transactions Completed";
    }

    private <T> void tryCall(Supplier<ActionResult<T> > action, Consumer<T> postAction) {
        try {
            ActionResult<T> result = action.get();
            if (result.isSuccess()) {
                T data = result.getData();
                postAction.accept(data);
            } else {
                String errorMessage = result.getErrorMessage();
                throw new RuntimeException(errorMessage);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
