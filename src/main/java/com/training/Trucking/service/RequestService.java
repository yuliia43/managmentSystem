package com.training.Trucking.service;

import com.training.Trucking.dto.RequestDTO;
import com.training.Trucking.entity.Request;
import com.training.Trucking.repository.RequestRepository;
import com.training.Trucking.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    public Request saveRequest(String request, String name) {
        return requestRepository.save(
                Request.builder()
                        .request(request)
                        .status("new")
                        .price(0L)
                        .creator(name)
                        .build()
        );
    }

    public Request saveRequest(Request request) {
        return requestRepository.save(request);
    }

    public List<RequestDTO> getRequestsByCreator(String creator) {
        return requestRepository.findByCreator(creator)
                .get().stream()
                .map(r -> RequestDTO.builder()
                        .request(r.getRequest())
                        .id(r.getId())
                        .status(r.getStatus())
                        .reason(r.getReason())
                        .price(r.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status)
                .orElseThrow(RuntimeException::new);
    }

    public Request findRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void updateStatusAndMasterById(String status, Long id, String master, String reason, Long price) {
        requestRepository.updateStatusAndMasterById(status, id, userRepository.findIdByEmail(master).get(), reason, price);
    }

    public List<Request> findAllRequests() {
        return Optional.ofNullable(requestRepository.findAll())
                .orElseThrow(RuntimeException::new);
    }

    public void updateStatusById(String status, Long id) {
        requestRepository.updateStatusById(status, id);
    }

    public  List<Request> getRequestsByStatusAndEmail(String status, String email){
        List<Request> requests =requestRepository.findByStatusAndEmail(email, status).orElseThrow(RuntimeException::new);
        requests.stream().forEach(r->log.info(r.getRequest()));
        return requests;
    }
}