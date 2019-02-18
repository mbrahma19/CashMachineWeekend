package rocks.zipcode.atm;

import javafx.scene.layout.ColumnConstraints;
import rocks.zipcode.atm.bank.Bank;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.Pos;

/**
 * @author ZipCodeWilmington
 */
public class CashMachineApp extends Application {

    private TextField field = new TextField();
    private TextField deposit = new TextField();

    public static Button btnSubmit = new Button("Login with Account ID");
    public static Button btnDeposit = new Button("Deposit");
    public static Button btnWithdraw = new Button("Withdraw");
    public static Button btnExit = new Button("Exit");

    private CashMachine cashMachine = new CashMachine(new Bank());


    private Parent createContent2() {
        GridPane grid = new GridPane();
        grid.setPrefSize(600, 600);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        TextArea areaInfo = new TextArea();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40);
        grid.getColumnConstraints().addAll(column1, column2);


        grid.add(field, 0, 0, 2, 1);
        grid.add(btnSubmit, 0, 1, 2, 1);
        grid.add(deposit, 0, 2, 2, 1);
        grid.add(btnDeposit, 0, 3);
        grid.add(btnWithdraw, 1, 3);
        grid.add(areaInfo, 0, 4, 2, 1);
        grid.add(btnExit, 1, 6);

        btnDeposit.setDisable(true);
        btnWithdraw.setDisable(true);
        btnExit.setDisable(true);

        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(field.getText());
            cashMachine.login(id);
            areaInfo.setText(cashMachine.toString());

        });

        btnDeposit.setOnAction(e -> {
            double amount = Double.parseDouble(deposit.getText());
            cashMachine.deposit(amount);
            areaInfo.setText(cashMachine.toString());
        });

        btnWithdraw.setOnAction(e -> {
            double amount = Double.parseDouble(deposit.getText());
            cashMachine.withdraw(amount);

            areaInfo.setText(cashMachine.withdrawToString());
        });

        btnExit.setOnAction(e -> {
            cashMachine.exit();
            field.clear();
            deposit.clear();

            areaInfo.setText(cashMachine.exitString());

            btnDeposit.setDisable(true);
            btnWithdraw.setDisable(true);
            btnExit.setDisable(true);
        });
        return grid;
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent2()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
