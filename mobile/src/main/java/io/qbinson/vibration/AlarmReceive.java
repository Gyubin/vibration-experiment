package io.qbinson.vibration;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class AlarmReceive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long[][] vibrations = new long[][]{
                {0, 100, 140, 150, 700, 100, 140, 150, 700, 100, 140, 150},     // pulse
                {0, 230, 140, 100, 1000, 230, 140, 100, 1000, 230, 140, 100},   // heartbeat
                {0, 120, 360, 120, 360, 120, 360, 120, 360, 120, 360, 120}     // walk
        };

        long[][] vibrationsNone1 = new long[][]{
                {0, 1700},  // none1
                {0, 900, 900, 900}, // none2
                {0, 900, 900, 900, 900, 900}  // none3
        };

        long[][] vibrationsNone2 = new long[][]{
                {0, 900, 900, 900}, // none2
                {0, 900, 900, 900, 900, 900},  // none3
                {0, 1700}  // none1
        };

        long[][] vibrationsNone3 = new long[][]{
                {0, 900, 900, 900, 900, 900},  // none3
                {0, 1700},  // none1
                {0, 900, 900, 900} // none2
        };

        String[][] messagesNone = {
                {"일어설 시간입니다.", "일어서서 일 분 동안 몸을 움직이십시오."},
                {"스트레칭할 시간입니다.", "잠시 기지개를 활짝 펴십시오."},
                {"산책할 시간입니다.", "주변을 가볍게 걸어 보십시오."}
        };

        String[][] messagesWeak = {
                {"일어나세요.", "일어서서 일분동안 몸을 움직이세요."},
                {"스트레칭을 해주세요", "잠시 기지개를 활짝 펴보세요."},
                {"산책할 시간이에요.", "잠시 주변을 걸어보는게 어떨까요."}
        };

        String[][] messagesStrong = {
                {"일어설 시간이야.", "일어서서 일분동안 몸을 움직여 보자."},
                {"스트레칭할 시간이야.", "활짝 기지개를 펴봐."},
                {"산책할 시간이야.", "잠시 주변을 걸어보는게 어때."}
        };

        SharedPreferences temp = context.getSharedPreferences("CELLS", Context.MODE_WORLD_READABLE);
        int count = intent.getIntExtra("count", 100);
        int cellNum = intent.getIntExtra("cellNum", 100);
        int realIndex = temp.getInt("index"+count, 100);

        NotificationManager manager= (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Log.v("mth", "리시버에서 분기 들어가기 직전 cellNum: " + cellNum);
        Log.v("mth", "리시버에서 count: " + count);
        if (cellNum == 0) {
            // CELL 1: vibrations, messagesNone
            builder.setSmallIcon(R.mipmap.icon_none)
                    .setTicker(messagesNone[realIndex][0])
                    .setContentTitle(messagesNone[realIndex][0])
                    .setContentText(messagesNone[realIndex][1])
                    .extend(new NotificationCompat.WearableExtender().setBackground(
                            BitmapFactory.decodeResource(context.getResources(), R.mipmap.social_none)
                    ));

            builder.setVibrate(vibrations[realIndex]);
            builder.setAutoCancel(true);

            Log.v("mth", "all: " + temp.getAll().toString());
            Log.v("mth", "title: " + messagesNone[realIndex][0]);
            Log.v("mth", "text: " + messagesNone[realIndex][1]);
        } else if (cellNum == 1) {
            // CELL 2: vibrations, messagesWeak
            builder.setSmallIcon(R.mipmap.icon_weak)
                    .setTicker(messagesWeak[realIndex][0])
                    .setContentTitle(messagesWeak[realIndex][0])
                    .setContentText(messagesWeak[realIndex][1])
                    .extend(new NotificationCompat.WearableExtender().setBackground(
                            BitmapFactory.decodeResource(context.getResources(), R.mipmap.social_weak)
                    ));

            builder.setVibrate(vibrations[realIndex]);
            builder.setAutoCancel(true);

            Log.v("mth", "all: " + temp.getAll().toString());
            Log.v("mth", "title: " + messagesWeak[realIndex][0]);
            Log.v("mth", "text: " + messagesWeak[realIndex][1]);
        } else if (cellNum == 2) {
            // CELL 3: vibrations, messagesStrong
            builder.setSmallIcon(R.mipmap.icon_strong)
                    .setTicker(messagesStrong[realIndex][0])
                    .setContentTitle(messagesStrong[realIndex][0])
                    .setContentText(messagesStrong[realIndex][1])
                    .extend(new NotificationCompat.WearableExtender().setBackground(
                            BitmapFactory.decodeResource(context.getResources(), R.mipmap.social_strong)
                    ));

            builder.setVibrate(vibrations[realIndex]);
            builder.setAutoCancel(true);

            Log.v("mth", "all: " + temp.getAll().toString());
            Log.v("mth", "title: " + messagesStrong[realIndex][0]);
            Log.v("mth", "text: " + messagesStrong[realIndex][1]);
        } else if (cellNum == 3) {
            // CELL 4: vibrationsNone1, messagesNone
            builder.setSmallIcon(R.mipmap.icon_none)
                    .setTicker(messagesNone[realIndex][0])
                    .setContentTitle(messagesNone[realIndex][0])
                    .setContentText(messagesNone[realIndex][1])
                    .extend(new NotificationCompat.WearableExtender().setBackground(
                            BitmapFactory.decodeResource(context.getResources(), R.mipmap.social_none)
                    ));

            builder.setVibrate(vibrationsNone1[realIndex]);
            builder.setAutoCancel(true);

            Log.v("mth", "all: " + temp.getAll().toString());
            Log.v("mth", "title: " + messagesNone[realIndex][0]);
            Log.v("mth", "text: " + messagesNone[realIndex][1]);
        } else if (cellNum == 4) {
            // CELL 5: vibrationsNone2, messagesWeak
            builder.setSmallIcon(R.mipmap.icon_weak)
                    .setTicker(messagesWeak[realIndex][0])
                    .setContentTitle(messagesWeak[realIndex][0])
                    .setContentText(messagesWeak[realIndex][1])
                    .extend(new NotificationCompat.WearableExtender().setBackground(
                            BitmapFactory.decodeResource(context.getResources(), R.mipmap.social_weak)
                    ));

            builder.setVibrate(vibrationsNone2[realIndex]);
            builder.setAutoCancel(true);

            Log.v("mth", "all: " + temp.getAll().toString());
            Log.v("mth", "title: " + messagesWeak[realIndex][0]);
            Log.v("mth", "text: " + messagesWeak[realIndex][1]);
        } else if (cellNum == 5) {
            // CELL 6: vibrationsNone3, messagesStrong
            builder.setSmallIcon(R.mipmap.icon_strong)
                    .setTicker(messagesStrong[realIndex][0])
                    .setContentTitle(messagesStrong[realIndex][0])
                    .setContentText(messagesStrong[realIndex][1])
                    .extend(new NotificationCompat.WearableExtender().setBackground(
                            BitmapFactory.decodeResource(context.getResources(), R.mipmap.social_strong)
                    ));

            builder.setVibrate(vibrationsNone3[realIndex]);
            builder.setAutoCancel(true);

            Log.v("mth", "all: " + temp.getAll().toString());
            Log.v("mth", "title: " + messagesStrong[realIndex][0]);
            Log.v("mth", "text: " + messagesStrong[realIndex][1]);
        }

        Notification notification= builder.build();
        manager.notify(0, notification);
    }
}
