package com.dc.truckdcslotsystem.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class DistributionCenter {

    @Id
    @NotNull
    @Column(name="dcNumber")
    private Long dcNumber;


    @Column(name = "dcCity")
    @NotNull
    private String dcCity;


    @Column(name = "dcType")
    @NotNull
    private String dcType;

    public DistributionCenter() {
    }

    public DistributionCenter(Long dcNumber, String dcCity, String dcType) {
        this.dcNumber = dcNumber;
        this.dcCity = dcCity;
        this.dcType = dcType;
    }

    public Long getDcNumber() {
        return dcNumber;
    }

    public void setDcNumber(Long dcNumber) {
        this.dcNumber = dcNumber;
    }

    public String getDcCity() {
        return dcCity;
    }

    public void setDcCity(String dcCity) {
        this.dcCity = dcCity;
    }

    public String getDcType() {
        return dcType;
    }

    public void setDctype(String dcType) {
        this.dcType = dcType;
    }

    @Override
    public String toString() {
        return "DistributionCenter{" +
                "dcNumber=" + dcNumber +
                ", dcCity='" + dcCity + '\'' +
                ", dcType='" + dcType + '\'' +
                '}';
    }
}
