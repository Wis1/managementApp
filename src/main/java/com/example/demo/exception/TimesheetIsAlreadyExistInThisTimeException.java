package com.example.demo.exception;

public class TimesheetIsAlreadyExistInThisTimeException extends RuntimeException {
   public TimesheetIsAlreadyExistInThisTimeException(){
       super("Timesheet exists in this time");
   }
}
