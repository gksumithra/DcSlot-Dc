package com.dc.truckdcslotsystem.dao;

import com.dc.truckdcslotsystem.model.DcSlot;
import com.dc.truckdcslotsystem.model.DistributionCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DcSlotRepository extends CrudRepository<DcSlot, Integer> {

    List<DcSlot> findByDistributionCenter(DistributionCenter distributionCenter);


    DcSlot findByDcSlotId(Integer id);

     DcSlot searchByDcTimeSlot(Integer dcSlotId);


}
