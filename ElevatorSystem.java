
public class ElevatorSystem {
    public static void main(String[] args) {
        ElevatorInterface elevator = new Elevator(0);
        ElevatorControllerInterface controller = new ElevatorController(elevator);

        Thread controllerThread = new Thread((Runnable) controller);
        controllerThread.start();

        Button floorButton1 = new FloorButton(3, Direction.UP);
        Button floorButton2 = new FloorButton(5, Direction.DOWN);
        Button elevatorButton1 = new ElevatorButton(2);

        floorButton1.pressButton(controller);
        floorButton2.pressButton(controller);
        elevatorButton1.pressButton(controller);
        
        try {
        Thread.sleep(5000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
        
        controllerThread.interrupt();
    }
}
