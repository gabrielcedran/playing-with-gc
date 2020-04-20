package br.com.cedran.tests;

import java.lang.ref.WeakReference;
import java.nio.file.WatchEvent;

public class WeakReferenceExample {
    public static void main(String[] args) {
        // Strong reference
        Person person = new Person();

        // Weak Reference :P
        WeakReference<Person> weakReference = new WeakReference<>(person);

        Person person2 = weakReference.get();
        System.out.println(person2);

        // Freeing the strong references
        person = null;
        person2 = null;

        // As the GC has not run yet, the weak reference won't have been GC'd.
        Person person3 = weakReference.get();
        System.out.println(person3);
        person3 = null;

        // When the GC runs and there is no strong reference to the object, the weak reference is freed as well.
        System.gc();
        Person person4 = weakReference.get();
        System.out.println(person4);

    }

}

class Person {

}
