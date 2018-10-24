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
@IdClass(SentMessage.SentMessageKey.class)
public class SentMessage {

    @Id
    private String sender;

    @Id
    @Column(nullable = false)
    private String receiver;


    @Column(nullable = false)
    private String message;

    @Id
    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date time;

    @Column(nullable = false)
    private double fee;

    public SentMessage() {
    }

    public SentMessage(String sender, String receiver,
                       String message, Date time, double fee) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.time = time;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public static class SentMessageKey implements Serializable {

        private String sender;

        private Date time;

        private String receiver;

        public SentMessageKey() {
        }

        public SentMessageKey(String sender, String receiver, Date time) {
            this.sender = sender;
            this.receiver = receiver;
            this.time = time;
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

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SentMessageKey that = (SentMessageKey) o;

            if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
            if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
            return time != null ? time.equals(that.time) : that.time == null;
        }

        @Override
        public int hashCode() {
            int result = sender != null ? sender.hashCode() : 0;
            result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
            result = 31 * result + (time != null ? time.hashCode() : 0);
            return result;
        }
    }

}

