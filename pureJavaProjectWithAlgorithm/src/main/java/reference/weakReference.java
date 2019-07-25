package reference;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

public class weakReference {

    public static void main(String[] argu) throws InterruptedException {
        MyObject myObject = new MyObject();
        ReferenceQueue<MyObject> rQ = new ReferenceQueue<>();
        WeakReference<MyObject> weakRf = new WeakReference(myObject, rQ);
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
        System.out.println("Before GC:Weak Get= " + weakRf.get());
        System.gc();
        System.out.println("After GC:Weak Get= " + weakRf.get());

    }
}
