package com.my.demo.wallet.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_info")
@Getter
@Setter
@ToString
@Builder
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String username;

    private String email;

    private String nickname;

    private String portrait;

    private String language;

    private String passwordSalt;

    private String loginPassword;

    private String paymentPassword;

    private Byte type;

    private Byte status;

    private Date createTime;

    private Date modifyTime;

    private Boolean isDelete;
}
