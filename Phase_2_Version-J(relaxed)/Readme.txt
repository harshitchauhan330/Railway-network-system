1. Main.java
This file will:

Handle input and output for the program.
Process commands and call appropriate methods in other classes.

2. RailwaySystem.java
This is the core of the system and will:

3. KeyValueStore.java
A custom key-value store implementation to replace HashMap.

java
Copy code

Manage data about lines, stations, and schedules.
Store and retrieve data for persistence.

4. Line.java
Represents a railway line:

Stores the line name and associated stations.
Includes methods to retrieve and manipulate line data.

5. Schedule.java
* Placeholder for Phase 2 development
Represents a train schedule:

Contains schedule details (line name, train number, timetable).
Ensures proper formatting and order of stops.

6. Persistence.java
Handles saving and loading the state using Java serialization.
* Included in RailwaySystem for simplicity
