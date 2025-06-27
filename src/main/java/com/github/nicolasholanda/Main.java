package com.github.nicolasholanda;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Provide at least one argument");
            System.exit(1);
        }

        String input = String.join(" ", args);
        System.out.println("Input: " + input);

        Parser parser = new Parser();
        List<DateGroup> groups = parser.parse(input);
        
        if (groups.isEmpty()) {
            System.out.println("No dates found");
            System.exit(1);
        }


        List<Date> futureDates = filterFutureDates(groups);
        
        if (futureDates.isEmpty()) {
            System.out.println("No future dates found");
            System.exit(1);
        }

        Date selectedDate;
        if (futureDates.size() > 1) {
            System.out.println("\nMultiple future dates found:");
            for (int i = 0; i < futureDates.size(); i++) {
                System.out.println("  " + (i + 1) + ". " + futureDates.get(i));
            }
            
            selectedDate = promptUserForDateSelection(futureDates);
        } else {
            selectedDate = futureDates.get(0);
        }

        String task = extractTask(input, groups);
        
        System.out.println("\nFinal result:");
        System.out.println("Task: " + task);
        System.out.println("Date: " + selectedDate);
    }
    
    private static Date promptUserForDateSelection(List<Date> dates) {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Select a date (1-" + dates.size() + "): ");
                
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if (choice >= 1 && choice <= dates.size()) {
                        return dates.get(choice - 1);
                    }
                }
                
                System.out.println("Invalid choice. Please enter a number between 1 and " + dates.size());
                scanner.nextLine();
            }
        }
    }
    
    private static List<Date> filterFutureDates(List<DateGroup> groups) {
        List<Date> futureDates = new ArrayList<>();
        Date now = new Date();
        
        for (DateGroup group : groups) {
            for (Date date : group.getDates()) {
                if (date.after(now)) {
                    futureDates.add(date);
                }
            }
        }
        
        return futureDates;
    }
    
    private static String extractTask(String input, List<DateGroup> groups) {
        String task = input;
        
        for (DateGroup group : groups) {
            String dateText = group.getText();
            task = task.replace(dateText, "").trim();
        }
        
        return task;
    }
}