package com.os.foodie.model;

public class TempEarningModel {

    private String earningId;
    private String transactionId;
    private String amount;
    private String date;

    public TempEarningModel() {
    }

    public TempEarningModel(String earningId, String transactionId, String amount, String date) {
        this.earningId = earningId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.date = date;
    }

    public String getEarningId() {
        return earningId;
    }

    public void setEarningId(String earningId) {
        this.earningId = earningId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}