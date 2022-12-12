package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.*;

import java.util.ArrayList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;

public class SQLiteAccountDAO implements AccountDAO {
    SQLiteDatabase database;
    java.io.File filename = Constants.CONTEXT.getFilesDir();
    public SQLiteAccountDAO(){
        database = SQLiteDatabase.openOrCreateDatabase(filename.getAbsolutePath() + "/200276D.sqlite", null);
        database.execSQL("CREATE TABLE IF NOT EXISTS Account(accountNo VARCHAR(50),bankName VARCHAR(50),accountHolderName VARCHAR(50), balance NUMERIC(10,2));");
    }

    @Override
    public List<String> getAccountNumbersList() {
        Cursor setOfResult = database.rawQuery("Select accountNo from Account",null);
        List<String> result = new ArrayList<String>();
        setOfResult.moveToFirst();
        while(!setOfResult.isAfterLast())
        {
            result.add(setOfResult.getString(0));
            setOfResult.moveToNext();
        }
        return result;
    }

    @Override
    public List<Account> getAccountsList() {
        Cursor setOfResult = database.rawQuery("Select * from Account;",null);
        List<Account> result = new ArrayList<Account>();
        setOfResult.moveToFirst();
        while(!setOfResult.isAfterLast())
        {

            result.add( new Account(setOfResult.getString(0),setOfResult.getString(1),setOfResult.getString(2), Double.parseDouble(setOfResult.getString(3) ) ));
            setOfResult.moveToNext();
        }
        return result;
    }

    @Override
    public Account getAccount(String accountNo) throws InvalidAccountException {
        Cursor setOfResult = database.rawQuery("Select * from Account where accountNo='" + accountNo+"';", null);
        setOfResult.moveToFirst();
        if (setOfResult.isAfterLast()) {
            throw new InvalidAccountException("Account No:" + accountNo + " is not valid!");
        }
        return new Account(setOfResult.getString(0), setOfResult.getString(1), setOfResult.getString(2), Double.parseDouble(setOfResult.getString(3)));
    }

    @Override
    public void addAccount(Account account) {
        database.execSQL("INSERT INTO Account VALUES('"+account.getAccountNo()+"','"+account.getBankName()+"','"+account.getAccountHolderName()+"','"+account.getBalance()+"');");

    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        database.execSQL("DELETE FROM Account WHERE accountNo='"+accountNo+"';");

    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account acc = getAccount(accountNo);

        double updatedBalance = acc.getBalance();
        if (ExpenseType.INCOME == expenseType) {
            updatedBalance += amount;
        } else
            updatedBalance-=amount;
        database.execSQL("UPDATE Account SET balance='"+updatedBalance+"' WHERE accountNo='"+accountNo+"';");
    }

}