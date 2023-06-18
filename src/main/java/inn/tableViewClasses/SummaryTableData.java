package inn.tableViewClasses;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class SummaryTableData {
   private int checkinID;
   private String guestName ;
   private String guestNumber;
   private String roomNo;
   private String bookingType;
   private Timestamp bookingDate;
   private Timestamp dueDate;
   private Timestamp checkoutDate;
   private LocalTime overTime;
   private String note;
   private String bookedBy;
   String formattedCheckOutDate;
   String formattedCheckInDate;
   String formattedDueDate;
   DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);

   public SummaryTableData(int checkinID, String guestName, String guestNumber, String roomNo, String bookingType, Timestamp bookingDate, Timestamp dueDate, Timestamp checkoutDate, LocalTime overTime, String note, String bookedBy) {
      this.checkinID = checkinID;
      this.guestName = guestName;
      this.guestNumber = guestNumber;
      this.roomNo = roomNo;
      this.bookingType = bookingType;
      this.bookingDate = bookingDate;
      this.dueDate = dueDate;
      this.checkoutDate = checkoutDate;
      this.overTime = overTime;
      this.note = note;
      this.bookedBy = bookedBy;
      this.formattedCheckOutDate = formatter.format(checkoutDate.toLocalDateTime());
      this.formattedCheckInDate = formatter.format(bookingDate.toLocalDateTime());
      this.formattedDueDate = formatter.format(dueDate.toLocalDateTime());
   }

   public int getCheckinID() {
      return checkinID;
   }

   public void setCheckinID(int checkinID) {
      this.checkinID = checkinID;
   }

   public String getGuestName() {
      return guestName;
   }

   public void setGuestName(String guestName) {
      this.guestName = guestName;
   }

   public String getGuestNumber() {
      return guestNumber;
   }

   public void setGuestNumber(String guestNumber) {
      this.guestNumber = guestNumber;
   }

   public String getRoomNo() {
      return roomNo;
   }

   public void setRoomNo(String roomNo) {
      this.roomNo = roomNo;
   }

   public String getBookingType() {
      return bookingType;
   }

   public void setBookingType(String bookingType) {
      this.bookingType = bookingType;
   }

   public Timestamp getBookingDate() {
      return bookingDate;
   }

   public void setBookingDate(Timestamp bookingDate) {
      this.bookingDate = bookingDate;
   }

   public Timestamp getDueDate() {
      return dueDate;
   }

   public void setDueDate(Timestamp dueDate) {
      this.dueDate = dueDate;
   }

   public Timestamp getCheckoutDate() {
      return checkoutDate;
   }

   public void setCheckoutDate(Timestamp checkoutDate) {
      this.checkoutDate = checkoutDate;
   }

   public LocalTime getOverTime() {
      return overTime;
   }

   public void setOverTime(LocalTime overTime) {
      this.overTime = overTime;
   }

   public String getNote() {
      return note;
   }

   public void setNote(String note) {
      this.note = note;
   }

   public String getFormattedCheckOutDate() {
      return formattedCheckOutDate;
   }

   public String getFormattedDueDate() {
      return formattedDueDate;
   }

   public void setFormattedDueDate(String formattedDueDate) {
      this.formattedDueDate = formattedDueDate;
   }

   public void setFormattedCheckOutDate(String formattedCheckOutDate) {
      this.formattedCheckOutDate = formattedCheckOutDate;
   }

   public String getFormattedCheckInDate() {
      return formattedCheckInDate;
   }

   public void setFormattedCheckInDate(String formattedCheckInDate) {
      this.formattedCheckInDate = formattedCheckInDate;
   }

   public String getBookedBy() {
      return bookedBy;
   }

   public void setBookedBy(String bookedBy) {
      this.bookedBy = bookedBy;
   }
}//end of class
