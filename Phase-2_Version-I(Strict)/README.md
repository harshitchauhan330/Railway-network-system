# Railway System Management

This project is a railway system management application written in Java. It allows users to manage railway lines, stations, schedules, and trains via textual commands. The system supports persisting its state between runs using serialization.

---

## Features

1. **Add Lines and Stations**
   - Insert railway lines with associated stations.
3. **Add Train Schedules**
   - Define train schedules with station names and corresponding times.
4. **List Operations**
   - List stations for a line or lines passing through a station.
   - List train schedules for a line.
5. **Remove Operations**
   - Remove lines or schedules.
6. **Save and Load State**
   - Automatically persists the state between application runs using `state.dat`.

---

## Installation

### Requirements

- Java JDK 8 or higher.

### Setup

1. Clone the repository or download the project files:
   ```bash
   git clone https://github.com/your-repo/railway-system-management.git
   cd railway-system-management

### Saved state
- The "TA" command exits the application but the last state of the application is saved in the "stat.dat" file. If the application needs to be refreshed, delete the stat.dat file once before compiling and running "main.java"(the file will be regenerated once the program runs).