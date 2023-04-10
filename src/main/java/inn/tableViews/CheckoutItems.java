package inn.tableViews;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicReference;

public class CheckoutItems {

    private int checkoutBookingId;
    private String checkoutRoomNo;
    private LocalTime checkoutDueTime;
    private AtomicReference<LocalTime> checkoutTimer;
    private Label checkoutStatus;
    private Button checkoutButton;

    public CheckoutItems(int checkoutBookingId, String checkoutRoomNo, LocalTime checkoutDueTime, AtomicReference<LocalTime> checkoutTimer, Label checkoutStatus, Button checkoutButton) {
        this.checkoutBookingId = checkoutBookingId;
        this.checkoutRoomNo = checkoutRoomNo;
        this.checkoutDueTime = checkoutDueTime;
        this.checkoutTimer = checkoutTimer;
        this.checkoutStatus = checkoutStatus;
        this.checkoutButton = checkoutButton;
    }

    public int getCheckoutBookingId() {
        return checkoutBookingId;
    }

    public void setCheckoutBookingId(int checkoutBookingId) {
        this.checkoutBookingId = checkoutBookingId;
    }

    public String getCheckoutRoomNo() {
        return checkoutRoomNo;
    }

    public void setCheckoutRoomNo(String checkoutRoomNo) {
        this.checkoutRoomNo = checkoutRoomNo;
    }

    public LocalTime getCheckoutDueTime() {
        return checkoutDueTime;
    }

    public void setCheckoutDueTime(LocalTime checkoutDueTime) {
        this.checkoutDueTime = checkoutDueTime;
    }

    public AtomicReference<LocalTime> getCheckoutTimer() {
        return checkoutTimer;
    }

    public void setCheckoutTimer(AtomicReference<LocalTime> checkoutTimer) {
        this.checkoutTimer = checkoutTimer;
    }

    public Label getCheckoutStatus() {
        return checkoutStatus;
    }

    public void setCheckoutStatus(Label checkoutStatus) {
        this.checkoutStatus = checkoutStatus;
    }

    public Button getCheckoutButton() {
        return checkoutButton;
    }

    public void setCheckoutButton(Button checkoutButton) {
        this.checkoutButton = checkoutButton;
    }
}
