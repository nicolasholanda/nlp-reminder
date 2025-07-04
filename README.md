# NLP Reminder CLI

A natural language reminder system with a simple command-line interface. Create reminders using natural language like "buy milk in 2 minutes" or "call mom tomorrow at 3pm".

## Features

- 🕒 Natural language time parsing
- 🔔 Desktop notifications (Linux, macOS, Windows)
- 📱 Simple command-line interface
- 🚀 Easy installation and setup
- 💾 Persistent storage with SQLite

## Quick Start

### 1. Build the Project

```bash
mvn clean package
```

### 2. Install the CLI Tool

```bash
chmod +x install.sh
./install.sh
```

### 3. Start the Reminder Service

```bash
java -jar target/nlp-reminder-1.0-SNAPSHOT.jar
```

### 4. Create Your First Reminder

```bash
reminder "buy milk in 2 minutes"
```

## Installation

### Prerequisites

- Java 11 or higher
- Maven (for building)
- netcat (for CLI communication)

### Step-by-Step Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/nicolasholanda/nlp-reminder.git
   cd nlp-reminder
   ```

2. **Build the project:**
   ```bash
   mvn clean package
   ```

3. **Install the CLI tool:**
   ```bash
   chmod +x install.sh
   ./install.sh
   ```

4. **Add to PATH (if needed):**
   If the installation script shows a warning about PATH, add this to your shell profile (~/.bashrc, ~/.zshrc, etc.):
   ```bash
   export PATH="$PATH:$HOME/.local/bin"
   ```
   Then restart your terminal or run `source ~/.bashrc`

## Usage

### Basic Usage

```bash
reminder "your reminder text with time"
```

### Examples

```bash
# Simple time-based reminders
reminder "buy milk in 2 minutes"
reminder "take medicine in 30 minutes"
reminder "meeting in 1 hour"

# Specific times
reminder "call mom at 3pm"
reminder "dinner at 7:30pm"
reminder "meeting tomorrow at 9am"

# Relative dates
reminder "pay bills tomorrow"
reminder "dentist appointment next Monday"
reminder "birthday party next Friday at 6pm"
```

### Help

```bash
reminder --help
```

## Running the Service

### Option 1: JAR File (Recommended)
```bash
java -jar target/nlp-reminder-1.0-SNAPSHOT.jar
```

### Option 2: Maven
```bash
mvn exec:java -Dexec.mainClass="com.github.nicolasholanda.Main"
```

### Option 3: IDE
Run the `Main` class directly from your IDE.

## How It Works

1. **CLI Tool**: The `reminder` command sends your reminder text to the service via Unix socket
2. **Natural Language Processing**: The service parses your text to extract the reminder message and time
3. **Storage**: Reminders are stored in a SQLite database (`reminders.db`)
4. **Monitoring**: A background service continuously checks for due reminders
5. **Notifications**: When a reminder is due, you get a desktop notification

## File Structure

```
nlp-reminder/
├── reminder              # CLI tool script
├── install.sh           # Installation script
├── pom.xml              # Maven configuration
├── src/                 # Java source code
├── target/              # Compiled classes and JAR
└── reminders.db         # SQLite database (created automatically)
```

## Troubleshooting

### "Reminder service is not running"
Make sure you've started the Java service:
```bash
java -jar target/nlp-reminder-1.0-SNAPSHOT.jar
```

### "netcat (nc) is not installed"
Install netcat:
- **Ubuntu/Debian**: `sudo apt install netcat`
- **macOS**: `brew install netcat`
- **CentOS/RHEL**: `sudo yum install nc`

### "Permission denied" when running reminder
Make sure the script is executable:
```bash
chmod +x reminder
```

### Notifications not working
- **Linux**: Make sure `notify-send` is installed (`sudo apt install libnotify-bin`)
- **macOS**: No additional setup required
- **Windows**: No additional setup required

## Development

### Building from Source
```bash
mvn clean compile
```

### Running Tests
```bash
mvn test
```

### Adding New Features
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests
5. Submit a pull request

## License

This project is open source. Feel free to contribute!

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request. 