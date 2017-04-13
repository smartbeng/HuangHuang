package com.lansum.lansumhh.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MaiBenBen on 2017/4/12.
 *
 * 文件存储（ShredPreference）包装类
 *
 */

public class SPHelper {
    public static final String SP_NAME = "share_data";

    private static SharedPreferences mPreferences;


    public SPHelper() {
    }


    private static SharedPreferences getSharedPreference() {
        if (mPreferences == null) {
            mPreferences = AppHolder.getContext().getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mPreferences;
    }


    /**
     * 使用 SharedPreferences 存储数据
     *
     * @param key
     * @param data
     */
    public static boolean save(String key, Object data) {
        SharedPreferences sharedPreference = getSharedPreference();
        SharedPreferences.Editor editor = sharedPreference.edit();
        // 根据传递的数据类型 动态判断存储的数据类型
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        }
        return editor.commit();
    }

    /**
     * 使用 SharedPreferences 获取数据
     *
     * @param key
     * @param defValue
     * @return
     */
    public static Object get(String key, Object defValue) {
        SharedPreferences sharedPreference = getSharedPreference();
        Object o = null;
        // 根据传递的默认取值 动态判断获取的数据
        if (defValue instanceof String) {
            o = sharedPreference.getString(key, (String) defValue);
        } else if (defValue instanceof Integer) {
            o = sharedPreference.getInt(key, (int) defValue);
        } else if (defValue instanceof Float) {
            o = sharedPreference.getFloat(key, (Float) defValue);
        } else if (defValue instanceof Boolean) {
            o = sharedPreference.getBoolean(key, (Boolean) defValue);
        } else if (defValue instanceof Long) {
            o = sharedPreference.getLong(key, (Long) defValue);
        }

        return o;
    }

    /**
     * 根据指定的key 删除数据
     *
     * @param key
     */
    public static void remove(String key) {
        getSharedPreference().edit().remove(key).commit();

    }

    /**
     * 删除 SharedPreferences 中所有的数据
     */
    public static void clear() {
        getSharedPreference().edit().clear().commit();
    }
}
