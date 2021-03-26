package com.huihe.eg.comm.util;

import com.cy.framework.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ Author     ：zwy
 * @ Date       ：2021/3/3 16:05
 * @ Description：emoji转换类
 * @ since: JDk1.8
 */
public class EmojiUtil {

    /**
     * 将str中的emoji表情转为byte数组
     *
     * @param str
     * @return
     */
    public static String resolveToByteFromEmoji(String str) {
        if (StringUtil.isNotEmpty(str)) {
            Pattern pattern = Pattern
                    .compile("[^(\u2E80-\u9FFF\\w\\s`~!@#\\$%\\^&\\*\\(\\)_+-？（）——=\\[\\]{}\\|;。，、《》”：；“！……’:'\"<,>\\.?/\\\\*)]");
            Matcher matcher = pattern.matcher(str);
            StringBuffer sb2 = new StringBuffer();
            while (matcher.find()) {
                matcher.appendReplacement(sb2, resolveToByte(matcher.group(0)));
            }
            matcher.appendTail(sb2);
            return sb2.toString();
        }
        return str;
    }

    /**
     * 将str中的byte数组类型的emoji表情转为正常显示的emoji表情
     *
     * @param str
     * @return
     */
    public static String resolveToEmojiFromByte(String str) {
        if (StringUtil.isNotEmpty(str)) {
            Pattern pattern2 = Pattern.compile("<:([[-]\\d*[,]]+):>");
            Matcher matcher2 = pattern2.matcher(str);
            StringBuffer sb3 = new StringBuffer();
            while (matcher2.find()) {
                matcher2.appendReplacement(sb3, resolveToEmoji(matcher2.group(0)));
            }
            matcher2.appendTail(sb3);
            return sb3.toString();
        }
        return str;
    }

    private static String resolveToByte(String str) {
        if (StringUtil.isNotEmpty(str)) {
            byte[] b = str.getBytes();
            StringBuffer sb = new StringBuffer();
            sb.append("<:");
            for (int i = 0; i < b.length; i++) {
                if (i < b.length - 1) {
                    sb.append(Byte.valueOf(b[i]).toString()).append(",");
                } else {
                    sb.append(Byte.valueOf(b[i]).toString());
                }
            }
            sb.append(":>");
            return sb.toString();
        }
        return str;
    }

    private static String resolveToEmoji(String str) {
        if (StringUtil.isNotEmpty(str)) {
            if (StringUtil.isNotEmpty(str)) {
                str = str.replaceAll("<:", "").replaceAll(":>", "");
                String[] s = str.split(",");
                byte[] b = new byte[s.length];
                for (int i = 0; i < s.length; i++) {
                    b[i] = Byte.parseByte(s[i]);
                }
                return new String(b);
            }
        }
        return str;
    }
}
