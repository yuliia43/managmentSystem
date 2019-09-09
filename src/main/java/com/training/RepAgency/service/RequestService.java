package com.training.RepAgency.service;

import com.training.RepAgency.dto.RequestDTO;
import com.training.RepAgency.entity.Request;
import com.training.RepAgency.repository.RequestRepository;
import com.training.RepAgency.repository.UserRepository;
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
        if (requestRepository.findByCreator(creator).isPresent()) {
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
        return null;
    }

    public List<Request> getRequestsByStatus(String status) {
        return requestRepository.findByStatus(status).orElse(null);
    }

    public Request findRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public void updateStatusAndMasterById(String status, Long id, String master, String reason, Long price) {
        Request tempRequest = requestRepository.findById(id).orElseThrow(RuntimeException::new);
        requestRepository.save(Request.builder()
                .id(id)
                .creator(tempRequest.getCreator())
                .request(tempRequest.getRequest())
                .status(status)
                .reason(reason)
                .price(price)
                .master(userRepository.findByEmail(master).orElse(null))
                .build());
        //requestRepository.updateStatusAndMasterById(status, id, userRepository.findByEmail(master).orElseThrow(RuntimeException::new), reason, price);
    }

    public List<Request> findAllRequests() {
        return Optional.ofNullable(requestRepository.findAll())
                .orElseThrow(RuntimeException::new);
    }

    public void updateStatusById(String status, Long id) {
        requestRepository.updateStatusById(status, id);
    }

    public List<Request> getRequestsByStatusAndEmail(String status, String email) {
        List<Request> requests = requestRepository.findByStatusAndEmail(email, status).orElseThrow(RuntimeException::new);
        requests.stream().forEach(r -> log.info(r.getRequest()));
        return requests;
    }
}
