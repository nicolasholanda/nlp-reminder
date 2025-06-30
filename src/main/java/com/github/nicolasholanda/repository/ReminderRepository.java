package com.github.nicolasholanda.repository;

import com.github.nicolasholanda.model.Reminder;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReminderRepository {
    private static final String DB_URL = "jdbc:sqlite:reminders.db";
    
    public ReminderRepository() {
        initDatabase();
    }
    
    private void initDatabase() {
        String sql = """
            CREATE TABLE IF NOT EXISTS reminders (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                task TEXT NOT NULL,
                due_date DATETIME NOT NULL,
                created_at DATETIME NOT NULL,
                completed BOOLEAN DEFAULT FALSE
            )
            """;
            
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    public void saveReminder(Reminder reminder) {
        String sql = "INSERT INTO reminders (task, due_date, created_at, completed) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, reminder.getTask());
            pstmt.setTimestamp(2, new Timestamp(reminder.getDueDate().getTime()));
            pstmt.setTimestamp(3, new Timestamp(reminder.getCreatedAt().getTime()));
            pstmt.setBoolean(4, reminder.isCompleted());
            
            pstmt.executeUpdate();
            System.out.println("Reminder saved successfully!");
            
        } catch (SQLException e) {
            System.err.println("Error saving reminder: " + e.getMessage());
        }
    }
    
    public List<Reminder> getPendingReminders() {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT * FROM reminders WHERE completed = FALSE ORDER BY due_date ASC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Reminder reminder = new Reminder(
                    rs.getLong("id"),
                    rs.getString("task"),
                    rs.getTimestamp("due_date"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("completed")
                );
                reminders.add(reminder);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving reminders: " + e.getMessage());
        }
        
        return reminders;
    }
    
    public List<Reminder> getDueReminders() {
        List<Reminder> reminders = new ArrayList<>();
        String sql = "SELECT * FROM reminders WHERE completed = FALSE AND due_date <= ? ORDER BY due_date ASC";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setTimestamp(1, new Timestamp(new Date().getTime()));
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Reminder reminder = new Reminder(
                    rs.getLong("id"),
                    rs.getString("task"),
                    rs.getTimestamp("due_date"),
                    rs.getTimestamp("created_at"),
                    rs.getBoolean("completed")
                );
                reminders.add(reminder);
            }
            
        } catch (SQLException e) {
            System.err.println("Error retrieving due reminders: " + e.getMessage());
        }
        
        return reminders;
    }
    
    public void markCompleted(Long id) {
        String sql = "UPDATE reminders SET completed = TRUE WHERE id = ?";
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
            
        } catch (SQLException e) {
            System.err.println("Error marking reminder as completed: " + e.getMessage());
        }
    }
} 