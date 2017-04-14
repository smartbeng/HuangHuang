package com.lansum.lansumhh.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MaiBenBen on 2017/4/14.
 */

public class CookieUtil {
    public String GetCookieParamInt(String CookieStr, String key) {
        String urlDeCode = null;
        try {
            urlDeCode = URLDecoder.decode(CookieStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            return "";
        }
        Pattern p = Pattern.compile(key + "\"\\:\"?([^\",}]+)", Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(urlDeCode);
        if (m.find()) {
            String UserName = m.group(1);
            UserName = UserName.replaceAll("%40", "@");
            return UserName;
        } else {
            return "";
        }

    }

}
