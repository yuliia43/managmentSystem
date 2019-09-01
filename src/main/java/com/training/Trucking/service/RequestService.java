package com.training.Trucking.service;

import com.training.Trucking.dto.RequestDTO;
import com.training.Trucking.entity.Request;
import com.training.Trucking.repository.RequestRepository;
import com.training.Trucking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public Request saveRequest(String request, String name){
        return requestRepository.save(
                Request.builder()
                .request(request)
                .status("new")
                .price(0L)
                .creator(name)
                .build()
        );
    }

    public Request saveRequest(Request request){
        return requestRepository.save(request);
    }

    public List<RequestDTO> getRequestsByCreator(String creator){
        return requestRepository.findByCreator(creator)
                .get().stream()
                .map(r-> RequestDTO.builder()
                        .request(r.getRequest())
                .status(r.getStatus())
                .reason(r.getReason())
                .price(r.getPrice())
                .build())
                .collect(Collectors.toList());
    }

    public List<Request> getRequestsByStatus(String status){
        return requestRepository.findByStatus(status)
                .orElseThrow(RuntimeException::new);
    }

    public Request findRequestById(Long id){
        return requestRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void updateStatusAndMasterById(String status, Long id, String master, String reason, Long price){
         requestRepository.updateStatusAndMasterById(status,id,userRepository.findIdByEmail(master).get(),reason,price);
    }
}
