package com.training.RepAgency.service;

import com.training.RepAgency.entity.Request;
import com.training.RepAgency.repository.RequestRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestServiceTest {
    @Autowired
    private RequestService requestService;

    @MockBean
    private RequestRepository requestRepository;
/*
    @Test
    public void saveRequest() {

        Request request = new Request();
        request.setRequest("test");
        request.setCreator("creator");

        Assert.assertFalse(requestService.saveRequest(request.getRequest(), request.getCreator()));
        Mockito.verify(requestRepository, Mockito.times(1)).save(request);
    }

    @Test
    public void updateStatusAndMasterById() {
        Request request = new Request();
        request.setRequest("test");
        request.setCreator("creator");
        assertTrue(requestService.saveRequest(request).isPresent());
        Mockito.verify(requestRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(requestRepository, Mockito.times(1)).save(request);
    }

    @Test
    public void updateStatusById() {

        Request request = new Request();
        requestService.saveRequest("test","create");
        assertTrue(requestService.saveRequest(request).isPresent());
        Mockito.verify(requestRepository, Mockito.times(1)).updateStatusById("new",1L);
    }*/
}