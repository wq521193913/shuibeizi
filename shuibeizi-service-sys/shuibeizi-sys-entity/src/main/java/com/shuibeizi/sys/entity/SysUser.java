package com.shuibeizi.sys.entity;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author: Administrator
 * @description:* @date 2018-10-29 21:36:26
 */
public class SysUser {
    /**
     * uid
     */
    private Integer uid;
    /**
     * 工号
     */
    private String userNo;
    /**
     * 姓名
     */
    @NotNull(message = "用户姓名不能为空")
    private String userName;
    /**
     * 身份证号
     */
    private String userIdCard;
    /**
     * 手机号
     */
    @NotNull(message = "手机号不能为空")
    private String userPhone;
    /**
     * 现住址
     */
    private String userAddress;
    /**
     * 家庭住址
     */
    private String homeAddress;
    /**
     * 性别(-1:未填写;0=男;1=女)
     */
    private Integer userSex = -1;
    /**
     * 生日
     */
    private Date userBirthday;
    /**
     * 毕业院校
     */
    private String userSchool;
    /**
     * 学历
     */
    private String userEducation;
    /**
     * 特长
     */
    private String userStrong;
    /**
     * 入职日期
     */
    private Date entryDate;
    /**
     * 离职日期
     */
    private Date dimissionDate;
    /**
     * 状态(0:在职;1=离职;2=停薪留职)
     */
    private Integer status = 0;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 创建人
     */
    private Integer createUser;
    /**
     * 修改时间
     */
    private Date updateDate;
    /**
     * 修改人
     */
    private Integer updateUser;
    /**
     * 密码
     */
    private String password;

    public Integer getUid() {
        return uid;
    }

    public SysUser setUid(Integer uid) {
        this.uid = uid;
        return this;
    }

    public String getUserNo() {
        return userNo;
    }

    public SysUser setUserNo(String userNo) {
        this.userNo = userNo;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public SysUser setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserIdCard() {
        return userIdCard;
    }

    public SysUser setUserIdCard(String userIdCard) {
        this.userIdCard = userIdCard;
        return this;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public SysUser setUserPhone(String userPhone) {
        this.userPhone = userPhone;
        return this;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public SysUser setUserAddress(String userAddress) {
        this.userAddress = userAddress;
        return this;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public SysUser setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
        return this;
    }

    public Integer getUserSex() {
        return userSex;
    }

    public SysUser setUserSex(Integer userSex) {
        this.userSex = userSex;
        return this;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public SysUser setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
        return this;
    }

    public String getUserSchool() {
        return userSchool;
    }

    public SysUser setUserSchool(String userSchool) {
        this.userSchool = userSchool;
        return this;
    }

    public String getUserEducation() {
        return userEducation;
    }

    public SysUser setUserEducation(String userEducation) {
        this.userEducation = userEducation;
        return this;
    }

    public String getUserStrong() {
        return userStrong;
    }

    public SysUser setUserStrong(String userStrong) {
        this.userStrong = userStrong;
        return this;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public SysUser setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public Date getDimissionDate() {
        return dimissionDate;
    }

    public SysUser setDimissionDate(Date dimissionDate) {
        this.dimissionDate = dimissionDate;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public SysUser setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getRemarks() {
        return remarks;
    }

    public SysUser setRemarks(String remarks) {
        this.remarks = remarks;
        return this;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public SysUser setCreateDate(Date createDate) {
        this.createDate = createDate;
        return this;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public SysUser setCreateUser(Integer createUser) {
        this.createUser = createUser;
        return this;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public SysUser setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public SysUser setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public SysUser setPassword(String password) {
        this.password = password;
        return this;
    }
}