package com.fss.mobiletrading.object;

public class LiveChatConversation_Item {
	public static final int RECEIVER_MESSAGE = 1;
	public static final int SENDER_MESSAGE = 0;
	public String Content;
	public String Date;
	public int location;

	public LiveChatConversation_Item(String paramString1, String paramString2,
			int paramInt) {
		this.Content = paramString1;
		this.Date = paramString2;
		this.location = paramInt;
	}
}

/*
 * Location: C:\Users\Admin\Desktop\tools\classes-dex2jar.jar Qualified Name:
 * com.fss.adapter.LiveChatConversation_Item JD-Core Version: 0.6.0
 */