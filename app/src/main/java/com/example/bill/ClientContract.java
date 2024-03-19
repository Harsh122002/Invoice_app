package com.example.bill;

import android.provider.BaseColumns;

public final class ClientContract {

    public ClientContract() {}

    public static class ClientEntry implements BaseColumns {
        public static final String TABLE_NAME = "clients";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ROLE = "role";
        public static final String COLUMN_NAME_GST_NUMBER = "gst_number";
        public static final String COLUMN_NAME_MOBILE_NUMBER = "mobile_number";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }
}
