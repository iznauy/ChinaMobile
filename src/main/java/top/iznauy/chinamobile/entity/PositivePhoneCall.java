package top.iznauy.chinamobile.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created on 2018/10/24.
 * Description:
 *
 * @author iznauy
 */
@Entity
@IdClass(PositivePhoneCall.PositivePhoneCallKey.class)
public class PositivePhoneCall {

    @Id
    private String sender;

    @Column(nullable = false)
    private String receiver;

    @Id
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date endTime;


    @Column(nullable = false)
    private double fee;

    public PositivePhoneCall() {
    }

    public PositivePhoneCall(String sender, String receiver, Date startTime, Date endTime, double fee) {
        this.sender = sender;
        this.receiver = receiver;
        this.startTime = startTime;
        this.endTime = endTime;
        this.fee = fee;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public static class PositivePhoneCallKey implements Serializable {

        private String sender;

        private Date startTime;

        public PositivePhoneCallKey() {
        }

        public PositivePhoneCallKey(String sender, Date startTime) {
            this.sender = sender;
            this.startTime = startTime;
        }

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public Date getStartTime() {
            return startTime;
        }

        public void setStartTime(Date startTime) {
            this.startTime = startTime;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PositivePhoneCallKey that = (PositivePhoneCallKey) o;

            if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
            return startTime != null ? startTime.equals(that.startTime) : that.startTime == null;
        }

        @Override
        public int hashCode() {
            int result = sender != null ? sender.hashCode() : 0;
            result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
            return result;
        }
    }

}
