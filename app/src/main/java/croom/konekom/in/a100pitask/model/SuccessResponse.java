package croom.konekom.in.a100pitask.model;

import com.google.gson.annotations.SerializedName;


import java.util.List;

public class SuccessResponse {

    @SerializedName("success")
    private boolean success;
    @SerializedName("message")
    private String message;
    @SerializedName("result")
    private List<Currency> currencies = null;

    public SuccessResponse(boolean success, String message, List<Currency> currencies) {
        this.success = success;
        this.message = message;
        this.currencies = currencies;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }
}
