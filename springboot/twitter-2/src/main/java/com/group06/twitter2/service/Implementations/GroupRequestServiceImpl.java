package com.group06.twitter2.service.Implementations;

import com.group06.twitter2.repository.GroupRequestRepository;
import com.group06.twitter2.service.GroupRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupRequestServiceImpl implements GroupRequestService {
    @Autowired
    GroupRequestRepository groupRequestRepository;

    // TODO: Add methods here
}
