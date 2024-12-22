
public class ElevatorStatus {
    private int currentFloor;
    private Direction direction;
    private DoorState doorState;

    public ElevatorStatus(int currentFloor) {
        this.currentFloor = currentFloor;
        this.direction = Direction.IDLE;
        this.doorState = DoorState.CLOSED;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public DoorState getDoorState() {
        return doorState;
    }

    public void updateFloor(int floor) {
        this.currentFloor = floor;
    }

    public void updateDirection(Direction direction) {
        this.direction = direction;
    }

    public void updateDoorState(DoorState doorState) {
        this.doorState = doorState;
    }
}
