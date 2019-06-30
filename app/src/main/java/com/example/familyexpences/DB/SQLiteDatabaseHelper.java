package com.example.familyexpences.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.familyexpences.DTOs.Expense;
import com.example.familyexpences.DTOs.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 10;
    public static final String DB_NAME = "FamilyExpenses.db";

    public static final String TABLE_USERS = "user";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_FAMILIES = "families";
    public static final String TABLE_EXPENSES = "expenses";

    public static final String TABLE_USERS_ID = "id";
    public static final String TABLE_USERS_USERNAME = "username";
    public static final String TABLE_USERS_PASSWORD = "password";
    public static final String TABLE_USERS_NAME = "name";


    public static final String TABLE_FAMILIES_ID = "id";
    public static final String TABLE_FAMILIES_NAME = "name";
    public static final String TABLE_FAMILIES_ADMIN_ID = "admin_id";

    public static final String TABLE_CATEGORIES_ID = "id";
    public static final String TABLE_CATEGORIES_NAME = "name";

    public static final String TABLE_EXPENSES_ID = "id";
    public static final String TABLE_EXPENSES_CATEGORY_ID = "category_id";
    public static final String TABLE_EXPENSES_USER_ID = "user_id";
    public static final String TABLE_EXPENSES_DATE_OF_ADDING = "date_of_adding";
    public static final String TABLE_EXPENSES_DESCRIPTION = "description";
    public static final String TABLE_EXPENSES_PRICE = "price";


    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "('" + TABLE_USERS_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_USERS_USERNAME + "' varchar(50) NOT NULL UNIQUE," +
            "'" + TABLE_USERS_PASSWORD + "' varchar(50) NOT NULL," +
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


    public static final String MYERROR = "MYERROR";

    public SQLiteDatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CATEGORIES);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_FAMILIES);
        db.execSQL(CREATE_TABLE_EXPENSES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAMILIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    public boolean login(String username, String password) {
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();

            String sql = "SELECT * FROM " + TABLE_USERS
                    + " WHERE " + TABLE_USERS_USERNAME + " = '" + username + "'" +
                    " AND " + TABLE_USERS_PASSWORD + " = '" + password + "'";

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
            cv.put(TABLE_USERS_USERNAME, user.getUsername());
            cv.put(TABLE_USERS_PASSWORD, user.getPassword());
            cv.put(TABLE_USERS_NAME, user.getName());

            long id = db.insert(TABLE_USERS, null, cv);
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
            String sql = "SELECT " + TABLE_USERS_NAME + " FROM " + TABLE_USERS;
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                String username = c.getString(c.getColumnIndex(TABLE_USERS_USERNAME));
                usernames.add(username);
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return usernames;
    }

    public List<String> getCategories() {
        SQLiteDatabase db = null;
        List<String> categories = new ArrayList<String>();
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + TABLE_CATEGORIES_NAME + " FROM " + TABLE_CATEGORIES;
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                String name = c.getString(c.getColumnIndex(TABLE_CATEGORIES_NAME));
                categories.add(name);
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
            cv.put(TABLE_CATEGORIES_NAME, name);

            long id = db.insert(TABLE_CATEGORIES, null, cv);
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

    public List<Expense> getExpenses() {
        SQLiteDatabase db = null;
        List<Expense> expenses = new ArrayList<Expense>();
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + "ex." + TABLE_EXPENSES_DESCRIPTION +
                    ", ex." + TABLE_EXPENSES_PRICE +
                    ", c." + TABLE_CATEGORIES_NAME +
                    " FROM " + TABLE_EXPENSES + " as ex," + TABLE_CATEGORIES + " as c " +
                    " WHERE ex." + TABLE_EXPENSES_CATEGORY_ID + " = c." + TABLE_CATEGORIES_ID;
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                String description = c.getString(c.getColumnIndex(TABLE_EXPENSES_DESCRIPTION));
                BigDecimal price = new BigDecimal(c.getString(c.getColumnIndex(TABLE_EXPENSES_PRICE)));
                String category = c.getString(c.getColumnIndex(TABLE_EXPENSES_CATEGORY_ID));
                Expense expense = new Expense(description, price, category);
                expenses.add(expense);
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return expenses;
    }
}