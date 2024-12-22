
public class FloorButton implements Button {
    private final int floor;
    private final Direction direction;

    public FloorButton(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public void pressButton(ElevatorControllerInterface controller) {
        System.out.println("Floor " + floor + " button pressed for " + direction);
        controller.addRequest(new Request(floor, direction));
    }
}
