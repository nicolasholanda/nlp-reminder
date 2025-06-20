package com.github.nicolasholanda;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Provide exactly one argument");
            System.exit(1);
        }

        String input = args[0];

        Parser parser = new Parser();
        
        List<DateGroup> groups = parser.parse(input);
        
        if (groups.isEmpty() || groups.getFirst().getDates().isEmpty()) {
            System.out.println("No dates found");
            System.exit(1);
        }

        Date date = groups.getFirst().getDates().getFirst();

        System.out.println(date);
    }
}