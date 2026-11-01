

public class Doctor implements Runnable {

    private String name;
    private WaitingRoom waitingRoom;
    private TreatmentRooms treatmentRooms;

    public Doctor(String name, WaitingRoom waitingRoom, TreatmentRooms treatmentRooms) {

        this.name = name;
        this.waitingRoom = waitingRoom;
        this.treatmentRooms = treatmentRooms;
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {

            try {
                Patient patient = waitingRoom.removePatient();

                System.out.println(name + " is assigned to " + patient.getName() + ".");
                System.out.println(name
                        + " is trying to acquire a treatment room.");

                boolean acquired = treatmentRooms.acquireRoom();
                if (!acquired) {

                    System.out.println(name
                            + " could not acquire a treatment room.");

                    System.out.println(name + " is sending "
                            + patient.getName()
                            + " back to the waiting room.");

                    waitingRoom.addPatient(patient);

                    System.out.println(name + " is now available.");

                    continue;
                }

                try {

                    System.out.println(name
                            + " acquired a treatment room.");

                    System.out.println(name
                            + " begins treating "
                            + patient.getName() + ".");

                    
                    // Simulate treatment time
                    Thread.sleep((int) (Math.random() * 5 + 1) * 1000);

                    System.out.println(name
                            + " finished treating "
                            + patient.getName() + ".");

                } finally {

                    treatmentRooms.releaseRoom();

                    System.out.println(name
                            + " released the treatment room.");
                }

                System.out.println(name + " is now available.");

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
