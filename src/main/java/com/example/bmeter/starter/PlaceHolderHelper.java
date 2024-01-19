package com.example.bmeter.starter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceHolderHelper {

    private static Pattern PATTERN = Pattern.compile("\\{(\\w.*?)\\}");

    protected static List<String> getProperties(String template){
        List<String> properties = new ArrayList<>();
        Matcher matcher = PATTERN.matcher(template);
        while (matcher.find()) {
            properties.add(matcher.group(1));
        }
        return properties;
    }

    protected static String replaceOnce(String template, String placeholder, String replacement) {
        if (template == null) {
            return template;
        }
        int loc = template.indexOf(placeholder);
        if (loc < 0) {
            return template;
        } else {
            return new StringBuffer(template.substring(0, loc)).append(replacement)
                    .append(template.substring(loc + placeholder.length())).toString();
        }
    }
}
