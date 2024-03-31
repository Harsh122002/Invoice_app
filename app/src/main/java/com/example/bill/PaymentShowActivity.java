package com.example.bill;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PaymentShowActivity extends AppCompatActivity implements PaymentAdapter.PaymentClickListener {

    private RecyclerView recyclerView;
    private PaymentAdapter adapter;
    private List<Payment> paymentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentview);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentList = new ArrayList<>();
        adapter = new PaymentAdapter(getPaymentsFromDatabase(), this);
        recyclerView.setAdapter(adapter);
    }

    private List<Payment> getPaymentsFromDatabase() {
        paymentList.clear();
        PDatabaseHelper dbHelper = new PDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + PDatabaseHelper.TABLE_PAYMENT, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                Payment payment = new Payment();
                payment.setId(cursor.getLong(cursor.getColumnIndex(PDatabaseHelper.COLUMN_ID)));
                payment.setName(cursor.getString(cursor.getColumnIndex(PDatabaseHelper.COLUMN_NAME)));
                payment.setDate(cursor.getString(cursor.getColumnIndex(PDatabaseHelper.COLUMN_DATE)));
                payment.setNumber(cursor.getString(cursor.getColumnIndex(PDatabaseHelper.COLUMN_NUMBER)));
                payment.setOption(cursor.getString(cursor.getColumnIndex(PDatabaseHelper.COLUMN_OPTION)));
                payment.setAmount(cursor.getString(cursor.getColumnIndex(PDatabaseHelper.COLUMN_AMOUNT)));

                paymentList.add(payment);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return paymentList;
    }

    @Override
    public void onDeleteClick(Payment payment) {
        deletePaymentFromDatabase(payment.getId());
        adapter.updateData(getPaymentsFromDatabase());
    }

    private void deletePaymentFromDatabase(long paymentId) {
        PDatabaseHelper dbHelper = new PDatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(PDatabaseHelper.TABLE_PAYMENT, PDatabaseHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(paymentId)});
        db.close();
    }
}
