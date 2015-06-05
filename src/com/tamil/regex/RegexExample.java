package com.tamil.regex;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description :
 *
 */

public class RegexExample {
    
	public static void main(String[] args) {
        String regex = "[\\\\!\"#$%&()*+,./:;<=>?@\\[\\]^{|}~]+";
        String realStrings[] = new String[] {"user-name-@test^"};
        Pattern p = Pattern.compile(regex);

        for (String realString : realStrings) {
            Matcher m = p.matcher(realString);
            boolean splCharFound = m.find();
            if (splCharFound) {
                MatchResult result = m.toMatchResult();
                System.out.println(realString + " has a special character, that is [" + realString.charAt(result.start()) + "]");
                System.out.println("result.group() = " + result.group());
                System.out.println("result.groupCount() = " + result.groupCount());

            } else {
                System.out.println(realString + " not have any special character");
            }
        }
    }
}
