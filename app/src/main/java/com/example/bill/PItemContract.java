package com.example.bill;

import android.provider.BaseColumns;

public final class PItemContract {
    private PItemContract() {}

    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "items";
        public static final String COLUMN_NAME_INVOICE_NUMBER = "invoice_number";
        public static final String COLUMN_NAME_DATE = "date";
        public static final String COLUMN_NAME_CLIENT_NAME = "client_name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_PRODUCT = "product";
        public static final String COLUMN_NAME_QTY = "qty";
        public static final String COLUMN_NAME_UNIT = "unit";
        public static final String COLUMN_NAME_PRICE = "price";
        public static final String COLUMN_NAME_GST = "gst";
        public static final String COLUMN_NAME_DISCOUNT = "discount";
        public static final String COLUMN_NAME_AMOUNT = "amount";
        public static final String COLUMN_NAME_TOTAL_AMOUNT = "total_amount";
    }
}
