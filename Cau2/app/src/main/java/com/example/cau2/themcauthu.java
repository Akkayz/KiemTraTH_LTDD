package com.example.cau2;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class themcauthu extends AppCompatActivity {

    private MainActivity.FootballTeamDatabaseHelper dbHelper;
    private EditText edt_hoVaTen, edt_soAo, edt_diaChi;
    private Button btn_them, btn_xoa, btn_dong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_themcauthu);

        dbHelper = new MainActivity.FootballTeamDatabaseHelper(this);
        edt_hoVaTen = findViewById(R.id.edt_hoVaTen);
        edt_soAo = findViewById(R.id.edt_soAo);
        edt_diaChi = findViewById(R.id.edt_diaChi);
        btn_them = findViewById(R.id.btn_them);
        btn_xoa = findViewById(R.id.btn_xoa);
        btn_dong = findViewById(R.id.btn_dong);

        btn_them.setOnClickListener(v -> {
            if (validateInput()) {
                insertPlayer();
            }
        });

        btn_xoa.setOnClickListener(v -> clearFields());

        btn_dong.setOnClickListener(v -> {
            showConfirmationDialog();
        });
    }

    private boolean validateInput() {
        String name = edt_hoVaTen.getText().toString().trim();
        String numberStr = edt_soAo.getText().toString().trim();
        String address = edt_diaChi.getText().toString().trim();

        if (name.isEmpty() || numberStr.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void insertPlayer() {
        // Kiểm tra xem tất cả các trường thông tin có dấu (*) đã được nhập chưa
        if (TextUtils.isEmpty(edt_hoVaTen.getText().toString().trim()) ||
                TextUtils.isEmpty(edt_soAo.getText().toString().trim()) ||
                TextUtils.isEmpty(edt_diaChi.getText().toString().trim())) {
            Toast.makeText(this, "Vui lòng điền đầy đủ thông tin có dấu (*)", Toast.LENGTH_SHORT).show();
            return; // Không thêm cầu thủ nếu thiếu thông tin
        }

        // Nếu tất cả thông tin đã được nhập, tiến hành thêm cầu thủ vào cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", edt_hoVaTen.getText().toString().trim());
        values.put("number", Integer.parseInt(edt_soAo.getText().toString().trim()));
        values.put("address", edt_diaChi.getText().toString().trim());

        long newRowId = db.insert("tblplayer", null, values);
        if (newRowId != -1) {
            Toast.makeText(this, "Thêm cầu thủ thành công", Toast.LENGTH_SHORT).show();
            clearFields();
        } else {
            Toast.makeText(this, "Thêm cầu thủ thất bại", Toast.LENGTH_SHORT).show();
        }
    }


    private void clearFields() {
        edt_hoVaTen.setText("");
        edt_soAo.setText("");
        edt_diaChi.setText("");
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có chắc muốn đóng màn hình?")
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                    }
                })
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}