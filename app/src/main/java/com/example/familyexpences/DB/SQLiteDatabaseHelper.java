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
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieEntry;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 20;
    public static final String DB_NAME = "FamilyExpenses.db";
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
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_REQUESTS);
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

    public int getFamilyAdminId(int userID) {
        SQLiteDatabase db = null;
        int FamilyAdminID = 0;
        try {
            db = getReadableDatabase();
            String sql = " SELECT " + Constants.TABLE_FAMILIES_ADMIN_ID + " FROM " +
                    Constants.TABLE_FAMILIES + " WHERE " + Constants.TABLE_FAMILIES_ADMIN_ID + " = '" + userID + "'";
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                FamilyAdminID = c.getInt(c.getColumnIndex(Constants.TABLE_FAMILIES_ADMIN_ID));
            }

        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());

        }
        return FamilyAdminID;
    }

    public int getFamilyID(String familyName) {
        SQLiteDatabase db = null;
        int FamilyID = 0;
        try {
            db = getReadableDatabase();
            String sql = " SELECT " + Constants.TABLE_FAMILIES_ID + " FROM " +
                    Constants.TABLE_FAMILIES + " WHERE " + Constants.TABLE_FAMILIES_NAME + " = '" + familyName + "'";
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                FamilyID = c.getInt(c.getColumnIndex(Constants.TABLE_FAMILIES_ID));
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }
        return FamilyID;
    }

    public int getFamilyIDByAdmin(int familyAdminID) {
        SQLiteDatabase db = null;
        int FamilyID = 0;
        try {
            db = getReadableDatabase();
            String sql = " SELECT " + Constants.TABLE_FAMILIES_ID + " FROM " +
                    Constants.TABLE_FAMILIES + " WHERE " + Constants.TABLE_FAMILIES_ADMIN_ID + " = '" + familyAdminID + "'";
            Cursor c = db.rawQuery(sql, null);
            while (c.moveToNext()) {
                FamilyID = c.getInt(c.getColumnIndex(Constants.TABLE_FAMILIES_ID));
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }
        return FamilyID;
    }

    public List<String> getFamilyMembers(int LoggedUser) {
        SQLiteDatabase db = null;
        List<String> usernames = new ArrayList<String>();
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + Constants.TABLE_USERS_USERNAME + " FROM " + Constants.TABLE_USERS
                    + " WHERE " + Constants.TABLE_USERS_ID + " = '" + LoggedUser + "'";
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

    public boolean AssignedToFamily(String username) {
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            String sql = " SELECT " + Constants.TABLE_USERS_FAMILY_ID + " FROM " + Constants.TABLE_FAMILIES;
            /*String sql = " SELECT " + Constants.TABLE_USERS_FAMILY_ID  + " FROM " + Constants.TABLE_FAMILIES
            + " WHERE " + Constants.TABLE_USERS_FAMILY_ID + " = '" + username + "'";*/

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

     /*Using procedures at once insertUser, insertFamily, updateUser
     for adding new family and automatically assigning its admin(adding new user), and assigning user's family id
    */
    // logic for adding admin use

    public int insertUser(User user) {
        SQLiteDatabase db = null;
        int UserId = 0;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_USERS_USERNAME, user.getUsername());
            cv.put(Constants.TABLE_USERS_PASSWORD, user.getPassword());
            cv.put(Constants.TABLE_USERS_NAME, user.getName());

            UserId = (int) db.insert(Constants.TABLE_USERS, null, cv);

        } catch (SQLException e) {
            Log.wtf(MYERROR, e.getMessage());
        } finally {
            if (db != null)
                db.close();
        }

        return UserId;
    }

    public int insertFamily(String FamilyName, int AdminID) {
        SQLiteDatabase db = null;
        int Family_Id = 0;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_FAMILIES_NAME, FamilyName);
            cv.put(Constants.TABLE_FAMILIES_ADMIN_ID, AdminID);

            Family_Id = (int) db.insert(Constants.TABLE_FAMILIES, null, cv);

        } catch (SQLException e) {
            Log.wtf(MYERROR, e.getMessage());
        }

        return Family_Id;
    }

    public boolean updateUser(int FamilyID, int userId) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_USERS_FAMILY_ID, FamilyID);
            String sql = " UPDATE " + Constants.TABLE_USERS + " SET " + Constants.TABLE_USERS_FAMILY_ID + "=" + FamilyID +
                    " WHERE " + Constants.TABLE_USERS_ID + "=" + userId;
            db.execSQL(sql);
            long id = db.update(Constants.TABLE_USERS, cv, Constants.TABLE_USERS_ID + "=?" + userId, null);
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


    // -----------logic for adding admin use-----------


    //--------logic for creating a user member and new request for joining a family---------

    /*
    Using both procedures  InsertFamilyUser and GenerateFamilyRequest
     for creating a new user, and creating a new request for joining
     already registered family
    */

    public boolean InsertFamilyUser(User user, int FamilyID) {
        SQLiteDatabase db = null;

        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_USERS_USERNAME, user.getUsername());
            cv.put(Constants.TABLE_USERS_PASSWORD, user.getPassword());
            cv.put(Constants.TABLE_USERS_NAME, user.getName());

            long id = db.insert(Constants.TABLE_USERS, null, cv);
            GenerateFamilyRequest(FamilyID, (int) id);
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

    public boolean GenerateFamilyRequest(int FamilyID, int UserID) {
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_REQUESTS_FAMILY_ID, FamilyID);
            cv.put(Constants.TABLE_REQUESTS_USER_ID, UserID);
            cv.put(Constants.TABLE_REQUESTS_IS_APPROVED, 0);

            long id = db.insert(Constants.TABLE_REQUESTS, null, cv);
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


    ////--------logic for creating a user member and new request for joining a family---------


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

    public List<String> getFamilies() {

        SQLiteDatabase db = null;
        List<String> families = new ArrayList<String>();
        try {
            db = getReadableDatabase();
            String sql = "SELECT " + Constants.TABLE_FAMILIES_NAME
                    + " FROM " + Constants.TABLE_FAMILIES;
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

    public ArrayList<PieEntry> getPieEntries() {
        SQLiteDatabase db = null;
        db = getWritableDatabase();

        String[] columns = new String[]{"TABLE_CATEGORIES_NAME AS category", "SUM(TABLE_EXPENSES_PRICE) AS price"};
        String sql = "SELECT " + Constants.TABLE_CATEGORIES_NAME + " as category, SUM(" + Constants.TABLE_EXPENSES_PRICE +
                ") " + "price from " + Constants.TABLE_EXPENSES + " ex " +
                "JOIN " + Constants.TABLE_CATEGORIES + " " +
                "c on ex." + Constants.TABLE_EXPENSES_CATEGORY_ID
                + " = c." + Constants.TABLE_CATEGORIES_ID
                + " Where ex." + Constants.TABLE_EXPENSES_USER_ID + " = 1 " +
                "GROUP by category";

        db.rawQuery(sql, null);

        Cursor csr = db.rawQuery(sql, null);
        ArrayList<PieEntry> rv = new ArrayList<>();
        while (csr.moveToNext()) {
            rv.add(new PieEntry(
                    csr.getInt(csr.getColumnIndex("category")),
                    csr.getFloat(csr.getColumnIndex("price"))));
        }
        csr.close();
        return rv;

    }

    public String checkRequests(int familyID) {
        SQLiteDatabase db = null;

        String UserName = "";
        try {
            db = getReadableDatabase();
            String sql = " SELECT " + Constants.TABLE_USERS_USERNAME +" FROM " + Constants.TABLE_USERS + " AS u"+
                           " JOIN " + Constants.TABLE_REQUESTS + " AS req ON req." + Constants.TABLE_REQUESTS_USER_ID + " = " +
                              " u." + Constants.TABLE_USERS_ID + " WHERE " +
                            Constants.TABLE_REQUESTS_FAMILY_ID + " = " + familyID ;

            Cursor c = db.rawQuery(sql, null);

            /*SELECT User_name FROM users u
                JOIN table_requests r
                ON r.TABLE_REQUESTS_USER_ID = u.User_ID
                WHERE TABLE_REQUESTS_FAMILY_ID = 9
                */

            if(c.moveToFirst()){
                UserName = c.getString(c.getColumnIndex(Constants.TABLE_USERS_USERNAME));
            }
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }
        return UserName;
    }

    public boolean deleteRequest(int UserId){
        SQLiteDatabase db = null;
        db = getWritableDatabase();
        try{
             //delete request
             db.delete(Constants.TABLE_REQUESTS, Constants.TABLE_REQUESTS_USER_ID + "=" + UserId, null);

             //delete user from the DB
             db.delete(Constants.TABLE_USERS, Constants.TABLE_USERS_ID + "=" + UserId, null);

            return true;
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }
        return false;
    }

    public boolean acceptRequest(int UserId, int FamilyId){
        SQLiteDatabase db = null;
        db = getWritableDatabase();
        try{

            ContentValues cv = new ContentValues();
            cv.put(Constants.TABLE_USERS_FAMILY_ID,FamilyId);
            db.update(Constants.TABLE_USERS,cv,Constants.TABLE_USERS_ID + "=" + UserId,null);

            //delete request - it is approved
            db.delete(Constants.TABLE_REQUESTS, Constants.TABLE_REQUESTS_USER_ID + "=" + UserId, null);


            return true;
        } catch (Exception e) {
            Log.wtf(MYERROR, e.getMessage());
        }
        return false;
    }


}

