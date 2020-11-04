package com.dc.truckdcslotsystem.service;

import com.dc.truckdcslotsystem.model.DcSlot;

import java.util.List;

public interface DcSlotService {
    public boolean addDcSlot(DcSlot dcSlot, Long dcNumber);

    List<DcSlot> searcDcSlot(Long dcNumber);
    DcSlot updateDcSlot(DcSlot dcSlot, Integer dcSlotId);

    boolean deleteDcSlot(Integer dcSlot);

    DcSlot searchByDcTimeSlot(Integer dcSlotId);


    public List<DcSlot> getAllDcSlots();
}
