package com.dc.truckdcslotsystem.service.impl;

import com.dc.truckdcslotsystem.dao.DcSlotRepository;
import com.dc.truckdcslotsystem.exception.DistributionCenterException;
import com.dc.truckdcslotsystem.model.DcSlot;
import com.dc.truckdcslotsystem.model.DistributionCenter;
import com.dc.truckdcslotsystem.service.DcSlotService;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service

public class DcSlotServiceImpl implements DcSlotService {

      Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private DcSlotRepository dcSlotRepository;

    @Override
    public boolean addDcSlot(DcSlot dcSlot , Long dcNumber) {

        if (findBydcNumber(dcNumber) == null){

            throw new DistributionCenterException("Distribution center Number is not exist");
        }
        DistributionCenter distributionCenter = findBydcNumber(dcNumber);

        if (this.checkSlot(dcSlot.getDcTimeSlot(),distributionCenter)){

            throw new RuntimeException("SLOT NOT AVAILABLE ");
        }
        dcSlot.setDistributionCenter(distributionCenter);
        this.CompareDate(dcSlot.getDcTimeSlot());
        dcSlotRepository.save(dcSlot);

        return  true;

    }

    @Override
    public List<DcSlot> searcDcSlot(Long dcNumber) {
        DistributionCenter distributionCenter = findBydcNumber(dcNumber);
        List<DcSlot> dcSlots =  dcSlotRepository.findByDistributionCenter(distributionCenter);

        return dcSlots;
    }



    @Override
    public DcSlot updateDcSlot(DcSlot dcSlot, Integer dcSlotId) {

        if ( dcSlotRepository.findByDcSlotId(dcSlotId) != null){

            if (this.checkSlot(dcSlot.getDcTimeSlot(), dcSlot.getDistributionCenter())) {
                throw new DistributionCenterException("SLOT NOT AVAILABLE");

            }
            DcSlot dcSlot1 = dcSlotRepository.findByDcSlotId(dcSlotId);
            dcSlot1.setDcTimeSlot(dcSlot.getDcTimeSlot());
            dcSlot1.setMaxTruck(dcSlot.getMaxTruck());
            dcSlotRepository.save(dcSlot1);
            return dcSlot1;

        }
        return  null;

    }

    @Override
    public  boolean deleteDcSlot(Integer dcSlot ){



        if (dcSlotRepository.findByDcSlotId(dcSlot) != null){

            DcSlot dcSlot1 = dcSlotRepository.findByDcSlotId(dcSlot);

            dcSlotRepository.delete(dcSlot1);
            return  true;
        }
        return false;

    }

    @Override
    public DcSlot searchByDcTimeSlot(Integer dcSlotId) {
        if (dcSlotRepository.searchByDcTimeSlot(dcSlotId) != null){

            return dcSlotRepository.searchByDcTimeSlot(dcSlotId );
        }

        return null;
    }

    @Override
    public List<DcSlot> getAllDcSlots() {
        return (List<DcSlot>) dcSlotRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "difaultfindbydcnuber")
    private DistributionCenter findBydcNumber(Long dcNumber){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        System.out.println(entity);

        DistributionCenter distributionCenter  = restTemplate.exchange("http://localhost:8090/root/v1/dc/{dcNumber}", HttpMethod.GET, entity, DistributionCenter.class, dcNumber).getBody();
        logger.debug("sdfdsf", distributionCenter);
        return  distributionCenter;
    }


    private boolean CompareDate(String checkDate){

        String previous_time = checkDate.substring(0,8);
        System.out.println(previous_time);
        String nextTime = checkDate.substring(9,14);
        System.out.println(nextTime);
        Date d1 = null;
        Date d2 = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("HH:mm"); // 12 hour format
            d1 = (Date) format.parse(previous_time);
            d2 = (Date) format.parse(nextTime);
        }catch (Exception e){
            throw new DistributionCenterException("Date Format Is not correct");
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d1);
        calendar.add(Calendar.HOUR, 1);
        d1 = calendar.getTime();
        System.out.println( d1 + "cds" +  d2 + "sdd"  + d1.compareTo(d2));
        if (d1.compareTo(d2)  != 0 ){

            throw new DistributionCenterException("time Should be One Hr");
        }
        return true;
    }

    private boolean checkSlot(String dcTimeSlot, DistributionCenter distributionCenter){
        List<DcSlot> dcSlots = dcSlotRepository.findByDistributionCenter(distributionCenter);


        for(int i=0;i<dcSlots.size();i++) {
            Date d1 = null;
            Date d2 = null;
            Date d3 = null;
            Date d4 = null;
            String previous_time = dcSlots.get(i).getDcTimeSlot().substring(0, 5);
            String check_time = dcTimeSlot.substring(0, 5);
            String check_timeTo = dcTimeSlot.substring(9, 14);
            String nextTime = dcSlots.get(i).getDcTimeSlot().substring(9, 14 );
            try {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                d1 = (Date) format.parse(previous_time);

                d2 = (Date) format.parse(nextTime);

                d3 = (Date) format.parse(check_time);

                d4 = (Date) format.parse(check_timeTo);
               ;

                if (isBetweenValidTime(d1, d2, d3)) {
                    System.out.println("first if"  +isBetweenValidTime(d1, d2, d3));
                    return true;
                }
                System.out.println("first if"  +isBetweenValidTime(d1, d2, d3));
                if (isBetweenValidTime(d1, d2, d4)) {

                    return true;
                }
                System.out.println("secound if" + isBetweenValidTime(d1, d2, d4));
            } catch (Exception e) {
                System.out.println(e);
                throw new DistributionCenterException("TIME SLOT EXCEPTION");
            }
        }
        return false;
    }



    public static final boolean isBetweenValidTime(Date startTime, Date endTime, Date validateTime)
    {
        boolean validTimeFlag = false;
        if(endTime.compareTo(startTime) <= 0)
        {
            if(validateTime.compareTo(endTime) < 0 || validateTime.compareTo(startTime) >= 0)
            {
                validTimeFlag = true;
            }
        }
        else if(validateTime.compareTo(endTime) < 0 && validateTime.compareTo(startTime) >= 0)
        {
            validTimeFlag = true;
        }
        return validTimeFlag;
    }




   private  String difaultfindbydcnuber(){
       System.out.println("inside fallback");

       return  null;
    }

    }





