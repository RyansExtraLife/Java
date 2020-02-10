package com.example.AssetManagementSystem.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class DatabaseContentProvider {

    private SQLiteDatabase sqlLiteDatabase;

    public DatabaseContentProvider(SQLiteDatabase passedDatabase) {
        this.sqlLiteDatabase = passedDatabase;
    }

    protected abstract <T> T cursorToEntity(Cursor passedCursor);

    protected Cursor query(String passedTableName, String[] passedColumns, String passedSelection, String[] passedSelectionArguments, String passedSortOrder) {
        return sqlLiteDatabase.query(passedTableName, passedColumns, passedSelection, passedSelectionArguments, null, null, passedSortOrder);
    }

    protected long insert(String passedTableName, ContentValues passedContentValues) {
        return sqlLiteDatabase.insert(passedTableName, null, passedContentValues);
    }

    protected int update(String passedTableName, ContentValues passedContentValues, String passedSelection, String[] passedSelectionArguments) {
        return sqlLiteDatabase.update(passedTableName, passedContentValues, passedSelection, passedSelectionArguments);
    }

    protected int delete(String passedTableName, String passedSelection, String[] passedSelectionArguments) {
        return sqlLiteDatabase.delete(passedTableName, passedSelection, passedSelectionArguments);
    }
}