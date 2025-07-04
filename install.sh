#!/bin/bash

# NLP Reminder CLI Installation Script

set -e

echo "NLP Reminder CLI Installation"
echo "=============================="

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REMINDER_SCRIPT="$SCRIPT_DIR/reminder"

# Check if the reminder script exists
if [ ! -f "$REMINDER_SCRIPT" ]; then
    echo "Error: reminder script not found at $REMINDER_SCRIPT"
    exit 1
fi

# Make the reminder script executable
echo "Making reminder script executable..."
chmod +x "$REMINDER_SCRIPT"

# Determine installation directory
if [ "$EUID" -eq 0 ]; then
    # Running as root, install system-wide
    INSTALL_DIR="/usr/local/bin"
    echo "Installing system-wide to $INSTALL_DIR"
else
    # Running as user, install to user's bin directory
    INSTALL_DIR="$HOME/.local/bin"
    echo "Installing to user directory $INSTALL_DIR"
    
    # Create the directory if it doesn't exist
    mkdir -p "$INSTALL_DIR"
fi

# Create symlink
TARGET="$INSTALL_DIR/reminder"
echo "Creating symlink: $TARGET -> $REMINDER_SCRIPT"

if [ -L "$TARGET" ]; then
    echo "Removing existing symlink..."
    rm "$TARGET"
fi

ln -s "$REMINDER_SCRIPT" "$TARGET"

# Check if the installation directory is in PATH
if [[ ":$PATH:" != *":$INSTALL_DIR:"* ]]; then
    echo ""
    echo "⚠️  Warning: $INSTALL_DIR is not in your PATH"
    echo "To add it to your PATH, add this line to your shell profile (~/.bashrc, ~/.zshrc, etc.):"
    echo "  export PATH=\"\$PATH:$INSTALL_DIR\""
    echo ""
    echo "Then restart your terminal or run:"
    echo "  source ~/.bashrc  # or ~/.zshrc"
    echo ""
else
    echo "✓ Installation directory is already in PATH"
fi

echo ""
echo "✓ Installation complete!"
echo ""
echo "You can now use the reminder command:"
echo "  reminder \"buy milk in 2 minutes\""
echo ""
echo "To see all available options:"
echo "  reminder --help"
echo ""
echo "Note: Make sure to start the reminder service first:"
echo "  java -jar nlp-reminder.jar"
echo "" 