package com.example.mustore;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mustore.adapter.ChatAdapter;
import com.example.mustore.api.ChatApi;
import com.example.mustore.common.Constant;
import com.example.mustore.common.Utils;
import com.example.mustore.model.ChatData;
import com.example.mustore.retrofit.response.UserView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private ImageView back;
    private RecyclerView chatMessage;
    private ImageButton send;
    private EditText content;
    private ShimmerFrameLayout shimmerFrameLayout;
    private String room;
    private ChatAdapter chatAdapter;

    long u1,u2;

    private List<ChatData> chatData=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        init();
        onConnectSocket();
    }
    private void init(){
        Intent intent=getIntent();
        UserView userView= (UserView) intent.getSerializableExtra("user_chat");
        back=findViewById(R.id.imgBack);
        chatMessage=findViewById(R.id.chatRecyclerView);
        send=findViewById(R.id.btnSendMessage);
        content=findViewById(R.id.etMessage);
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        shimmerFrameLayout.startShimmerAnimation();
        chatAdapter=new ChatAdapter(this,ChatActivity.this);
         u1= Utils.getUserId(this);
        u2=userView.getId();
        fetchMessage(u1, u2);
        chatAdapter.setChatData(chatData);
        chatMessage.setAdapter(chatAdapter);
        chatMessage.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        onClickSendMessage();
        onClick();
        System.out.println("ADMIN "+userView.getRole());

    }
    private void fetchMessage(Long u1,Long u2){
        ChatApi.chatApi.getChatMessage(u1,u2).enqueue(new Callback<List<ChatData>>() {
            @Override
            public void onResponse(Call<List<ChatData>> call, Response<List<ChatData>> response) {
                chatData=response.body();
                chatAdapter.setChatData(chatData);
                room=chatData.get(0).getRoom();
                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmerAnimation();
                chatMessage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Call<List<ChatData>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchMessage(u1,u2);
    }

    private Socket socket;

    private void onConnectSocket(){
        {
            try {
                Long userId=Utils.getUserId(this);
                String uri=Constant.socketChat+"?"+String.format("userId=%s",userId);
                socket=  IO.socket(uri);
                socket.connect();
                onListingMessage();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void onListingMessage(){
        socket.on("message", args -> runOnUiThread(() -> {
                ChatData chatData1=null;
                ObjectMapper  objectMapper=new ObjectMapper();
            try {
                chatData1=objectMapper.readValue(args[0].toString(),ChatData.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            if(chatData1!=null){
                chatData.add(chatData1);
                chatAdapter.setChatData(chatData);
            }
        }));
    }
/*    {
        "senderId": 4,
            "reciverId": 1,
            "content": "Hello, ban yeu",
            "room": "1"
    }*/
    private void sendMessageChat() throws JSONException {
        String contentMess =content.getText().toString();
        content.setText("");
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("senderId",u1);
        jsonObject.put("reciverId",u2);
        jsonObject.put("content",contentMess);
        jsonObject.put("room",room);
        socket.emit("chat",jsonObject);
        chatData.add(new ChatData(u1,u2,contentMess,room));
        chatAdapter.setChatData(chatData);
    }

    private void onClickSendMessage(){
        send.setOnClickListener(view -> {
            try {
                sendMessageChat();
                hideSoftKeyboard(ChatActivity.this);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    private void onClick(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ChatActivity.this,UserChatActivity.class);
                startActivity(intent);
            }
        });
    }
}
