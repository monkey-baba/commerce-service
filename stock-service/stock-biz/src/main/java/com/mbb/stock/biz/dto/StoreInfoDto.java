package com.mbb.stock.biz.dto;

public class StoreInfoDto {
    private String name;

    public Long getClassifyid() {
        return Classifyid;
    }

    public void setClassifyid(Long classifyid) {
        Classifyid = classifyid;
    }

    private Long Classifyid;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAddress() {
        return address;
    }

    public void setAddress(Long address) {
        this.address = address;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    private Long address;
    private Long status;
    private String contact;
    private String code;
    private String owner;
}
