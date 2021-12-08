import java.util.Scanner;

public class Main {

    static boolean flag = true;
    static int accountBalance = 0;
    static int rows = 0, columns = 2;
    static String[][] transactions;

    public static void main(String[] args) {
        runApplication();
    }

    public static void runApplication(){
        while(flag){
            printMenu();
            switch (chooseOption()){
                case 1: addTransaction();
                    break;
                case 2: deleteTransaction();
                    break;
                case 3: showBalance();
                    break;
                case 4: showAllTransactions();
                    break;
                case 5: showTransactionByCategory();
                    break;
                case 0: flag = false;
                    break;
                default:
                    System.out.println("Выбрана невалидная опция, попробуйте ещё раз");

            }
        }
    }
    public static void printMenu(){
        System.out.println("1. Добавить трату/пополнение\n" +
                "2. Удалить трату/пополнение\n" +
                "3. Узнать текущий счёт\n" +
                "4. Вывести все траты/пополнения\n" +
                "5. Вывести траты/пополнения по определенной категории\n" +
                "0. Выход");
    }

    public static int chooseOption(){
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int option=-1;
        if(!input.isEmpty() && input.length() ==1) option = Integer.parseInt(input);
        return option;
    }

    public static void addTransaction(){
        String[][] transaction = getUserInput("Введите категорию и сумму: ");

        if (checkAmount(transaction)) {
            accountBalance += Integer.parseInt(transaction[0][1]);
            rows++;
            String[][] newTransactionsArray = new String[rows][columns];
            if (rows != 1) {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < columns; j++) {
                        if (i == rows - 1) newTransactionsArray[i][j] = transaction[0][j];
                        else newTransactionsArray[i][j] = transactions[i][j];
                    }
                }
                //System.arraycopy(newTransactionsArray, 0, transactions, 0, newTransactionsArray.length);
                transactions = newTransactionsArray;

            } else transactions = transaction;
        }
    }

    public static boolean checkAmount(String[][]transaction){
        int amount = Integer.parseInt(transaction[0][1]);
        if((accountBalance>=0 && (amount + accountBalance >=0)) || (accountBalance<0 && amount >0)) return true;
        else {
            System.out.println("Операция не может быть выполнена - недостаточно средств на счёте");
            return false;
        }
    }

    //TODO протестировать
    public static void deleteTransaction(){
        int transactionNumber = getLineForDeletion();
        if (transactionNumber >0){
            accountBalance -=Integer.parseInt(transactions[transactionNumber-1][1]);
            transactions[transactionNumber-1][0] = null;
            transactions[transactionNumber-1][1] = null;
        }
        else System.out.println("Операция не может быть выполнена - под данным номером запись отсутствует");
    }

    public static void showBalance(){
        System.out.println("Ваш текущий счёт: "+ accountBalance);
    }

    public static void showAllTransactions(){
        for (int i = 0; i < rows; i++) {
            if(transactions[i][0] != null) System.out.println(i + 1 + ". " + transactions[i][0] + " " + transactions[i][1]);
        }
    }

    public static void showTransactionByCategory() {
        String category = getCategory();
        if(categoryExist(category)){
            int increment = 1;
            for(String [] transaction : transactions){
                if (transaction[0] != null && transaction[0].equals(category)) System.out.println(increment++ + ". " + " " + transaction[0] + " " + transaction[1]);;
            }
        }
        else System.out.println("Данная категория трат/пополнений отсутствует");
    }

    public static boolean categoryExist(String category) {
        boolean flag = false;
        for(String [] transaction : transactions){
            if (transaction[0] != null && transaction[0].equals(category)) {flag = true; break;}
        }
        return flag;
    }

    public static String getCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите название категории трат/пополнений: ");
        return scanner.nextLine();
    }

    public static String[][] getUserInput(String description){
        Scanner scanner = new Scanner(System.in);
        System.out.print(description);
        String input = scanner.nextLine();
        if(!input.isEmpty()){
            String [] out = input.split(" ");
            return new String[][]{{out[0], out[1]}};
        }
        else return null;
    }

    public static int getLineForDeletion() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер траты/пополнения, которую необходимо удалить: ");
        String input = scanner.nextLine();
        int result = -1;
        if(!input.isEmpty()) {
            int num = Integer.parseInt(input);
            if (num <= transactions.length) result = num;
        }
        return result;
    }
}
