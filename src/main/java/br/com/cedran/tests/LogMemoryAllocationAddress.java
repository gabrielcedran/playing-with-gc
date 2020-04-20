package br.com.cedran.tests;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class LogMemoryAllocationAddress {

    private static Unsafe unsafe;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static long addressOf(Object o) {
        Object[] array = new Object[]{o};
        long baseOffset = unsafe.arrayBaseOffset(Object[].class);
        int addressSize = unsafe.addressSize();
        long objectAddress;
        // Check if the address size is for 32bit environment or bit envs.
        switch (addressSize) {
            case 4:
                objectAddress = unsafe.getInt(array, baseOffset);
                break;
            case 8:
                objectAddress = unsafe.getLong(array, baseOffset);
                break;
            default:
                throw new Error("Unsupported address size: " + addressSize);
        }
        return objectAddress;
    }

    /**
     * As a simple approach to see the GC in action, execute this simple program and save the output in a csv file.
     * Open it in a excel file and create a line graph chart based on the values. It possible to see the memory address allocations
     * increasing while more objects are being created and then when the GC executes, the memory address number dropping and
     * start increasing again (low address number).
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 32000; i++) {
            Object gcMe = new GCMe();
            long address = addressOf(gcMe);
            System.out.println(address);

        }
    }
}

// A big object just to allocate enough memory while creating this object and force the GC to run :)
class GCMe {
    long data;
    long a1;
    long a2;
    long a3;
    long a4;
    long a5;
    long a6;
    long a7;
    long a8;
    long a9;
    long a10;
    long a11;
    long a12;
    long a13;
    long a14;
    long a15;
    long a16;
    long a17;
    long a18;
}
