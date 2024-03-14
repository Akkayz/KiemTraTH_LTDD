package com.example.cau2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class PlayerListActivity extends AppCompatActivity {

    private ListView listViewPlayers;
    private MainActivity.FootballTeamDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_list);

        dbHelper = new MainActivity.FootballTeamDatabaseHelper(this);
        listViewPlayers = findViewById(R.id.listViewPlayers);

        displayPlayers();
    }

    private void displayPlayers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Player> playerList = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT name, number, address FROM tblplayer", null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int number = cursor.getInt(cursor.getColumnIndex("number"));
                String address = cursor.getString(cursor.getColumnIndex("address"));

                Player player = new Player(name, number, address);
                playerList.add(player);
            } while (cursor.moveToNext());
            cursor.close();
        }

        PlayerAdapter adapter = new PlayerAdapter(playerList);
        listViewPlayers.setAdapter(adapter);
    }

    private static class Player {
        String name;
        int number;
        String address;

        Player(String name, int number, String address) {
            this.name = name;
            this.number = number;
            this.address = address;
        }
    }

    private static class PlayerAdapter extends BaseAdapter {
        private List<Player> playerList;

        PlayerAdapter(List<Player> playerList) {
            this.playerList = playerList;
        }

        @Override
        public int getCount() {
            return playerList.size();
        }

        @Override
        public Object getItem(int position) {
            return playerList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_list_item, parent, false);
            }

            Player player = playerList.get(position);

            TextView textViewName = convertView.findViewById(R.id.textViewName);
            TextView textViewNumber = convertView.findViewById(R.id.textViewNumber);
            TextView textViewAddress = convertView.findViewById(R.id.textViewAddress);

            textViewName.setText(player.name);
            textViewNumber.setText(String.valueOf(player.number));
            textViewAddress.setText(player.address);

            return convertView;
        }
    }
}
