import java.util.LinkedList;
import java.util.Queue;

public class WaitingRoom {

    private Queue<Patient> queue;

    private Semaphore spaces;
    private Semaphore patients;
    private Semaphore lock;

    public WaitingRoom(int capacity) {

        if (capacity <= 0) {
            throw new IllegalArgumentException("Invalid capacity");
        }

        queue = new LinkedList<>();

        spaces = new Semaphore(capacity);
        patients = new Semaphore(0);
        lock = new Semaphore(1);
    }

    public void addPatient(Patient patient) throws InterruptedException
    {

        spaces.P();

        lock.P();

        queue.add(patient);

        lock.V();

        patients.V();
    }

    public Patient removePatient() throws InterruptedException
    {

        patients.P();

        lock.P();

        Patient patient = queue.remove();

        lock.V();

        spaces.V();

        return patient;  //نطلع المريض من الطابور للدكتور يعالجه
    }

    public int getWaitingCount() throws InterruptedException {

        lock.P(); //استنى لحد ما محدش يكون بيعدّل الـ Queue، وبعدها اقرأ العدد.

        int count = queue.size();

        lock.V();

        return count;
    }
}
