package com.training.RepAgency.service;

import com.training.RepAgency.dto.RequestDTO;
import com.training.RepAgency.entity.Request;
import com.training.RepAgency.entity.User;
import com.training.RepAgency.repository.RequestRepository;
import com.training.RepAgency.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RequestService {

    private final RequestRepository requestRepository;

    private final UserRepository userRepository;

    public RequestService(RequestRepository requestRepository, UserRepository userRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }

    public Request saveRequest(String request, Date deadline, String name, String master) {
        return requestRepository.save(
                Request.builder()
                        .request(request)
                        .status("new")
                        .deadline(deadline)
                        .master(userRepository.findByEmail(master).orElse(null))
                        .creator(name)
                        .build()
        );
    }

    public Optional<Request> saveRequest(Request request) {
        return Optional.ofNullable(requestRepository.save(request));
    }


    public Page<RequestDTO> getRequestsByCreator(String creator, String status, Pageable pageable) {


        return requestRepository.findByCreatorAndStatusNot(creator, status)
                .<Page<RequestDTO>>map(requests -> new PageImpl<>(requests.stream()
                .map(this::buildRequest)
                .collect(Collectors.toList())))
                .orElseThrow(RuntimeException::new);
    }

    private RequestDTO buildRequest(Request r) {
        return RequestDTO.builder()
                .request(r.getRequest())
                .id(r.getId())
                .status(r.getStatus())
                .deadline(r.getDeadline())
                .build();
    }

    public Page<Request> getRequestsByStatus(String status, Pageable pageable) {
        return requestRepository.findByStatus(status, pageable);

    }

    public Request findRequestById(Long id) {
        return requestRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    /*public void updateStatusAndMasterById(String status, Long id, String master, String reason, Long price) {
        Request tempRequest = requestRepository.findById(id).orElseThrow(RuntimeException::new);
        requestRepository.save(Request.builder()
                .id(id)
                .creator(tempRequest.getCreator())
                .request(tempRequest.getRequest())
                .status(status)
                .deadline(reason)
                .price(price)
                .master(userRepository.findByEmail(master).orElse(null))
                .build());
    }*/


    public void updateStatusById(String status, Long id) {
        requestRepository.updateStatusById(status, id);
    }

    public void updateStatusAndReasonById(String status, Long id, String reason) {
        requestRepository.updateStatusAndReasonById(status, id, reason);
    }

    public Page<Request> getRequestsByStatusAndEmail(String status, String email, Pageable pageable) {
        return requestRepository.findByStatusAndEmail(email, status, pageable);
    }

    public Optional<List<Request>> getByCreatorAndStatus(String creator, String status) {
        return requestRepository.findByCreatorAndStatus(creator, status);
    }
}
