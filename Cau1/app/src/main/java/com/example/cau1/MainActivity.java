package com.example.cau1;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private TextView txt_thongTinChiTiet;
    private EditText edt_hovaten, edt_soLuong, edt_donGia, edt_thue, edt_thanhTien;
    private Button btn_tinhTien, btn_tiepTuc, btn_dong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.drinks_array, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
        txt_thongTinChiTiet = findViewById(R.id.txt_thongTinChiTiet);
        edt_hovaten = findViewById(R.id.edt_hovaten);
        edt_soLuong = findViewById(R.id.edt_soLuong);
        edt_donGia = findViewById(R.id.edt_donGia);
        edt_thue = findViewById(R.id.edt_thue);
        edt_thanhTien = findViewById(R.id.edt_thanhTien);
            btn_tinhTien = findViewById(R.id.btn_tinhTien);
            btn_tiepTuc = findViewById(R.id.btn_tiepTuc);
            btn_dong = findViewById(R.id.btn_dong);
            btn_tinhTien.setOnClickListener(v -> {
                if (TextUtils.isEmpty(edt_hovaten.getText()) ||
                        TextUtils.isEmpty(edt_soLuong.getText()) ||
                        TextUtils.isEmpty(edt_donGia.getText()) ||
                        TextUtils.isEmpty(edt_thue.getText()) ||
                        spinner.getSelectedItem() == null) {

                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
                else {
                    String soLuong = edt_soLuong.getText().toString();
                    String donGia = edt_donGia.getText().toString();
                    String thue = edt_thue.getText().toString();
                    String thanhTien = edt_thanhTien.getText().toString();

                    int soLuongInt = Integer.parseInt(soLuong);
                    int donGiaInt = Integer.parseInt(donGia);
                    int thueInt = Integer.parseInt(thue);
                    int thanhTienInt = Integer.parseInt(thanhTien);
                    String hoTen = edt_hovaten.getText().toString();
                    thanhTienInt = soLuongInt * donGiaInt + (soLuongInt * donGiaInt * thueInt / 100);
                    edt_thanhTien.setText(String.valueOf(thanhTienInt));
                    String thongTinChiTiet = "Tên khách hàng: " + hoTen + "\n" +
                            "Nước uống: " + spinner.getSelectedItem().toString() + "\n" +
                            "Số lượng: " + soLuong + "\n" +
                            "Đơn giá: " + donGia + "\n" + "Thành tiền: "+ thanhTienInt;
                    txt_thongTinChiTiet.setText(thongTinChiTiet);
                }
            });
            btn_tiepTuc.setOnClickListener(v -> {
                edt_hovaten.setText("");
                edt_soLuong.setText("");
                edt_donGia.setText("");
                edt_thue.setText("");
                edt_thanhTien.setText("");
            });
            btn_dong.setOnClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Xác nhận để thoát ");
                builder.setMessage("Bạn có chắc thoát?");
                builder.setPositiveButton("Có", (dialog, which) -> {
                    finish();
                });
                builder.setNegativeButton("Không", (dialog, which) -> {
                    dialog.dismiss();
                });
                builder.show();
            });
            btn_tiepTuc.setOnClickListener(v -> {
                edt_hovaten.setText("");
                edt_soLuong.setText("");
                edt_donGia.setText("");
                edt_thue.setText("");
                edt_thanhTien.setText("");
                txt_thongTinChiTiet.setText("");
            });
        }

    }
