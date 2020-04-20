package br.com.cedran.tests;

import java.time.LocalDateTime;
import java.util.WeakHashMap;

public class WeakHashMapExample {

    public static void main(String[] args) throws InterruptedException {
        WeakHashMap<FinalPerson, PersonMetadata> weakHashMap = new WeakHashMap<>();
        FinalPerson mary = new FinalPerson();
        weakHashMap.put(mary, new PersonMetadata());

        PersonMetadata personMetadata = weakHashMap.get(mary);
        System.out.println(personMetadata);

        mary = null;
        System.gc();
        Thread.sleep(1000);

        System.out.println(weakHashMap.containsValue(personMetadata));
    }

}

final class FinalPerson {

}

class PersonMetadata {
    private LocalDateTime date;
    public PersonMetadata() {
        date = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "PersonMetadata{" +
                "date=" + date +
                '}';
    }
}
