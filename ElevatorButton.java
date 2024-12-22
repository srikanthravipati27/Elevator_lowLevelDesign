
public class ElevatorButton implements Button {
    private final int floor;

    public ElevatorButton(int floor) {
        this.floor = floor;
    }

    @Override
    public void pressButton(ElevatorControllerInterface controller) {
        System.out.println("Elevator button pressed for floor " + floor);
        controller.addRequest(new Request(floor, null));
    }
}
