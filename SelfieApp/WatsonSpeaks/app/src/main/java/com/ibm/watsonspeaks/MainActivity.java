package com.ibm.watsonspeaks;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    EditText editText;
    EditText editText2;
    EditText editText3;

    Button button;
    Button button2;
    Button button3;

    int whichButton;

    StreamPlayer streamPlayer;

    private TextToSpeech initTextToSpeechService() {
        TextToSpeech service = new TextToSpeech();
        String username = "<Watson API user>";
        String password = "<Watson API password>";
        service.setUsernameAndPassword(username, password);
        return service;
    }

    private class WatsonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... textsToAnalyse) {

            if (whichButton == 1) {
                System.out.println(editText.getText());
                System.out.println(editText.getText());
            } else if (whichButton == 2) {
                System.out.println(editText2.getText());
            } else if (whichButton == 3) {
                System.out.println(editText3.getText());
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("what is happening inside a thread - we are running Watson service");
                }
            });


            String textToSay = "Hallo it is IBM Watson";
            TextToSpeech textService = initTextToSpeechService();
            streamPlayer = new StreamPlayer();



            if (whichButton == 1) {
                textToSay = String.valueOf(editText.getText());
            } else if (whichButton == 2) {
                textToSay = String.valueOf(editText2.getText());
            } else {
                textToSay = String.valueOf(editText3.getText());
            }
            streamPlayer.playStream(textService.synthesize(textToSay, Voice.EN_MICHAEL).execute());
            return "text to speech done";

        }

        //setting the value of UI outside of the thread
        @Override
        protected void onPostExecute(String result) {
            textView.setText("The status is: " + result);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        whichButton = 0;

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);

        editText2 = (EditText) findViewById(R.id.editText2);
        button2 = (Button) findViewById(R.id.button2);

        editText3 = (EditText) findViewById(R.id.editText3);
        button3 = (Button) findViewById(R.id.button3);

        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichButton = 1;
                System.out.println("Logging to the console that the button pressed for the text : " + editText.getText());
                textView.setText("Displaying at UI the sentiment to be checked for : " + editText.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichButton = 2;
                System.out.println("Logging to the console that the button pressed for the text : " + editText2.getText());
                textView.setText("Displaying at UI the sentiment to be checked for : " + editText2.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});
            }

        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whichButton = 3;
                System.out.println("Logging to the console that the button pressed for the text : " + editText3.getText());
                textView.setText("Displaying at UI the sentiment to be checked for : " + editText3.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});
            }

        });

    }
}
