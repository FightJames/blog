package reference;

public class MyObject {
    // when object is destroyed , this method will be called.
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("MyObject finalize() called");
    }
}
