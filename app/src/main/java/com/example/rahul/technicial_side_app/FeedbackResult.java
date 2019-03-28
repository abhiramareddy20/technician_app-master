package com.example.rahul.technicial_side_app;

public class FeedbackResult {
    String descreption,spinnerdate;


    public FeedbackResult(String descreption, String spinnerdate) {
        this.descreption = descreption;
        this.spinnerdate = spinnerdate;
    }



    public String getDescreption() {
        return descreption;
    }

    public String getSpinnerdate() {
        return spinnerdate;
    }
}
