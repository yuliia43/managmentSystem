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

import java.util.Optional;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestServiceTest {
    @Autowired
    private RequestService requestService;

    @MockBean
    private RequestRepository requestRepository;

    @Test
    public void saveRequest() {

        Request request = new Request();
        request.setRequest("test");
        request.setCreator("creator");
        requestService.saveRequest(request);
        Mockito.verify(requestRepository, Mockito.times(1)).save(request);
    }

    @Test
    public void updateStatusAndMasterById() {
        Request request = new Request();
        request.setId(1L);
        Optional<Request> temp=Optional.of(request);
        Mockito.doReturn(temp)
                .when(requestRepository)
                .findById(request.getId());
        requestService.updateStatusAndMasterById("new",request.getId(),"master","reason",450L);
        Mockito.verify(requestRepository, Mockito.times(1)).findById(request.getId());

    }

    @Test
    public void updateStatusById() {

        Request request = new Request();
        request.setId(1L);
        request.setStatus("new");
        requestService.updateStatusById(request.getStatus(),request.getId());
        Mockito.verify(requestRepository, Mockito.times(1)).updateStatusById(request.getStatus(),request.getId());
    }
}