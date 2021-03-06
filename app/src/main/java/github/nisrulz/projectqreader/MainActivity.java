/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package github.nisrulz.projectqreader;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.Button;
import android.widget.TextView;
import github.nisrulz.qreader.QRDataListener;
import github.nisrulz.qreader.QREader;

public class MainActivity extends AppCompatActivity {

  private SurfaceView surfaceView;
  private TextView textView_qrcode_info;
  private Button btn_toggle;
  private boolean isRunning = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    surfaceView = (SurfaceView) findViewById(R.id.camera_view);
    textView_qrcode_info = (TextView) findViewById(R.id.code_info);

    QREader.getInstance().setUpConfig(new QRDataListener() {
      @Override public void onDetected(final String data) {
        Log.d("QREader", "Value : " + data);
        textView_qrcode_info.post(new Runnable() {
          @Override public void run() {
            textView_qrcode_info.setText(data);
          }
        });
      }
    });

    QREader.getInstance().init(this, surfaceView);
  }

  @Override protected void onResume() {
    super.onResume();

    QREader.getInstance().start();
    isRunning = true;
  }

  @Override protected void onPause() {
    super.onPause();

    QREader.getInstance().stop();
    isRunning = false;
  }

  @Override protected void onDestroy() {
    super.onDestroy();

    QREader.getInstance().releaseAndCleanup();
    isRunning = false;
  }
}
