package com.example.flutternativesocketclient;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class TimerService extends IntentService {

    EchoWebSocketListener listener;
    static WebSocket ws;

    String url = "ws://192.168.15.33:8080/oncallWS";

    private OkHttpClient client = new OkHttpClient();

    private boolean isAppClosed = false;

    public TimerService() {
        super("Timer service ");
    }

    public static void sendData(String data) {
        ws.send(data);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("timer", "timer service has started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        /*while(intent == null) {
            Log.i("TIMER", "NULO CARALHO");
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Bundle bundle = new Bundle();
            bundle.putString("message", "counting done...");
            ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
            resultReceiver.send(1234, bundle);
        }
        Log.i("TIMER", "ONLINE");*/
        /*if(intent == null) {
            Log.i("TIMER", "NULO CARALHO");
            isAppClosed = true;
            //while (isAppClosed) {
                Log.i("Timer", "app closed");
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Bundle bundle = new Bundle();
                bundle.putString("message", "counting done...");
                ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
                resultReceiver.send(1234, bundle);
                return;
            //}
        } else {
            Log.i("TIMER", "NAO NULO CARALHO");
            isAppClosed = false;
            //while(!isAppClosed) {
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.i("Timer", "app openned");
                Bundle bundle = new Bundle();
                bundle.putString("message", "counting done...");
                ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
                resultReceiver.send(1234, bundle);
            //}
        }*/

//        if(intent == null) { //program has finished
//            isAppClosed = true;
//           //while (isAppClosed) {
//               int time = 5;
//               for(int i = 0; i < time; i++) {
//                   Log.i("timer", "i (intent is null) = " + i);
//                   try {
//                       Thread.sleep(1000);
//                   } catch (Exception e) {
//                       e.printStackTrace();
//                   }
//
//               }
//
//               NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
//               builder.setContentText("Timer done");
//               builder.setContentTitle("Title");
//               builder.setSmallIcon(R.mipmap.ic_launcher);
//
//               NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
//               manager.notify(1, builder.build());
//
//               return;
//           //}
//        }
//        isAppClosed = false;
//        int time = intent.getIntExtra("time", 0);
//        ResultReceiver resultReceiver = intent.getParcelableExtra("receiver");
//        for(int i = 0; i < time; i++) {
//            Log.i("timer", "i (intent is not null )= " + i);
//            try {
//                Thread.sleep(1000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }
//        Bundle bundle = new Bundle();
//        bundle.putString("message", "counting done...");
//        resultReceiver.send(1234, bundle);

        start();
    }

    private final class EchoWebSocketListener extends WebSocketListener {
        private static final int NORMAL_CLOSURE_STATUS = 1000;

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            //webSocket.send("Hello, it's SSaurel !");
            //webSocket.send("What's up ?");
            //webSocket.send(ByteString.decodeHex("deadbeef"));
            webSocket.send("{\"type\":\"TEST\",\"data\":\"Connected\"}");
            //webSocket.close(NORMAL_CLOSURE_STATUS, "Goodbye !");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            MainActivity.dataFromWebSocket = text;
            output("Receiving : " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            output("Receiving bytes : " + bytes.hex());
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            webSocket.close(NORMAL_CLOSURE_STATUS, null);
            output("Closing : " + code + " / " + reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            output("Error : " + t.getMessage());
        }
    }

    private void start() {
        Request request = new Request.Builder().url(url)
                .addHeader("user_id", "3")
                .addHeader("username", "layunne3")
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYXl1bm5lMyIsImV4cCI6MTU0Mzc5OTMyM30.J1VZxpB4pzusrdJJ8h9FllTAA8cYBmQrSeceympmw7MXtpxC5GKa_Kp7j6N2h0zFJ_cEk7B46D4HI61hPIP3SQ") .build();
        listener = new EchoWebSocketListener();
        ws = client.newWebSocket(request, listener);
        client.dispatcher().executorService().shutdown();
    }

    private void output(final String txt) {
//        runOnUiThread(new Runnable() {
//            @Override

//            public void run() {
//                Log.i("MAIN", txt);
//            }
//        });
//    }\
        Log.i("MAIN", txt);
    }


//    @Override
//    public void onTaskRemoved(Intent rootIntent){
//        Intent restartServiceIntent = new Intent(getApplicationContext(), this.getClass());
//        restartServiceIntent.setPackage(getPackageName());
//
//        PendingIntent restartServicePendingIntent = PendingIntent.getService(getApplicationContext(), 1, restartServiceIntent, PendingIntent.FLAG_ONE_SHOT);
//        AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        alarmService.set(
//                AlarmManager.ELAPSED_REALTIME,
//                SystemClock.elapsedRealtime() + 1000,
//                restartServicePendingIntent);
//
//        super.onTaskRemoved(rootIntent);
//    }
}
