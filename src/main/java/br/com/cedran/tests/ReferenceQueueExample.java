package br.com.cedran.tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReferenceQueueExample {

    public static void main(String[] args) throws IOException {
        FinalPerson person = new FinalPerson();
        PersonCleaner personCleaner = new PersonCleaner();
        final ReferenceQueue<FinalPerson> referenceQueue = new ReferenceQueue<>();
        PersonWeakReference weakReference = new PersonWeakReference(person, personCleaner, referenceQueue);

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                System.out.println("Runnable executed!");
                // Remove wait for the object to be available.
                PersonWeakReference wr = (PersonWeakReference) referenceQueue.remove();
                wr.clean();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        person = null;
        // As soon as the GC is executed, the weak reference person is going to be added to the reference queue and is ready to be polled/removed.
        // If it was a soft reference, it would be added only when the reference was cleared, under memory pressure.
        System.gc();

        BufferedReader br = new BufferedReader((new InputStreamReader(System.in)));
        System.out.println("Press any key to continue");
        br.readLine();
        executorService.shutdown();

    }
}

class PersonCleaner {

    public void clean() {
        System.out.println("Cleaned!");
    }
}

class PersonWeakReference extends WeakReference<FinalPerson> {

    private PersonCleaner personCleaner;
    public PersonWeakReference(FinalPerson referent, PersonCleaner personCleaner, ReferenceQueue<? super FinalPerson> q) {
        super(referent, q);
        this.personCleaner = personCleaner;
    }

    public void clean() {
        personCleaner.clean();
    }
}
