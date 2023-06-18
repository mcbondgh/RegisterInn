package inn.prompts;

import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;

public class UserNotification {
    Notifications notifications = Notifications.create();
    public void  successNotification(String title, String text) {
        Image logo = new Image("G:\\My Drive\\RegisterInn\\src\\main\\resources\\inn\\images\\successIcon.png");
        notifications.graphic(new ImageView(logo));
        notifications.title(title);
        notifications.text(text);
        notifications.position(Pos.TOP_CENTER);
        notifications.show();
        notifications.hideAfter(Duration.seconds(3));
    }

    public void errorNotification(String title, String text) {
        notifications.title(title);
        notifications.text(text);
        notifications.showError();
        notifications.hideAfter(Duration.seconds(3));
    }

    public void confirmAction(String title, String text) {
        notifications.action(new Action("YES"), new Action("NO"));
        notifications.position(Pos.CENTER);
        notifications.title(title);
        notifications.text(text);
        notifications.hideAfter(Duration.seconds(10000));
        notifications.showConfirm();
        if(ActionEvent.ACTION.equals("YES")) {
            System.out.println("done.");
        }

    }

    public void informationNotification(@NamedArg("Title")String title, @NamedArg("Text")String text) {
        notifications.title(title);
        notifications.text(text);
        notifications.hideAfter(Duration.seconds(3));
        notifications.showInformation();
        }


    }//end of clas.....
