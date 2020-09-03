package com.example.travelbot;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private ListView mListView;
    private ImageButton mSendButton;
    private EditText mMessageEditText;

    public Bot mBot;
    public static Chat mChat;
    private MessagesAdapter mAdapter;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.btn_send){
                if (mChat == null) {
                    Toast.makeText(MainActivity.this, "The bot requires storage permissions.", Toast.LENGTH_SHORT).show();
                    setupBot();
                    return;
                }

                String message = mMessageEditText.getText().toString();
                String response = mChat.multisentenceRespond(mMessageEditText.getText().toString());
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                mAdapter.add(new Message(message, true));
                mAdapter.add(new Message(response, false));
                mMessageEditText.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        mAdapter = new MessagesAdapter(this, new ArrayList<Message>());
        mListView.setAdapter(mAdapter);

        mSendButton.setOnClickListener(mOnClickListener);
        setupBot();
    }

    private void setupBot() {
        FileUtils.storeAiml(this);
        mBot = new Bot("Robot", MagicStrings.root_path, "chat");
        mChat = new Chat(mBot);
    }


    private void findViews() {
        mListView = findViewById(R.id.listView);
        mSendButton =  findViewById(R.id.btn_send);
        mMessageEditText = findViewById(R.id.et_message);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults.length > 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    setupBot();
            }
        }
    }
}
