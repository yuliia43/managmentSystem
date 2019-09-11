package com.training.RepAgency.service;

import com.training.RepAgency.dto.RequestDTO;
import com.training.RepAgency.dto.RequestInfoDTO;
import com.training.RepAgency.entity.Request;
import com.training.RepAgency.repository.RequestRepository;
import com.training.RepAgency.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    public Page<RequestDTO> getRequestsByCreator(String creator, Pageable pageable) {
        if (requestRepository.findByCreator(creator).isPresent()) {
            List<RequestDTO> temp= requestRepository.findByCreator(creator)
                    .get().stream()
                    .map(r -> RequestDTO.builder()
                            .request(r.getRequest())
                            .id(r.getId())
                            .status(r.getStatus())
                            .reason(r.getReason())
                            .price(r.getPrice())
                            .build())
                    .collect(Collectors.toList());
            return new PageImpl<RequestDTO>(temp);
        }
        return null;
    }

    public Page<Request> getRequestsByStatus(String status, Pageable pageable) {
        return requestRepository.findByStatus(status, pageable);

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


    public void updateStatusById(String status, Long id) {
        requestRepository.updateStatusById(status, id);
    }

    public Page<Request> getRequestsByStatusAndEmail(String status, String email, Pageable pageable) {
        return requestRepository.findByStatusAndEmail(email, status, pageable);
    }
}
