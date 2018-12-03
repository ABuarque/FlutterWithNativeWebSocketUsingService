package com.example.flutternativesocketclient;

import android.os.Bundle;
import android.content.Intent;
import io.flutter.app.FlutterActivity;
import io.flutter.plugins.GeneratedPluginRegistrant;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import android.widget.Toast;
import android.util.Log;

public class MainActivity extends FlutterActivity {
  public static String dataFromWebSocket = "nada ainda";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    GeneratedPluginRegistrant.registerWith(this);

    MessageReceiver messageReceiver = new MessageReceiver(new Message());
        //start();
        Intent intent = new Intent(this, TimerService.class);
        intent.putExtra("time", 12);
        intent.putExtra("receiver", messageReceiver);
        startService(intent);
    
    new MethodChannel(getFlutterView(), "test_activity").setMethodCallHandler(
                new MethodCallHandler() {
                    @Override
                    public void onMethodCall(MethodCall call, Result result) {
                        if(call.method.equals("startNewActivity")) {
                            Log.i("NATIVE", "OI");
                            TimerService.sendData("PICA");
                            result.success(dataFromWebSocket);
                        } else {
                          result.notImplemented();
                        }
                    }
                });
  }

  public class Message {
        public void displayMessage(int resultCode, Bundle resultData) {
            String message = resultData.getString("message");
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    }

}
