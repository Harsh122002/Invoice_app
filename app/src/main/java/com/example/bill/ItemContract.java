package com.example.bill;

import android.provider.BaseColumns;

public final class ItemContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ItemContract() {}

    /* Inner class that defines the table contents */
    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_DISCOUNT = "discount";
        public static final String COLUMN_NAME_GST_TAX = "gst_tax";
        public static final String COLUMN_NAME_QTY = "qty";

    }
}
