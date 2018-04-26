package com.zd.pojo;

import java.io.Serializable;

/**
 * >>>>>  SQL:
 * select id,Employee_Number ,Is_PDA_Enabled , CompanyID, Sex, CellPhone , Enable_Status ,
 * Username , PDA_Passs_Word , DepartmentID, Nickname
 * from com_userinfo
 * <p/>
 * >>>>>  数据结构:
 * <p/>
 * {
 * "id": "6F1E7BD3-C2AF-4FBD-8994-012722320833",
 * "Employee_Number": "HBGC01",
 * "Is_PDA_Enabled": true,
 * "CompanyID": "E2778D19-0E65-4C9D-A163-2A28606A7168",
 * "Username": "lmt",
 * "PDA_Passs_Word": "654321",
 * "Sex": true,
 * "CellPhone": "15221391109",
 * "DepartmentID": "8B98F83B-4D1B-4CA4-837F-1B87098A34F6",
 * "Enable_Status": true,
 * "Nickname": "张三"
 * }
 */
public class UserData implements Serializable {
    private String id;

    private String Employee_Number;

    private boolean Is_PDA_Enabled;

    private String CompanyID;

    private String Username;

    private String PDA_Passs_Word;

    private boolean Sex;

    private String CellPhone;

    private String DepartmentID;

    private boolean Enable_Status;

    private String Nickname;

    private String LoginTime;

    private String Created_UserID;

    public void setCreated_UserID( String id ) {
        this.Created_UserID = id;
    }

    public String getCreated_UserID() {
        return this.Created_UserID;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getId() {
        return this.id;
    }

    public void setEmployee_Number( String Employee_Number ) {
        this.Employee_Number = Employee_Number;
    }

    public String getEmployee_Number() {
        return this.Employee_Number;
    }

    public void setIs_PDA_Enabled( boolean Is_PDA_Enabled ) {
        this.Is_PDA_Enabled = Is_PDA_Enabled;
    }

    public boolean getIs_PDA_Enabled() {
        return this.Is_PDA_Enabled;
    }

    public void setCompanyID( String CompanyID ) {
        this.CompanyID = CompanyID;
    }

    public String getCompanyID() {
        return this.CompanyID;
    }

    public void setUsername( String Username ) {
        this.Username = Username;
    }

    public String getUsername() {
        return this.Username;
    }

    public void setPDA_Passs_Word( String PDA_Passs_Word ) {
        this.PDA_Passs_Word = PDA_Passs_Word;
    }

    public String getPDA_Passs_Word() {
        return this.PDA_Passs_Word;
    }

    public void setSex( boolean Sex ) {
        this.Sex = Sex;
    }

    public boolean getSex() {
        return this.Sex;
    }

    public void setCellPhone( String CellPhone ) {
        this.CellPhone = CellPhone;
    }

    public String getCellPhone() {
        return this.CellPhone;
    }

    public void setDepartmentID( String DepartmentID ) {
        this.DepartmentID = DepartmentID;
    }

    public String getDepartmentID() {
        return this.DepartmentID;
    }

    public void setEnable_Status( boolean Enable_Status ) {
        this.Enable_Status = Enable_Status;
    }

    public boolean getEnable_Status() {
        return this.Enable_Status;
    }

    public void setNickname( String Nickname ) {
        this.Nickname = Nickname;
    }

    public String getNickname() {
        return this.Nickname;
    }

    public void setLoginTime( String LoginTime ) {
        this.LoginTime = LoginTime;
    }

    public String getLoginTime() {
        return this.LoginTime;
    }

}
