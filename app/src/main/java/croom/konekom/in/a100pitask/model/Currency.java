package croom.konekom.in.a100pitask.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
/***
 * Created By Kartikey Kumar Srivastava
 */

//Currency model both as a table as well as model for retrofit
@Entity(tableName = "Currency")
public class Currency {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Currency")
    @ColumnInfo(name = "Currency")
    private String currency;
    @SerializedName("CurrencyLong")
    @ColumnInfo(name = "CurrencyLong")
    private String currencyLong;
    @SerializedName("MinConfirmation")
    private String minConfirmation;
    @SerializedName("TxFee")
    @ColumnInfo(name = "TxFee")
    private double txFee;
    @SerializedName("IsActive")
    private boolean isActive;
    @SerializedName("IsRestricted")
    private boolean isRestricted;
    @SerializedName("CoinType")
    private String coinType;
    @SerializedName("BaseAddress")
    private String baseAddress;
    @SerializedName("Notice")
    private String notice;

    public Currency(String currency, String currencyLong, String minConfirmation, double txFee, boolean isActive, boolean isRestricted, String coinType, String baseAddress, String notice) {
        this.currency = currency;
        this.currencyLong = currencyLong;
        this.minConfirmation = minConfirmation;
        this.txFee = txFee;
        this.isActive = isActive;
        this.isRestricted = isRestricted;
        this.coinType = coinType;
        this.baseAddress = baseAddress;
        this.notice = notice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrencyLong() {
        return currencyLong;
    }

    public void setCurrencyLong(String currencyLong) {
        this.currencyLong = currencyLong;
    }

    public String getMinConfirmation() {
        return minConfirmation;
    }

    public void setMinConfirmation(String minConfirmation) {
        this.minConfirmation = minConfirmation;
    }

    public double getTxFee() {
        return txFee;
    }

    public void setTxFee(double txFee) {
        this.txFee = txFee;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isRestricted() {
        return isRestricted;
    }

    public void setRestricted(boolean restricted) {
        isRestricted = restricted;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public String getBaseAddress() {
        return baseAddress;
    }

    public void setBaseAddress(String baseAddress) {
        this.baseAddress = baseAddress;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
