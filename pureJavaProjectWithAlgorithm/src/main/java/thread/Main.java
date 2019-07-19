package thread;

public class Main {
    static boolean flag = false;

    public static void main(String[] argu) throws InterruptedException {
        Thread target = new Thread(new Runnable() {
            @Override
            public void run() {
                while (!flag) {
                }
                while (flag) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Hello Thread flag is true");
                }
            }
        });
        target.start();
        Thread.sleep(2000);
        flag = true;
        System.out.println("Flag is true");
        Thread.sleep(2000);
        flag = false;
        System.out.println("Flag is false");
        Thread.sleep(2000);
        System.out.println(target.isAlive());

    }
}
