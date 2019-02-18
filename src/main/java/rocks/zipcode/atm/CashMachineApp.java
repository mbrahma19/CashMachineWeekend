package rocks.zipcode.atm;

import javafx.scene.control.Label;
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
    public static Button btnExit = new Button("Exit Account");
    public static Button btnNewAccount = new Button("Start a New Account");

    public TextArea areaInfo = new TextArea();

    private Stage newStage = new Stage();

    private CashMachine cashMachine = new CashMachine(new Bank());


    private GridPane createGrid(){
        GridPane grid = new GridPane();
        grid.setPrefSize(600, 600);
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(40);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(40);
        grid.getColumnConstraints().addAll(column1, column2);

        return grid;
    }

    private Parent createContent2() {
        GridPane grid = createGrid();


        grid.add(field, 0, 0, 2, 1);
        grid.add(btnSubmit, 0, 1, 2, 1);
        grid.add(deposit, 0, 2, 2, 1);
        grid.add(btnDeposit, 0, 3);
        grid.add(btnWithdraw, 1, 3);
        grid.add(areaInfo, 0, 4, 2, 1);
        grid.add(btnExit, 0, 6);
        grid.add(btnNewAccount,0,8);

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

            areaInfo.setText(cashMachine.withdrawToString(amount));
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

        btnNewAccount.setOnAction(e -> {
            Scene newScene = new Scene(createNewAccountContent());
            newStage.setScene(newScene);
            newStage.show();

        });
        return grid;
    }


    private Parent createNewAccountContent() {

        GridPane newAccGrid = createGrid();

        Label btnSetID = new Label("New Account ID");
        Label btnSetName = new Label("Specify Account Name");
        Label btnSetEmail = new Label("Specifiy Email");
        Label btnSetBalance= new Label("Specify Starting Balance");
        Label btnSetType= new Label("Specify the Type of Account to Open");

        Button btnSubmit = new Button("Submit Information");

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField balanceField = new TextField();
        TextField typeField = new TextField();

        newAccGrid.add(btnSetID, 0, 0);
        newAccGrid.add(idField, 0,1, 1,1);
        newAccGrid.add(btnSetName, 0, 2);
        newAccGrid.add(nameField, 0,3, 1,1);
        newAccGrid.add(btnSetEmail, 0, 4);
        newAccGrid.add(emailField, 0,5, 1,1);
        newAccGrid.add(btnSetBalance, 0, 6);
        newAccGrid.add(balanceField, 0,7, 1,1);
        newAccGrid.add(btnSetType, 0, 8);
        newAccGrid.add(typeField, 0,9, 1,1);
        newAccGrid.add(btnSubmit,0,10,2,1);

        btnSubmit.setOnAction(e -> {
            int id = Integer.parseInt(idField.getText());
            String name = nameField.getText();
            String email = emailField.getText();
            double balance = Double.parseDouble(balanceField.getText());
            String type = typeField.getText();
            cashMachine.newAccount(id,name,email,balance,type);
            newStage.close();
        });

        return newAccGrid;
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
