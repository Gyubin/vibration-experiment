package io.qbinson.vibration;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "진동 실험 앱입니다 참여해주셔서 고맙습니다. :-)", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        writeSharedPreference();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void mOnClick(View v){
        switch( v.getId() ){
            case R.id.button1:
                makeAlarm(true, 0);
                break;
            case R.id.button2:
                makeAlarm(true, 1);
                break;
            case R.id.button4:
                makeAlarm(true, 2);
                break;
            case R.id.button5:
                makeAlarm(true, 3);
                break;
            case R.id.button_cancel:
                makeAlarm(false, 9);
                break;
        }
    }

    public void makeAlarm(boolean isCell, int cellNum) {
        Log.v("mth", "makeAlarm 첫 진입 cellNum = " + cellNum);
        // 실험 시작 버튼을 눌렀을 때
        if (isCell) {
            // 공통 처리: 비밀번호, 취소 버튼 Enabled로 바꾸기
            Button cancelButton = (Button)findViewById(R.id.button_cancel);
            cancelButton.setEnabled(true);
            cancelButton.setTextColor(Color.parseColor("#ffffff"));
            cancelButton.setTextSize(30);

            EditText pw = (EditText)findViewById(R.id.editText);
            pw.setEnabled(true);

            // 공통 처리: CELL 버튼 모두 disable로 바꾸기. 터치한 버튼이라면 텍스트도 바꾸기.
            int[] buttonIds = {R.id.button1, R.id.button2, R.id.button4, R.id.button5};
            Button startButton;
            for (int i = 0; i < 4; i++) {
                startButton = (Button)findViewById(buttonIds[i]);
                startButton.setEnabled(false);
                if (cellNum == i) {
                    startButton.setText("진행 중...");
                }
            }

            // 공통 처리: 알람매니저, 인텐트 생성
            AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
            PendingIntent[] sender = new PendingIntent[6];
            int count = 0;
            long alarmTime;

            Calendar target = Calendar.getInstance();
            target.setTimeInMillis(System.currentTimeMillis());

            int[][] times = new int[][]{
                    {13, 5}, {14, 1}, {15, 9}, {16, 22}, {17, 7}, {18, 9} // 진짜
                    // {19, 11}, {19, 12}, {19, 13}, {19, 14}, {19, 15}, {19, 16}  // 테스트
            };
            for (int[] time : times) {
                Intent intent = new Intent(MainActivity.this, AlarmReceive.class);
                intent.putExtra("count", count);
                Log.v("mth", "시간대별 반복 돌 때 cellNum = " + cellNum);
                intent.putExtra("cellNum", cellNum);    // 요거 전달해서 메시지, 진동 구분.

                sender[count] = PendingIntent.getBroadcast(MainActivity.this, count, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                target.set(Calendar.HOUR_OF_DAY, time[0]);
                target.set(Calendar.MINUTE, time[1]);
                target.set(Calendar.SECOND, 0);
                target.set(Calendar.MILLISECOND, 0);
                alarmTime = target.getTimeInMillis();
                am.set(AlarmManager.RTC_WAKEUP, alarmTime, sender[count]);
                count++;
            }
            Toast toast = Toast.makeText(getApplicationContext(), "실험이 시작됩니다.", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();

        // 중단 버튼을 눌렀을 때
        } else {
            EditText pw = (EditText)findViewById(R.id.editText);
            if (pw.getText().toString().equals("0948")) {

                // 공통 처리: CELL 버튼 모두 enabled로 바꾸기. 터치한 버튼이라면 텍스트도 바꾸기.
                int[] buttonIds = {R.id.button1, R.id.button2, R.id.button4, R.id.button5};
                String[] buttonTexts = {"CELL 1", "CELL 2", "CELL 4", "CELL 5"};
                Button startButton;
                for (int i = 0; i < 4; i++) {
                    startButton = (Button)findViewById(buttonIds[i]);
                    startButton.setEnabled(true);
                    startButton.setText(buttonTexts[i]);
                }

                // 취소 버튼 터치 불가능으로 바꾸기
                Button cancelButton = (Button)findViewById(R.id.button_cancel);
                cancelButton.setEnabled(false);
                cancelButton.setTextColor(Color.parseColor("#000000"));
                cancelButton.setTextSize(20);

                // 비밀번호 입력칸 비우기
                pw.setText("");
                pw.setEnabled(false);

                // 모든 알람 없애기
                PendingIntent[] sender = new PendingIntent[6];
                AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(MainActivity.this, AlarmReceive.class);
                for(int i = 0; i < 6; i++) {
                    sender[i] = PendingIntent.getBroadcast(MainActivity.this, i, intent, 0);
                    am.cancel(sender[i]);
                }

                Toast toast = Toast.makeText(getApplicationContext(), "모든 실험이 중단되었습니다", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                pw.setText("");
                Toast toast = Toast.makeText(getApplicationContext(), "비밀번호 틀림", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    }

    public void writeSharedPreference() {
        int[] cells = {0, 1, 2, 0, 1, 2};
        shuffleArray(cells);
        SharedPreferences sp = this.getSharedPreferences("CELLS", Context.MODE_PRIVATE | Context.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sp.edit();
        for (int i = 0; i < 6; i++) {
            editor.putInt("index"+i, cells[i]);
        }
        editor.commit();
    }

    static void shuffleArray(int[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
    }

}
