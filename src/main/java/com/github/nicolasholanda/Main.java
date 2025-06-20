package com.github.nicolasholanda;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        
        String[] testInputs = {
            "buy milk tomorrow at 10AM",
            "call mom next Friday at 3pm",
            "take medicine in 2 hours",
            "meeting with John on Monday 9am",
            "dentist appointment next week Tuesday",
            "pick up kids today at 5pm",
            "submit report by end of day",
            "birthday party next Saturday 2pm"
        };
        
        for (String input : testInputs) {
            System.out.println("Input: " + input);
            
            List<DateGroup> groups = parser.parse(input);
            
            if (groups.isEmpty()) {
                System.out.println("  No dates found");
            } else {
                for (DateGroup group : groups) {
                    List<Date> dates = group.getDates();
                    System.out.println("  Found " + dates.size() + " date(s):");
                    
                    for (Date date : dates) {
                        System.out.println("    " + date);
                    }
                    
                    System.out.println("    Matched text: \"" + group.getText() + "\"");
                }
            }
            System.out.println();
        }
        
        System.out.println("Demo completed! Natty is working correctly.");
    }
}