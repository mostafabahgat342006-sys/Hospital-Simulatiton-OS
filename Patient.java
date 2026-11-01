public class Patient implements Runnable {
    private final String name;
    private final WaitingRoom waitingRoom;

    public Patient(String name, WaitingRoom waitingRoom) {
        this.name = name;
        this.waitingRoom = waitingRoom;
    }

    public String getName() {
        return name;
    }

    @Override
    public void run() {
        try {
            // 1. وصول المريض إلى المستشفى
            System.out.println(name + " arrived.");
            
            // 2. محاولة دخول صالة الانتظار
            waitingRoom.addPatient(this);
            System.out.println(name + " entered the waiting room.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
