##Watson Speech to text app

The voice user interface is a great element for enhancing IoT, Robots, or your vehicle user interface while driving (not able to look away from the road ahead). IBM is giving you could extend your application with such an interface in about 10 minutes.

IBM Watson Text-To-Speech service is available as IBM cognitive service for us to be used from IBM Bluemix platform.

Prerequisites: Have you got your coffee machine and coffee grains ready for grinding? Have you ever done it before - check the previous espresso lab!
– The coffee machine – get yourself the Android Studio, and install it.
– Get yourself a coffee grains – either a real Android device (a smartphone or a tablet), or a virtual one
– the coffee shop vibe – an account with IBM Bluemix for IBM Watson cognitive services
I explain at the end of this blogpost on how to get straight with prereqs.
<blockquote>I hope you got your coffee machine, and your coffee powder are ready. Let’s brew your Android espresso.</blockquote>
<b>Here goes the steps:</b>
<ul>
	<li>Create a typical Android application in Java.</li>
	<li>Add the Watson lib: Watson-Developer-Cloud SDK for Java.</li>
	<li>Instantiate the Bluemix Watson service and get the username and password to it.</li>
	<li>Add some little code in your Android app to invoke the cognitive service.</li>
	<li>And taste the app – or test the espresso.</li>
</ul>
<b>Create a typical Android application in Java.</b>
You’ll build a simple application with a button, an editable text field, and an output field.
The idea - create the GUI First, create a simple single view application with a graphical user interface (GUI) that includes a text field, a label, and a button. When a user presses the button, the text in the text field is to be sent to Watson service (that would be implemented in the next steps), which analyzes it and returns an JSON with analysis results that is shown in the output - view or label field.

For now you would take the text in the text field and simply echo it in the label field when the button is pressed.
[The video shows how the UI is being built.](https://www.youtube.com/VT9GjjlMHOo)

Start a new project. The project is to be run on phones and tablets. Add an empty activity. Keep the defaults for the MainActivity. Update the design in the MainActivity.xml file. Add EditText field. Add a button and resize the ViewText field. Finally wire the UI with the code.

Add the parameters of the buttons:
<pre>public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;
</pre>
Add the async task for launching the operations when a button is pressed:
<pre>    private class WatsonTask extends AsyncTask&lt;String, Void, String&gt; {

        @Override
        protected String doInBackground(String... textToSpeak) {
</pre>
this insert is good to pass the parameters to UI from inside of the async thread:
<pre>            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("running the Watson thread");
                }
            });
</pre>
this is just a simple return (we will add the IBM Watson call later):
<pre>            return "text to speech done";
        }

</pre>
When done we will set the Text View with the status:
<pre>        @Override
        protected void onPostExecute(String result) {
            textView.setText("TTS status: " + result);
        }

    }
</pre>
Setting up the UI parameters:
<pre>    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);
</pre>
Add the on click listener:
<pre>        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("the text to speech: " + editText.getText());
                textView.setText("TTS: " + editText.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});


            }
        });
    }
}
</pre>
And then we would see the results:

Run the test to see if the fields work as planned and you see the debug println in the console as well as at the ViewText field.

The entire code for the UI is here:
<pre>package com.ibm.watsonvoicetts;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    private class WatsonTask extends AsyncTask&lt;String, Void, String&gt; {
        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("running the Watson thread");
                }
            });

            return "text to speech done";
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText("TTS status: " + result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("the text to speech: " + editText.getText());
                textView.setText("TTS: " + editText.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});
            }
        });
    }
}
</pre>
<b>Add the Watson lib: Watson-Developer-Cloud SDK for Java.</b>
Check the detaily described steps here at <a href="http://ibm.biz/Bdr6vv" target="_blank">Watson Developer Cloud SDK for JAVA</a>

[Here comes entire video of the below described steps:](https://www.youtube.com/f_yEKaM4xy4)

Add also the classes for the Android Sound.
Watson Developer Cloud SDK for Android classes. Alternatively copy over the classes (as it was shown on the provided video).

Added classes of Watson Developer Cloud SDK for Android
Now you can test if that setup works - try to import Watson.

<b>Instantiate the Bluemix Watson service and get the key token to it.</b>
<blockquote>Create a Watson service and get the username and password for it</blockquote>
Now, you create the Watson Text to Speech service. From the IBM Bluemix catalog, click Watson &gt; Text to Speech &gt; Create.
Go to IBM Bluemix and add/create the IBM Watson Text to Speech service - click <i>Create</i> the service.

Be sure to use a static API username and password. And copy over the credentials for the service. You would need them in the app later.

Now we need to allow to call Watson service from our app. Double click <code>/app/manifest/AndroidManifest.xml</code> in the view Android:
<script src="https://gist.github.com/blumareks/a0e33dff2e4c3e1a6375a75ef2e7b144.js"></script>

<b>Final steps</b>

The final steps - first add the imports (they might be added by Android Studio for you when typing the code):
<pre>import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;
</pre>
Just below the existing parameters add StreamPlayer:
<pre>    StreamPlayer streamPlayer;</pre>
Then instantiate the code by adding the username and the password to access Watson services from the app.
<pre>    private TextToSpeech initTextToSpeechService(){
        TextToSpeech service = new TextToSpeech();
        String username = "";
        String password = "";
        service.setUsernameAndPassword(username, password);
        return service;
    }
</pre>
And finally add the call to the Watson TTS service before the return clause:
<pre>            TextToSpeech textToSpeech = initTextToSpeechService();
            streamPlayer = new StreamPlayer();
            streamPlayer.playStream(textToSpeech.synthesize(String.valueOf(editText.getText()), Voice.EN_MICHAEL).execute());

            return "text to speech done";
</pre>
You are ready to run and hear the voice of IBM Watson!

<b>Check the entire code:</b>

```java
package com.ibm.watsonvoicetts;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.android.library.audio.StreamPlayer;
import com.ibm.watson.developer_cloud.text_to_speech.v1.TextToSpeech;
import com.ibm.watson.developer_cloud.text_to_speech.v1.model.Voice;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    EditText editText;
    Button button;

    StreamPlayer streamPlayer;


    private TextToSpeech initTextToSpeechService(){
        TextToSpeech service = new TextToSpeech();
        String username = "";
        String password = "";
        service.setUsernameAndPassword(username, password);
        return service;
    }

    private class WatsonTask extends AsyncTask&lt;String, Void, String&gt; {
        @Override
        protected String doInBackground(String... textToSpeak) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText("running the Watson thread");
                }
            });

            TextToSpeech textToSpeech = initTextToSpeechService();
            streamPlayer = new StreamPlayer();
            streamPlayer.playStream(textToSpeech.synthesize(String.valueOf(editText.getText()), Voice.EN_MICHAEL).execute());

            return "text to speech done";
        }

        @Override
        protected void onPostExecute(String result) {
            textView.setText("TTS status: " + result);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("the text to speech: " + editText.getText());
                textView.setText("TTS: " + editText.getText());

                WatsonTask task = new WatsonTask();
                task.execute(new String[]{});


            }
        });
    }
}
```
<b>Prerequisites:</b>
– <a href="https://developer.android.com/studio/index.html" target="_blank">getting the Android Studio, and install it, run it – for windows and/or mac</a>
– <a href="https://developer.android.com/training/basics/firstapp/running-app.html" target="_blank">Get yourself either a real Android device (a smartphone or a tablet and the data cable), and enable the device for development – follow the instruction for real device</a>
– <a href="https://developer.android.com/studio/run/emulator.html" target="_blank">alternatively or in addition to a real device you might want to setup a virtual Android device</a>
– <a href="http://ibm.biz/Bdrp22" target="_blank">setup an account with IBM Bluemix for IBM Watson cognitive services – free of charge for 30 days</a>

Please follow me on Twitter: <a href="https://twitter.com/blumareks" target="_blank">@blumareks</a>, and check my blog on <a href="http://blumareks.blogspot.com/" target="_blank">blumareks.blogspot.com</a>
