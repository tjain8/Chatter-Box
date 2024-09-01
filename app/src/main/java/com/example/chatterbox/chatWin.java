package com.example.chatterbox;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class chatWin extends AppCompatActivity {

    String recieverimg;
    String recieverUid;
    RecyclerView mmessagesAdpter;
    String recieverName;
    String SenderUID;
    CircleImageView profile;
    TextView recieverNamee;
    CardView sendbtn;
    EditText txtmsg;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    public static String sendImg;
    public static String recieverImgg;
    ArrayList<msgModelClass> messagesArrayList;
    messagesAdapter mmessageAdapter;

    String senderRoom;
    String recieverRoom;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_win);
        database = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        recieverName = getIntent().getStringExtra("nameeee");
        recieverimg = getIntent().getStringExtra("recieverImg");
        recieverUid = getIntent().getStringExtra("uid");

        messagesArrayList = new ArrayList<>();

        sendbtn = findViewById(R.id.sendbtnn);
        txtmsg = findViewById(R.id.textmsgg);
        profile = findViewById(R.id.profilerg0);
        recieverNamee = findViewById(R.id.recievername);

        mmessagesAdpter = findViewById(R.id.msgadpter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        mmessagesAdpter.setLayoutManager(linearLayoutManager);
        mmessageAdapter = new messagesAdapter(chatWin.this,messagesArrayList);
        mmessagesAdpter.setAdapter(mmessageAdapter);

        Picasso.get().load(recieverimg).into(profile);
        recieverNamee.setText(""+recieverName);

        SenderUID = firebaseAuth.getUid();

        senderRoom = SenderUID+recieverUid;
        recieverRoom = recieverUid+SenderUID;

        DatabaseReference reference = database.getReference().child("user").child(firebaseAuth.getUid());
        DatabaseReference chatreference = database.getReference().child("user").child(senderRoom).child("messages");

        chatreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    msgModelClass messages=dataSnapshot.getValue(msgModelClass.class);
                    Log.d("MessageRetrieved","Message:"+messages.getMessage()); //new change
                    messagesArrayList.add(messages);
                }
                mmessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sendImg = snapshot.child("profilepic").getValue().toString();
                recieverImgg = recieverimg;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = txtmsg.getText().toString();
                if(message.isEmpty()){
                    Toast.makeText(chatWin.this,"Enter The Message First",Toast.LENGTH_LONG).show();
                    return; //new change
                }
                txtmsg.setText("");
                Date date = new Date();
                msgModelClass messagess =new msgModelClass(message,SenderUID,date.getTime());
                database = FirebaseDatabase.getInstance();
                database.getReference().child("chats").child(senderRoom).child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        database.getReference().child("chats").child(recieverRoom).child("messages").push().setValue(messagess).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                            }
                        });
                    }
                });
            }
        });
    }
}