package com.javatpoint.verification;


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.javatpoint.models.UserRecord;

import lombok.Data;

@Entity
@Data
@Table(name = "confirmationtoken")
public class ConfirmationToken {
	private static final int EXPIRATION = 60 * 24;
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="token_id")
    private long tokenid;

    @Column(name="confirmation_token")
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="createdDate")
    private Date createdDate;
    @Column(name="expiryDate")
    private Date expiryDate=calculateExpiryDate(EXPIRATION);
    
    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public void setExpiryDate()
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, EXPIRATION);
        expiryDate=new Date(cal.getTime().getTime());
    }
    @OneToOne(targetEntity = UserRecord.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private UserRecord user;

    public ConfirmationToken(UserRecord user) {	
        this.user = user;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
    public ConfirmationToken() {	
        this.user = null;
        createdDate = new Date();
        confirmationToken = UUID.randomUUID().toString();
    }
}
