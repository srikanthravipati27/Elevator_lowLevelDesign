
import java.util.PriorityQueue;

public class Elevator implements ElevatorInterface {
    private final ElevatorStatus status;
    private final PriorityQueue<Integer> stops;

    public Elevator(int initialFloor) {
        this.status = new ElevatorStatus(initialFloor);
        this.stops = new PriorityQueue<>();
    }

    public ElevatorStatus getStatus() {
        return status;
    }

    @Override
    public void addStop(int floor) {
        stops.add(floor);
    }

    @Override
    public void processNextStop() {
        if (stops.isEmpty()) {
            status.updateDirection(Direction.IDLE);
            System.out.println("Elevator is idle.");
            return;
        }

        int nextFloor = stops.poll();
        System.out.println("Moving to floor " + nextFloor);

        while (status.getCurrentFloor() != nextFloor) {
            if (status.getCurrentFloor() < nextFloor) {
                status.updateDirection(Direction.UP);
                status.updateFloor(status.getCurrentFloor() + 1);
            } else {
                status.updateDirection(Direction.DOWN);
                status.updateFloor(status.getCurrentFloor() - 1);
            }
            System.out.println("Elevator at floor " + status.getCurrentFloor());
        }

        status.updateDirection(Direction.IDLE);
        System.out.println("Reached floor " + nextFloor);
        openAndCloseDoor();
    }

    private void openAndCloseDoor() {
        status.updateDoorState(DoorState.OPEN);
        System.out.println("Door is open.");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        status.updateDoorState(DoorState.CLOSED);
        System.out.println("Door is closed.");
    }
}
