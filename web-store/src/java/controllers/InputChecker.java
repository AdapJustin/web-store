/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Godwin Sabigan
 */

public class InputChecker {
   
    public void InputChecker(){
        
    }
    //Checks if email is a valid email
    public boolean validateEmail(String email){
        String emailRegex =  "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern emailPat= Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = emailPat.matcher(email);
        
        return matcher.find();
    }
    //Password must be 8-16 characters and contains an Upper and lower case letters and numbers
    public boolean validatePassword(String password){
        if(password.length()<8 || password.length()>16){
            return false;
        }
        
        boolean hasCapital=false;
        boolean hasLowerCase=false;
        boolean hasNumber=false;
        
        for(int i = 0; i<password.length();i++){
            char current = password.charAt(i);
            if(isNumber(current))
                hasNumber=true;
            if(isLower(current))
                hasLowerCase=true;
            if(isUpper(current))
                hasCapital=true;
            
            
        }
        if(hasNumber && hasCapital && hasLowerCase)
            return true;
        return false;
    }
    public static boolean isNumber(char c){
            return(c>='0' && c<='9');
        }
    public static boolean isLower(char c){
        return(c>='a' && c<='z');
    }
    public static boolean isUpper(char c){
        return(c>='A' && c<='Z');
    }
}


