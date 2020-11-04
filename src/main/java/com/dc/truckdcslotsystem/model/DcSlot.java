package com.dc.truckdcslotsystem.model;

import com.dc.truckdcslotsystem.model.DistributionCenter;

import javax.persistence.*;

@Entity
public class DcSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dcSlotId;


    @ManyToOne( cascade = {CascadeType.ALL})
    @JoinColumn(name = "dcNumber")
    private DistributionCenter distributionCenter;

    @Column( name = "dcTimeSlot")
    private String dcTimeSlot;

    @Column(name = "maxTruck")
    private  Integer maxTruck;

    public DcSlot() {
    }

    public DcSlot(Integer dcSlotId, DistributionCenter distributionCenter, String dcTimeSlot, Integer maxTruck) {
        this.dcSlotId = dcSlotId;
        this.distributionCenter = distributionCenter;
        this.dcTimeSlot = dcTimeSlot;
        this.maxTruck = maxTruck;
    }

    public Integer getDcSlotId() {
        return dcSlotId;
    }

    public void setDcSlotId(Integer dcSlotId) {
        this.dcSlotId = dcSlotId;
    }

    public DistributionCenter getDistributionCenter() {
        return distributionCenter;
    }

    public void setDistributionCenter(DistributionCenter distributionCenter) {
        this.distributionCenter = distributionCenter;
    }

    public String getDcTimeSlot() {
        return dcTimeSlot;
    }

    public void setDcTimeSlot(String dcTimeSlot) {
        this.dcTimeSlot = dcTimeSlot;
    }

    public Integer getMaxTruck() {
        return maxTruck;
    }

    public void setMaxTruck(Integer maxTruck) {
        this.maxTruck = maxTruck;
    }
}
