package com.tamil.regex;

/**
 *
 * Author : Tamilselvan Teivasekamani
 *
 * Description : Class to check password with  a digit, a lowercase, a upper case, one special char from @#$%^&+= and limited to 7 to 15 chars.
 *
 */

public class PasswordCheck {

    public static void main(String[] args) {
        String passwd = "TestMypass123$";
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{7,15}";
        if (passwd.matches(pattern)) {
            System.out.println("Password Matches");
        } else {
            System.out.println("Password not matches, should contains a digit, a lowercase, a upper case, one special char from @#$%^&+= and limited to 7 to 15 chars.
        }
    }
}