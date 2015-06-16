package com.example.root.push_bullet;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pushbullet.android.extension.MessagingExtension;
import de.greenrobot.event.EventBus;

import java.util.*;

public class LaunchActivity extends Activity {

    // Fakes the SMS provider for this sample, using a static like this is actually a bad idea but keeps things simple
    public static final Map<String, TextMessage> sMessages = new HashMap<String, TextMessage>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.main);

        // Simulate the arrival of a couple of text messages

        final TextMessage message0 = new TextMessage("John Doe", "A sample message at " + System.currentTimeMillis());
        final TextMessage message1 = new TextMessage("Jane Person", "Another message at " + System.currentTimeMillis());

        final List<TextMessage> messages = new ArrayList<TextMessage>(2);
        messages.add(message0);
        messages.add(message1);

        updateNotification(this, messages);

        for (int i = 0; i < messages.size(); i++) {
            final TextMessage message = messages.get(i);
            final String conversationIden = message.sender;

            sMessages.put(conversationIden, message);

            MessagingExtension.mirrorMessage(this, conversationIden, message.sender, message.message,
                    BitmapFactory.decodeResource(this.getResources(), android.R.drawable.ic_dialog_alert), null, 0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        EventBus.getDefault().unregister(this);
    }

    public static void updateNotification(final Context context, final Collection<TextMessage> messages) {
        final NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (messages == null || messages.size() == 0) {
            notificationManager.cancel(0);
            return;
        }

        final Notification.Builder builder = new Notification.Builder(context)
                .setSmallIcon(android.R.drawable.ic_delete)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true)
                .setContentTitle(messages.size() + " New Messages")
                .setContentText(TextUtils.join(", ", messages));

        final Notification.InboxStyle style = new Notification.InboxStyle();

        for (final TextMessage message : messages) {
            style.addLine(message.sender + " - " + message.message);
        }

        builder.setStyle(style);

        notificationManager.notify(0, builder.build());
    }

    public static class TextMessage {
        public final String sender, message;

        public TextMessage(final String sender, final String message) {
            this.sender = sender;
            this.message = message;
        }

        @Override
        public String toString() {
            return this.sender;
        }
    }

    public void onEventMainThread(final SampleMessagingExtension.ReplyEvent e) {
        final ViewGroup root = (ViewGroup) this.findViewById(R.id.root);

        final TextView textView = new TextView(root.getContext());
        textView.setText(e.conversationIden + ": " + e.message);

        root.addView(textView);
    }
}


