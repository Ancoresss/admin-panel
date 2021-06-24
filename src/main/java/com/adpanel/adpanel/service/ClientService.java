package com.adpanel.adpanel.service;

import com.adpanel.adpanel.model.Client;

import java.util.List;

public interface ClientService {
    List<Client> getClients();
    void addClient(Client client);
    void deleteClient(long id);
    Client getClient(long id);
}
