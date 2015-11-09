package com.example.root.push_bullet;

import com.pushbullet.android.extension.MessagingExtension;

public class SampleMessagingExtension extends MessagingExtension {
    private static final String TAG = "SampleMessagingExtension";

    @Override
    protected void onMessageReceived(final String conversationIden, final String message) {

        //Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
//       EventBus.getDefault().post(new ReplyEvent(conversationIden, message));
         LaunchActivity f=new LaunchActivity();
        f.onEventMainThread(new ReplyEvent(conversationIden, message));


    }

    @Override
    protected void onConversationDismissed(final String conversationIden) {
       // Log.i(TAG, "Pushbullet MessagingExtension: onConversationDismissed(" + conversationIden + ")");

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

