package com.jwt.model.sysadmin;

import javax.persistence.*;

/**
 * Created by Stefan on 13.04.2017.
 */
@Entity
@Table(name = "USER_PRIVATE_DATA")
public class UserPrivateData {

    @Id
    @Column(name = "user_private_data_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userPrivateDataId;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_no")
    private String phoneNo;


    public Long getUserPrivateDataId() {
        return userPrivateDataId;
    }

    public void setUserPrivateDataId(Long userPrivateDataId) {
        this.userPrivateDataId = userPrivateDataId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public String toString() {
        return "UserPrivateData{" +
                "userPrivateDataId=" + userPrivateDataId +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                '}';
    }
}
