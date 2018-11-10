package com.example.android.inventoryapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.inventoryapp.Data.BooksDbHelper;
import com.example.android.inventoryapp.Data.ItemsContract;

public class MainActivity extends AppCompatActivity {

    EditText mbookName;
    EditText mbookPrice;
    EditText mQuantity;
    EditText mSupplierName;
    EditText mSupplierNumber;
    TextView displayView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbookName = findViewById(R.id.bookName);
        mbookPrice = findViewById(R.id.bookPrice);
        mQuantity = findViewById(R.id.quantity);
        mSupplierName = findViewById(R.id.supplierName);
        mSupplierNumber = findViewById(R.id.supplierPhoneNumber);
        displayView = findViewById(R.id.displayView);
        Button enterButton = findViewById(R.id.button);
        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertBook();
            }
        });
        readInfo();
    }

    private void readInfo() {
        Log.e("readInfo", "readInfo: ");
        BooksDbHelper mDbHelper = new BooksDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        String[] projections = {
                ItemsContract.ItemsEntry._ID,
                ItemsContract.ItemsEntry.COLUMN_PRODUCT_NAME,
                ItemsContract.ItemsEntry.COLUMN_PRICE,
                ItemsContract.ItemsEntry.COLUMN_QUANTITY,
                ItemsContract.ItemsEntry.COLUMN_SUPPLIER_NAME,
                ItemsContract.ItemsEntry.COLUMN_SUPPLIER_PHONE_NUMBER
        };
        Cursor cursor = db.query(ItemsContract.ItemsEntry.TABLE_NAME,
                projections,
                null,
                null,
                null,
                null,
                null);
        try {
            displayView.setText("The table contains " + cursor.getCount() + " pets.\n\n");

            int idColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry._ID);
            int booknameColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_PRODUCT_NAME);
            int bookPriceColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_PRICE);
            int quantityColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_QUANTITY);
            int supplierNameColumnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_SUPPLIER_NAME);
            int supplierContactColulmnIndex = cursor.getColumnIndex(ItemsContract.ItemsEntry.COLUMN_SUPPLIER_PHONE_NUMBER);
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentBookName = cursor.getString(booknameColumnIndex);
                int currentPrice = cursor.getInt(bookPriceColumnIndex);
                int currentQuantity = cursor.getInt(quantityColumnIndex);
                String currentSupplierName = cursor.getString(supplierNameColumnIndex);
                int currentSupplierNumber = cursor.getInt(supplierContactColulmnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentBookName + " - " +
                        currentPrice + " - " +
                        currentQuantity + " - " +
                        currentSupplierName + " - " +
                        currentSupplierNumber));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertBook() {
        Log.e("insertBook", "insertBook: ");
        String nameString = mbookName.getText().toString().trim();
        String priceString = mbookPrice.getText().toString().trim();
        String quantityString = mQuantity.getText().toString().trim();
        String supplierNameString = mSupplierName.getText().toString().trim();
        String supplierContactString = mSupplierNumber.getText().toString().trim();
        int price = Integer.parseInt(priceString);
        int quantity = Integer.parseInt(quantityString);
        int number = Integer.parseInt(supplierContactString);
        ContentValues values = new ContentValues();
        values.put(ItemsContract.ItemsEntry.COLUMN_PRODUCT_NAME, nameString);
        values.put(ItemsContract.ItemsEntry.COLUMN_PRICE, price);
        values.put(ItemsContract.ItemsEntry.COLUMN_QUANTITY, quantity);
        values.put(ItemsContract.ItemsEntry.COLUMN_SUPPLIER_NAME, supplierNameString);
        values.put(ItemsContract.ItemsEntry.COLUMN_SUPPLIER_PHONE_NUMBER, number);
        BooksDbHelper mDbHelper = new BooksDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowId = db.insert(ItemsContract.ItemsEntry.TABLE_NAME, null, values);

        if (newRowId == -1)
            Log.e("insertinfo", "insertBook: error ");
        else
            Log.e("Data", "INSERTED BOOK ID: " + newRowId);

        readInfo();
    }
}
