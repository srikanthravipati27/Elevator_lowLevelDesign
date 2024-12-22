
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ElevatorController implements ElevatorControllerInterface, Runnable {
    private final ElevatorInterface elevator;
    private final BlockingQueue<Request> requestQueue;

    public ElevatorController(ElevatorInterface elevator) {
        this.elevator = elevator;
        this.requestQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public void addRequest(Request request) {
        requestQueue.add(request);
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Request request = requestQueue.take();
                System.out.println("Processing request for floor " + request.getFloor());
                elevator.addStop(request.getFloor());
                elevator.processNextStop();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Controller interrupted.");
            }
        }
    }
}
