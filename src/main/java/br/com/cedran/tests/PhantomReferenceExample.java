package br.com.cedran.tests;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;

public class PhantomReferenceExample {

    public static void main(String[] args) {
        ReferenceQueue<Person> queue = new ReferenceQueue<>();
        List<FinalizePerson> finalizePersonList = new ArrayList<>();
        List<Person> people = new ArrayList<>();
        for (int count = 0; count < 10; count++) {
            Person person = new Person();
            people.add(person);
            finalizePersonList.add(new FinalizePerson(person, queue));
        }

        people = null;
        System.gc();

        for (FinalizePerson finalizePerson : finalizePersonList) {
            System.out.println(finalizePerson.isEnqueued());
        }
        Reference<? extends Person> referenceFromQueue;
        while ((referenceFromQueue = queue.poll()) != null) {
            ((FinalizePerson) referenceFromQueue).cleanUp();
            referenceFromQueue.clear();
        }
    }
}

class FinalizePerson extends PhantomReference<Person> {

    public FinalizePerson(Person referent, ReferenceQueue<? super Person> q) {
        super(referent, q);
    }

    public void cleanUp() {
        System.out.println("Person is finalizing resources...");
    }
}