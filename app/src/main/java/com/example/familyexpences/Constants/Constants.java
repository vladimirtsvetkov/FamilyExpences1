package com.example.familyexpences.Constants;

public final class Constants {

    public static final String LOGIN = "login";
    public static final String LOGGED_USER = "loggedUser";

    public static final String TABLE_USERS = "user";
    public static final String TABLE_CATEGORIES = "categories";
    public static final String TABLE_FAMILIES = "families";
    public static final String TABLE_EXPENSES = "expenses";
    public static final String TABLE_REQUESTS = "requests";

    public static final String TABLE_USERS_ID = "id";
    public static final String TABLE_USERS_USERNAME = "username";
    public static final String TABLE_USERS_PASSWORD = "password";
    public static final String TABLE_USERS_NAME = "name";
    public static final String TABLE_USERS_FAMILY_ID = "UFamilyID ";

    public static final String TABLE_FAMILIES_ID = "id";
    public static final String TABLE_FAMILIES_NAME = "name";
    public static final String TABLE_FAMILIES_ADMIN_ID = "admin_id";

    public static final String TABLE_CATEGORIES_ID = "id";
    public static final String TABLE_CATEGORIES_NAME = "name";
    //  add public static final String TABLE_CATEGORIES_USER_ID = "user_id";

    public static final String TABLE_EXPENSES_ID = "id";
    public static final String TABLE_EXPENSES_CATEGORY_ID = "category_id";
    public static final String TABLE_EXPENSES_USER_ID = "user_id";
    public static final String TABLE_EXPENSES_DATE_OF_ADDING = "date_of_adding";
    public static final String TABLE_EXPENSES_DESCRIPTION = "description";
    public static final String TABLE_EXPENSES_PRICE = "price";

    public static final String TABLE_REQUESTS_REQUEST_ID = "request_id";
    public static final String TABLE_REQUESTS_FAMILY_ID = "family_id";
    public static final String TABLE_REQUESTS_USER_ID = "user_id";
    public static final String TABLE_REQUESTS_IS_APPROVED = "is_approved";


    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS
            + "('" + TABLE_USERS_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_USERS_FAMILY_ID + "' INTEGER(11) ," +
            "'" + TABLE_USERS_USERNAME + "' varchar(50) NOT NULL UNIQUE," +
            "'" + TABLE_USERS_PASSWORD + "' varchar(50) NOT NULL," +
            "'" + TABLE_USERS_NAME + "' varchar(50) NOT NULL)";


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
            "'" + TABLE_EXPENSES_DATE_OF_ADDING + "' INTEGER NOT NULL," +
            "'" + TABLE_EXPENSES_DESCRIPTION + "' VARCHAR(200) NOT NULL," +
            "'" + TABLE_EXPENSES_PRICE + "' decimal(6,4) NOT NULL," +
            " FOREIGN KEY ('" + TABLE_EXPENSES_CATEGORY_ID + "') REFERENCES " + TABLE_CATEGORIES + "('" + TABLE_CATEGORIES_ID + "')," +
            " FOREIGN KEY ('" + TABLE_EXPENSES_USER_ID + "') REFERENCES " + TABLE_USERS + "('" + TABLE_USERS_ID + "'))";

    public static final String CREATE_TABLE_REQUESTS = "CREATE TABLE " + TABLE_REQUESTS
            + "('" + TABLE_REQUESTS_REQUEST_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + TABLE_REQUESTS_FAMILY_ID + "' INTEGER(11) NOT NULL," +
            "'" + TABLE_REQUESTS_USER_ID + "' INTEGER(11) NOT NULL," +
            "'" + TABLE_REQUESTS_IS_APPROVED + "' INTEGER(1) NOT NULL," +
            " FOREIGN KEY ('" + TABLE_REQUESTS_FAMILY_ID + "') REFERENCES " + TABLE_FAMILIES + "('" + TABLE_FAMILIES_ID + "')," +
            " FOREIGN KEY ('" + TABLE_REQUESTS_USER_ID + "') REFERENCES " + TABLE_USERS + "('" + TABLE_USERS_ID + "'))";


}