package reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.ArrayList;

public class softReference {

    public static void main(String[] argu) throws InterruptedException {
        MyObject myObject = new MyObject();
        ReferenceQueue rQ = new ReferenceQueue<MyObject>();
        SoftReference<MyObject> softReference = new SoftReference<>(myObject, rQ);

        Thread thread = new Thread(() -> {
            Reference<MyObject> refM = null;
            while (true) {
                try {
                    refM = (Reference<MyObject>) rQ.remove();
//                    System.out.println("Object for rQ size is " +);
                } catch (InterruptedException e) {
                    //结束循环
                }
                if (refM != null) {
                    System.out.println("Object for SoftReference is " + refM.get());
                }
            }
        }
        );
        thread.setDaemon(true);
        thread.start();
        Thread.sleep(500);
        System.out.println("thread state " + thread.getState());

        myObject = null;
        System.gc();
        System.out.println("After GC:Soft Get= " + softReference.get());
        System.out.println("Allocate large memory ");
        ArrayList<Byte[]> k = new ArrayList<Byte[]>();
        for (int i = 0; i < 808; i++) {
            Byte[] b = new Byte[1000 * 1024];
            k.add(b);
        }
        System.out.println("After new byte[]:Soft Get= " + softReference.get());


        SoftReference<MyObject> softRef = new SoftReference<MyObject>(myObject, rQ);

    }

}
