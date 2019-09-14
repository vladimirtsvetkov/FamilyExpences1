package com.example.familyexpences.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.familyexpences.Constants.Constants;
import com.example.familyexpences.DTOs.Category;
import com.example.familyexpences.DTOs.Expense;
import com.example.familyexpences.DTOs.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 13;
    public static final String DB_NAME = "FamilyExpenses.db";

   /* public static final String TABLE_USERS = "user";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_FAMILIES = "families";
    public static final String TABLE_EXPENSES = "expenses";
    public static final String TABLE_WAITING_FOR_APPROVAL = "waitingForApproval";

    public static final String TABLE_USERS_ID = "id";
    public static final String TABLE_USERS_USERNAME = "username";
    public static final String TABLE_USERS_PASSWORD = "password";
    public static final String TABLE_USERS_NAME = "name";
    public static final String TABLE_USERS_FAMILY_ID = "FamilyID";

    public static final String TABLE_FAMILIES_ID = "id";
    public static final String TABLE_FAMILIES_NAME = "name";
    public static final String TABLE_FAMILIES_ADMIN_ID = "admin_id";

    public static final String TABLE_CATEGORIES_ID = "id";
    public static final String TABLE_CATEGORIES_NAME = "name";

    public static final String TABLE_EXPENSES_ID = "id";
    public static final String TABLE_EXPENSES_CATEGORY_ID = "category_id";
    public static final String TABLE_EXPENSES_EXPENSE_NAME = "expense_name";
    public static final String TABLE_EXPENSES_USER_ID = "user_id";
    public static final String TABLE_EXPENSES_DATE_OF_ADDING = "date_of_adding";
    public static final String TABLE_EXPENSES_DESCRIPTION = "description";
    public static final String TABLE_EXPENSES_PRICE = "price";

    public static final String TABLE_WAITING_FOR_APPROVAL_REQUEST_ID = "request_id";
    public static final String TABLE_WAITING_FOR_APPROVAL_FAMILY_ID = "family_id";
    public static final String TABLE_WAITING_FOR_APPROVAL_USER_ID = "user_id";



    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "('" + TABLE_USERS_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_USERS_USERNAME + "' varchar(50) NOT NULL UNIQUE," +
            "'" + TABLE_USERS_PASSWORD + "' varchar(50) NOT NULL," +
            "'" + TABLE_USERS_FAMILY_ID + "' INTEGER(11)," +
            "'" + TABLE_USERS_NAME + "' varchar(50) NOT NULL," +
            " FOREIGN KEY ('" + TABLE_FAMILIES_ID + "') REFERENCES " + TABLE_FAMILIES +
            "('" + TABLE_FAMILIES_ID + "'))";


    public static final String CREATE_TABLE_CATEGORIES = "CREATE TABLE " + TABLE_CATEGORIES
            + "('" + TABLE_CATEGORIES_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_CATEGORIES_NAME + "' VARCHAR(50) NOT NULL)";


    public static final String CREATE_TABLE_FAMILIES = "CREATE TABLE " + TABLE_FAMILIES
            + "('" + TABLE_FAMILIES_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_FAMILIES_NAME + "' varchar(50) NOT NULL," +
            "'" + TABLE_FAMILIES_ADMIN_ID + "' INTEGER(11) NOT NULL," +
            " FOREIGN KEY ('" + TABLE_FAMILIES_ADMIN_ID + "') REFERENCES " + TABLE_USERS +
            " ('" + TABLE_USERS_ID + "'))";


    public static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES
            + "('" + TABLE_EXPENSES_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_EXPENSES_CATEGORY_ID + "' INTEGER(11) NOT NULL," +
            "'" + TABLE_EXPENSES_USER_ID + "' INTEGER(11) NOT NULL," +
            "'" + TABLE_EXPENSES_DATE_OF_ADDING + "' Datetime NOT NULL," +
            "'" + TABLE_EXPENSES_DESCRIPTION + "' VARCHAR(200) NOT NULL," +
            "'" + TABLE_EXPENSES_PRICE + "' decimal(6,4) NOT NULL," +
            " FOREIGN KEY ('" + TABLE_EXPENSES_CATEGORY_ID + "') REFERENCES " + TABLE_CATEGORIES + "('" + TABLE_CATEGORIES_ID + "')," +
            " FOREIGN KEY ('" + TABLE_EXPENSES_USER_ID + "') REFERENCES " + TABLE_USERS + "('" + TABLE_USERS_ID + "'))";


    public static final String CREATE_TABLE_WAITING_FOR_APPROVAL = "CREATE TABLE " + TABLE_WAITING_FOR_APPROVAL
            + "('" + TABLE_WAITING_FOR_APPROVAL_REQUEST_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_WAITING_FOR_APPROVAL_FAMILY_ID + "' INTEGER(11) NOT NULL," +
            "'" + TABLE_WAITING_FOR_APPROVAL_USER_ID + "' INTEGER(11) NOT NULL," +
            " FOREIGN KEY ('" + TABLE_WAITING_FOR_APPROVAL_FAMILY_ID + "') REFERENCES " + TABLE_FAMILIES + "('" + TABLE_FAMILIES_ID + "')," +
            " FOREIGN KEY ('" + TABLE_WAITING_FOR_APPROVAL_USER_ID + "') REFERENCES " + TABLE_USERS + "('" + TABLE_USERS_ID + "'))";*/


    public static final String MYERROR = "MYERROR";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Constants.CREATE_TABLE_CATEGORIES);
        db.execSQL(Constants.CREATE_TABLE_USERS);
        db.execSQL(Constants.CREATE_TABLE_FAMILIES);
        db.execSQL(Constants.CREATE_TABLE_EXPENSES);
        db.execSQL(Constants.CREATE_TABLE_REQUESTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_FAMILIES);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " +  Constants.TABLE_REQUESTS);
        onCreate(db);
    }

    public int getUserId(String username) {
        SQLiteDatabase db = null;
        int userId = 0;
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + Constants.TABLE_USERS_ID + " FROM " + Constants.TABLE_USERS +
                    " WHERE " + Constants.TABLE_USERS_USERNAME + " = '" + username + "'";
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                userId = c.getInt(c.getColumnIndex(Constants.TABLE_USERS_ID));
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return userId;
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();

            String sql = "SELECT * FROM " + Constants.TABLE_USERS
                    + " WHERE " + Constants.TABLE_USERS_USERNAME + " = '" + username + "'" +
                    " AND " + Constants.TABLE_USERS_PASSWORD + " = '" + password + "'";

            Cursor c = db.rawQuery(sql, null);

            return c.moveToFirst();

        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }
        return false;
    }

    public boolean insertUser(User user) {
        SQLiteDatabase db = null;

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_USERS_USERNAME, user.getUsername());
            cv.put(Constants.TABLE_USERS_PASSWORD, user.getPassword());
            cv.put(Constants.TABLE_USERS_NAME, user.getName());

            long id = db.insert(Constants.TABLE_USERS, null, cv);
            insertFamily(user.getName(), (int) id);
            if (id != -1)
                return true;

        } catch (SQLException e) {
            Log.wtf(MYERROR, e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

        return false;
    }

        public boolean insertFamily(String FamilyName, int AdminID) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_FAMILIES_NAME, FamilyName);
            cv.put(Constants.TABLE_FAMILIES_ADMIN_ID, AdminID);

            long id = db.insert(Constants.TABLE_FAMILIES, null, cv);
            if (id != -1)
                return true;

        } catch (SQLException e) {
            Log.wtf(MYERROR, e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

        return false;
    }



    public List<String> getUsers() {
        SQLiteDatabase db = null;
        List<String> usernames = new ArrayList<String>();
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + Constants.TABLE_USERS_USERNAME + " FROM " + Constants.TABLE_USERS;
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                String username = c.getString(c.getColumnIndex(Constants.TABLE_USERS_USERNAME));
                usernames.add(username);
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return usernames;
    }

    public List<Category> getCategories() {
        SQLiteDatabase db = null;
        List<Category> categories = new ArrayList<Category>();
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + Constants.TABLE_CATEGORIES_ID + ", " + Constants.TABLE_CATEGORIES_NAME + " FROM " + Constants.TABLE_CATEGORIES;
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                int id = Integer.parseInt(c.getString(c.getColumnIndex(Constants.TABLE_CATEGORIES_ID)));
                String name = c.getString(c.getColumnIndex(Constants.TABLE_CATEGORIES_NAME));
                categories.add(new Category(id, name));
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return categories;
    }

    public boolean addCategory(String name) {
        SQLiteDatabase db = null;

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_CATEGORIES_NAME, name);

            long id = db.insert(Constants.TABLE_CATEGORIES, null, cv);
            if (id != -1)
                return true;

        } catch (SQLException e) {
            Log.wtf("MYERROR", e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

        return false;
    }
    public boolean deleteCategory(String name) {
        SQLiteDatabase db = null;

        try {
            db = getWritableDatabase();

            long id = db.delete(Constants.TABLE_CATEGORIES, Constants.TABLE_CATEGORIES_NAME + "=?", new String[]{name});
            if (id != -1)
                return true;

        } catch (SQLException e) {
            Log.wtf("MYERROR", e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

        return false;
    }

    public List<Expense> getExpenses(long startDate, long endDate) {
        SQLiteDatabase db = null;
        List<Expense> expenses = new ArrayList<Expense>();
        try {
            db = getReadableDatabase();
            String userAlias = "userName";
            String categoryAlias = "categoryName";
//            String sql = "SELECT " + "u." + Constants.TABLE_USERS_NAME + " as " + userAlias +
//                    ", ex." + Constants.TABLE_EXPENSES_DESCRIPTION +
//                    ", ex." + Constants.TABLE_EXPENSES_DATE_OF_ADDING +
//                    ", ex." + Constants.TABLE_EXPENSES_PRICE +
//                    ", c." + Constants.TABLE_CATEGORIES_NAME + " as " + categoryAlias +
//                    " FROM " + Constants.TABLE_EXPENSES + " ex" +
//                    " INNER JOIN " + Constants.TABLE_USERS + " u on u." + Constants.TABLE_USERS_ID + " = ex." + Constants.TABLE_EXPENSES_USER_ID +
//                    " INNER JOIN " + Constants.TABLE_CATEGORIES + " c on c." + Constants.TABLE_CATEGORIES_ID + " = ex." + Constants.TABLE_EXPENSES_CATEGORY_ID;

            String sql = "SELECT " + "u." + Constants.TABLE_USERS_NAME + " as " + userAlias +
                    ", ex." + Constants.TABLE_EXPENSES_DESCRIPTION +
                    ", ex." + Constants.TABLE_EXPENSES_DATE_OF_ADDING +
                    ", ex." + Constants.TABLE_EXPENSES_PRICE +
                    ", c." + Constants.TABLE_CATEGORIES_NAME + " as " + categoryAlias +
                    " FROM " + Constants.TABLE_EXPENSES + " ex" +
                    " INNER JOIN " + Constants.TABLE_USERS + " u on u." + Constants.TABLE_USERS_ID + " = ex." + Constants.TABLE_EXPENSES_USER_ID +
                    " INNER JOIN " + Constants.TABLE_CATEGORIES + " c on c." + Constants.TABLE_CATEGORIES_ID + " = ex." + Constants.TABLE_EXPENSES_CATEGORY_ID +
                    " WHERE ex." + Constants.TABLE_EXPENSES_DATE_OF_ADDING +
                    " BETWEEN " + startDate + " AND " + endDate;


            String mySql = "SELECT " + "u." + Constants.TABLE_USERS_USERNAME + " as " + userAlias +
                    ", ex." + Constants.TABLE_EXPENSES_DESCRIPTION +
                    ", ex." + Constants.TABLE_EXPENSES_DATE_OF_ADDING +
                    ", ex." + Constants.TABLE_EXPENSES_PRICE +
                    ", c." + Constants.TABLE_CATEGORIES_NAME + " as " + categoryAlias +
                    " FROM " + Constants.TABLE_EXPENSES + " ex" +
                    " INNER JOIN " + Constants.TABLE_USERS + " u on u." + Constants.TABLE_USERS_ID + " = ex." + Constants.TABLE_EXPENSES_USER_ID +
                    " INNER JOIN " + Constants.TABLE_CATEGORIES + " c on c." + Constants.TABLE_CATEGORIES_ID + " = ex." + Constants.TABLE_EXPENSES_CATEGORY_ID +
                    " WHERE ex." + Constants.TABLE_EXPENSES_DATE_OF_ADDING +
                    " BETWEEN " + startDate + " AND " + endDate;
            Cursor myCursor = db.rawQuery(mySql, null);
            while (myCursor.moveToNext()){
                String description = myCursor.getString(myCursor.getColumnIndex(Constants.TABLE_EXPENSES_DESCRIPTION));
                int a = 4;
            }

            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                String userName = c.getString(c.getColumnIndex(userAlias));
                String description = c.getString(c.getColumnIndex(Constants.TABLE_EXPENSES_DESCRIPTION));
                BigDecimal price = new BigDecimal(c.getString(c.getColumnIndex(Constants.TABLE_EXPENSES_PRICE)));
                String category = c.getString(c.getColumnIndex(categoryAlias));

                long dateOfAdding = c.getLong(c.getColumnIndex(Constants.TABLE_EXPENSES_DATE_OF_ADDING));
                Expense expense = new Expense(userName, description, price, category, dateOfAdding);

                expenses.add(expense);
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return expenses;
    }

   public List<String> getFamilies(){

       SQLiteDatabase db = null;
       List<String> families = new ArrayList<String>();
       try {
           db = getReadableDatabase();
           String sql = "SELECT "  + Constants.TABLE_FAMILIES_NAME
                   +" FROM " + Constants.TABLE_FAMILIES;
           Cursor c = db.rawQuery(sql, null);
           while (c.moveToNext()) {
               String FamilyName = c.getString(c.getColumnIndex(Constants.TABLE_FAMILIES_NAME));
               families.add(FamilyName);
           }
       } catch (Exception e) {
           Log.wtf(MYERROR, e.getMessage());
       }

       return families;
   }

    public boolean addExpense(int userId, int categoryId, BigDecimal price, long date, String description) {
        SQLiteDatabase db = null;

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_EXPENSES_USER_ID, userId);
            cv.put(Constants.TABLE_EXPENSES_CATEGORY_ID, categoryId);
            cv.put(Constants.TABLE_EXPENSES_PRICE, price.toString());
            cv.put(Constants.TABLE_EXPENSES_DATE_OF_ADDING, date);
            cv.put(Constants.TABLE_EXPENSES_DESCRIPTION, description);

            long id = db.insert(Constants.TABLE_EXPENSES, null, cv);
            if (id != -1)
                return true;

        } catch (SQLException e) {
            Log.wtf("MYERROR", e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

        return false;
    }
}