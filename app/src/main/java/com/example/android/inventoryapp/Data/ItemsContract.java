package com.example.android.inventoryapp.Data;

import android.provider.BaseColumns;

import com.example.android.inventoryapp.R;

public class ItemsContract {
    public static class ItemsEntry implements BaseColumns {
        public static final String _ID = BaseColumns._ID;
        public static final String TABLE_NAME = "Books";
        public static final String COLUMN_PRODUCT_NAME = "Name";
        public static final String COLUMN_PRICE = "Price";
        public static final String COLUMN_QUANTITY = "Quantity";
        public static final String COLUMN_SUPPLIER_NAME = "SupplierName";
        public static final String COLUMN_SUPPLIER_PHONE_NUMBER = "SupplierPhoneNumber";

    }
}
