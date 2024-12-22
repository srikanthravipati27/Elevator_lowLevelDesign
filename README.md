# Overview
The Elevator Management System simulates the operation of elevators in a multi-story building. It manages user requests efficiently, handles elevator movement, and maintains the state of the elevator. This project leverages Object-Oriented Programming (OOP) principles to ensure a modular, extensible, and maintainable design.

# 🚀 Purpose of the Design
**1.Scalability:** The system should handle increasing floors, users, or even multiple elevators in future versions.<br>
**2.Modularity:** Each component (e.g., buttons, requests, controller) has a clearly defined responsibility to make debugging and future upgrades easier.<br>
**3.Concurrency:** Ensures thread-safe operations as multiple requests can arrive simultaneously.

# Class Descriptions and Design Choices
# 1. Class: ElevatorStatus
**Purpose:** Tracks the current state of the elevator.
## Attributes:

&middot; currentFloor (int): Represents the elevator's current floor.<br>
&middot; direction (Direction Enum): Indicates whether the elevator is moving UP, DOWN, or is IDLE.<br>
&middot; doorState (DoorState Enum): Tracks the door state (OPEN or CLOSED).

# 2. Class: Request
**Purpose:** Represents a user's request for elevator service.
**Attributes:**

&middot; floor (int): The target floor.<br>
&middot; direction (Direction Enum): Indicates the desired direction (e.g., UP or DOWN).<br>
### Why a Class?

**Abstraction:** Encapsulating floor and direction into a Request class makes it easier to extend functionality, like adding timestamps or prioritizing emergency requests.<br>
**Tradeoff:** A simple pair of integers could represent floor and direction, but this would make extending the system harder and increase code coupling.

# 3. Enums: Direction and DoorState
**Purpose:** Enumerations are used to define finite, clear, and type-safe states.

&middot; Direction Enum: Tracks the elevator's movement (UP, DOWN, IDLE).<br>
&middot; DoorState Enum: Tracks the door's state (OPEN, CLOSED).<br>
### Why Enums?

&middot; Clarity: Using enums avoids magic values (like integers or strings) and makes the code self-explanatory.<br>
&middot; Maintainability: Adding or changing states becomes straightforward.<br>

# 4. Interface: Button
**Purpose:** Defines the contract for floor and elevator buttons.
### Methods:

&middot; pressButton(ElevatorControllerInterface controller): Sends a request to the elevator controller.<br>
### Why an Interface?

&middot; Extensibility: Adding new button types, like emergency or service buttons, becomes easier by implementing this interface.<br>
&middot; Tradeoff: A single class could manage button logic, but using an interface promotes separation of concerns and adheres to the Open-Closed Principle (OCP).<br>

# 5. Classes: FloorButton and ElevatorButton
**Purpose:** Represent buttons outside the elevator (on floors) and inside the elevator.
## Attributes:

&middot; FloorButton: Includes floor (int) and direction (Direction).<br>
&middot; ElevatorButton: Includes floor (int).<br>
## Design Consideration:

&middot; Floor buttons take both floor and direction because requests from outside need to specify travel direction.<br>
&middot; Elevator buttons only take floor because the direction is implicit based on the current and target floors.<br>

# 6. Class: Elevator
**Purpose:** Represents the physical elevator, manages stops, and processes movement.
## Attributes:

&middot; status (ElevatorStatus): Tracks the current state of the elevator.<br>
&middot; stops (PriorityQueue<Integer>): Schedules floors efficiently based on priority.<br>
### Why PriorityQueue for Stops?

&middot; Efficiency: Automatically sorts floors, reducing the need for manual sorting logic.<br>
&middot; Tradeoff: A simple list would be easier to implement but would require additional logic for ordering.<br>

# 7. Class: ElevatorController
**Purpose:** Manages requests and coordinates elevator actions.
## Attributes:

&middot; requestQueue (LinkedBlockingQueue<Request>): Thread-safe queue for handling concurrent requests.
### Why LinkedBlockingQueue?

&middot; Thread-Safety: Ensures safe handling of multiple user requests in a multi-threaded environment.<br>
&middot; Blocking Mechanism: Prevents busy waiting by pausing the thread until a new request arrives.<br>

# Key Design Decisions
## 1. Centralized Request Queue
The LinkedBlockingQueue in ElevatorController acts as the central repository for requests. This ensures:<br>

&middot; Concurrency: Multiple threads (representing button presses) can add requests without conflicts.<br>
&middot; Efficiency: The controller thread processes requests sequentially, avoiding race conditions.<br>

## 2. Encapsulation of State
The ElevatorStatus class encapsulates all state information (floor, direction, door state). This:<br>

&middot; Improves Modularity: Changes to state representation only affect this class.<br>
&middot; Simplifies Updates: Methods like updateFloor and updateDirection make state transitions explicit.<br>

## 3. Separation of Concerns
Using separate classes for requests, buttons, and controllers ensures:<br>

&middot; Loose Coupling: Components interact through well-defined interfaces.<br>
&middot; Extensibility: Adding new features, like handling multiple elevators, becomes straightforward.<br>

## Edge Cases to Consider ⚠️ <br>
**Simultaneous Requests:** Multiple users pressing buttons at once are safely handled by LinkedBlockingQueue. <br>
**Out-of-Service Floors:** Requests for unavailable floors are ignored or flagged as errors. <br>
**Emergency Stop:** Future enhancements can prioritize emergency stops over standard requests. <br>

# Workflow
## User Input:

&middot; A user presses a Floor Button or Elevator Button.<br>
&middot; This triggers the pressButton() method, creating a Request object.<br>
## Request Queue:

&middot; The Request is added to the LinkedBlockingQueue in the ElevatorController.
## Controller Thread 🔄 :

&middot; The run() method in ElevatorController processes requests from the queue.<br>
&middot; Each request is passed to the elevator using addStop().
## Elevator Movement 🛗:

&middot; The processNextStop() method in Elevator handles the next floor in the PriorityQueue.<br>
&middot; Updates to currentFloor, direction, and doorState are performed step-by-step.<br>
## Idle State 💤:

&middot; When no requests remain, the elevator transitions to IDLE.<br>

# Conclusion
This design prioritizes modularity, scalability, and thread-safety. By making informed trade-offs, such as choosing a PriorityQueue for stops and a LinkedBlockingQueue for requests, the system is both efficient and extensible. Future enhancements, like adding multiple elevators or advanced scheduling algorithms, can be seamlessly integrated due to the well-defined responsibilities of each component.
