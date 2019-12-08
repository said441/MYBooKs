package com.example.mybooks.Prevalent;

import com.example.mybooks.Model.User;

public class Prevalent {
    private static User currentOnlineUser;

    private static final String UserPhoneKey = "UserPhone";
    private static final String UserPasswordKey = "UserPassword";

    public static User getCurrentOnlineUser() {
        return currentOnlineUser;
    }

    public static void setCurrentOnlineUser(User currentOnlineUser) {
        Prevalent.currentOnlineUser = currentOnlineUser;
    }

    public static String getUserPhoneKey() {
        return UserPhoneKey;
    }

    public static String getUserPasswordKey() {
        return UserPasswordKey;
    }
}
