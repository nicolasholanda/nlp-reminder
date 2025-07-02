package com.github.nicolasholanda.util;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ParserUtil {

    private static final Parser parser = new Parser();

    public static List<DateGroup> parseDates(String input) {
        return parser.parse(input);
    }

    public static List<Date> filterFutureDates(List<DateGroup> groups) {
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

    public static String extractTask(String input, List<DateGroup> groups) {
        String task = input;
        for (DateGroup group : groups) {
            String dateText = group.getText();
            task = task.replace(dateText, "").trim();
        }
        return task;
    }
}
