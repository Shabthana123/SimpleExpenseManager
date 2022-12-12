package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.Constants;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

public class SQLiteTransactionDAO implements TransactionDAO {

    SQLiteDatabase database;
    java.io.File filename = Constants.CONTEXT.getFilesDir();

    public SQLiteTransactionDAO() {
        database = SQLiteDatabase.openOrCreateDatabase(filename.getAbsolutePath() + "/200276D.sqlite", null);
        // db.execSQL("CREATE TABLE IF NOT EXISTS Trans(accountNo VARCHAR(50),expenseType VARCHAR(50));");
        database.execSQL("CREATE TABLE IF NOT EXISTS Transactions(accountNo VARCHAR(50),expenseType VARCHAR(50),amount NUMERIC(10,2), date_value Date);");
    }


    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {
        database.execSQL("INSERT INTO Transactions VALUES('"+accountNo+"','"+((expenseType==ExpenseType.INCOME)?"INCOME":"EXPENSE")+"','"+amount+"','"+date.toString()+"');");
    }

    @Override
    public List<Transaction> getAllTransactionLogs() {
        Cursor setOfResult = database.rawQuery("Select * from Transactions",null);
        setOfResult.moveToFirst();
        List<Transaction> result = new ArrayList<Transaction>();
        while(!setOfResult.isAfterLast())
        {
            result.add( new Transaction(new Date(setOfResult.getString(3)),setOfResult.getString(0),((setOfResult.getString(1)=="INCOME")?ExpenseType.INCOME:ExpenseType.EXPENSE), Double.parseDouble(setOfResult.getString(2) ) ));
            setOfResult.moveToNext();
        }
        return result;
    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        Cursor setOfResult = database.rawQuery("Select * from Transactions ORDER BY date_value LIMIT "+limit,null);
        setOfResult.moveToFirst();
        List<Transaction> result = new ArrayList<Transaction>();
        while(!setOfResult.isAfterLast())
        {
            result.add( new Transaction(new Date(setOfResult.getString(3)),setOfResult.getString(0),((setOfResult.getString(1)=="INCOME")?ExpenseType.INCOME:ExpenseType.EXPENSE), Double.parseDouble(setOfResult.getString(2) ) ));
            setOfResult.moveToNext();
        }
        return result;
    }
}