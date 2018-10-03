package com.example.nisarga.testapplication;

import android.provider.BaseColumns;

public class DataBaseSchema implements BaseColumns
{


    public static final String TABLE_NAME="contacts";
    public static final String ATTR1_NAME="name";
    public static final String ATTR2_PHONE_NUMBER="phonenumber";
    public static final String ATTR3_PRIORITY="priority";

    public static final String TABLE_NAME_MESSAGE="message";
    public static final String ATTR1_MESSAGE="defaultMessage";

}
