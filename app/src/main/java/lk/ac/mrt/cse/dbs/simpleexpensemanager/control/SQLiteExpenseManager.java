package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.control.exception.ExpenseManagerException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SQLiteAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SQLiteTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;

public class SQLiteExpenseManager extends ExpenseManager{
    /*** Start ***/
    public SQLiteExpenseManager() throws ExpenseManagerException {
        setup();
    }

    @Override
    public void setup() throws ExpenseManagerException {

        AccountDAO sqliteMemoryAccountDAO = new SQLiteAccountDAO();
        setAccountsDAO(sqliteMemoryAccountDAO);

        TransactionDAO sqliteTransactionDAO = new SQLiteTransactionDAO();
        setTransactionsDAO(sqliteTransactionDAO);



        // test data
        Account testAcct1 = new Account("723412", "Celon Bank", "Aswin Kumar", 11000.0);
        Account testAcct2 = new Account("129834", "hatton national bank", "Vinson Robert", 70000.0);
        getAccountsDAO().addAccount(testAcct1);
        getAccountsDAO().addAccount(testAcct2);

        /*** End ***/
    }
}
