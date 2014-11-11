import org.spider.UserInterface;

/**
 * Created by peter.georgiev on 7/11/14.
 */
public class Root {
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                UserInterface frame = new UserInterface("Spider");
                frame.postCreate();
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
