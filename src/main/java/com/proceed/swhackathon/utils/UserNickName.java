package com.proceed.swhackathon.utils;

public class UserNickName {

    public static String of(String name) {
        String nickName = "";

        for (int i = 0; i < name.length(); i++) {
            nickName += (i == 0)? name.charAt(0): "*";
        }

        return nickName;
    }
}
