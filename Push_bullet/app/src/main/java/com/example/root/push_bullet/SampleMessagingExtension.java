package com.example.root.push_bullet;
import android.util.Log;

import com.example.root.push_bullet.LaunchActivity;
import com.pushbullet.android.extension.MessagingExtension;
import de.greenrobot.event.EventBus;

public class SampleMessagingExtension extends MessagingExtension {
    private static final String TAG = "SampleMessagingExtension";

    @Override
    protected void onMessageReceived(final String conversationIden, final String message) {
        Log.i(TAG, "Pushbullet MessagingExtension: onMessageReceived(" + conversationIden + ", " + message + ")");

        EventBus.getDefault().post(new ReplyEvent(conversationIden, message));
    }

    @Override
    protected void onConversationDismissed(final String conversationIden) {
        Log.i(TAG, "Pushbullet MessagingExtension: onConversationDismissed(" + conversationIden + ")");

        LaunchActivity.sMessages.remove(conversationIden);

        LaunchActivity.updateNotification(this, LaunchActivity.sMessages.values());
    }

    public static class ReplyEvent {
        public final String conversationIden, message;

        public ReplyEvent(final String conversationIden, final String message) {
            this.conversationIden = conversationIden;
            this.message = message;
        }
    }
}

