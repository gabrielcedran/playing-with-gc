package br.com.cedran.tests;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class GCMXBean {
    public static void main(String[] args) {
        List<GarbageCollectorMXBean> list = ManagementFactory.getGarbageCollectorMXBeans();
        list.forEach(mxBean -> {
            System.out.println("Name: " + mxBean.getName());
            System.out.println("Collection Count: " + mxBean.getCollectionCount());
            System.out.println("Collection Time: " + mxBean.getCollectionTime());

            for (String memoryPoolName : mxBean.getMemoryPoolNames()) {
                System.out.println("\t Pool name:" + memoryPoolName);
            }
            System.out.println();
        });
    }
}
