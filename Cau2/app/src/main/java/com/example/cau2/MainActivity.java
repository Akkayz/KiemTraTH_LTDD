package com.example.cau2;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private FootballTeamDatabaseHelper dbHelper;
    private Button btn_themCauThu, btn_danhSachCauThu;

    public static class FootballTeamDatabaseHelper extends SQLiteOpenHelper {
        private static final String DB_NAME = "footballteamanager.db";
        private static final int DB_VERSION = 1;

        public FootballTeamDatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE tblplayer ("
                    + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "name TEXT, "
                    + "number INTEGER, "
                    + "address TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public boolean databaseExists(Context context) {
            String dbPath = context.getDatabasePath(DB_NAME).getPath();
            return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY) != null;
        }
    }

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

        dbHelper = new FootballTeamDatabaseHelper(this);


        SQLiteDatabase db = dbHelper.getReadableDatabase();

        btn_themCauThu = findViewById(R.id.btn_themCauThu);
        btn_themCauThu.setOnClickListener(v -> openThemCauThuActivity());
        btn_danhSachCauThu = findViewById(R.id.btn_danhSachCauThu);
        btn_danhSachCauThu.setOnClickListener(v -> openPlayerListActivity());

    }

    private void openPlayerListActivity() {
        Intent intent = new Intent(this, PlayerListActivity.class);
        startActivity(intent);
    }
    private void openThemCauThuActivity() {
        Intent intent = new Intent(this, themcauthu.class);
        startActivity(intent);
    }

}
