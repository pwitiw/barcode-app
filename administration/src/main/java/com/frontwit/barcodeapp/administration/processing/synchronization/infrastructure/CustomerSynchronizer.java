package com.frontwit.barcodeapp.administration.processing.synchronization.infrastructure;

import com.frontwit.barcodeapp.administration.infrastructure.db.CustomerEntity;
import com.frontwit.barcodeapp.administration.processing.synchronization.SaveSynchronizedCustomer;
import com.frontwit.barcodeapp.administration.processing.synchronization.SourceRepository;
import com.frontwit.barcodeapp.administration.processing.synchronization.TargetOrder;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CustomerSynchronizer {
    private SaveSynchronizedCustomer saveSynchronizedCustomers;
    private SourceRepository sourceRepository;

    void synchronizeCustomers() {
        List<CustomerEntity> customers = sourceRepository.findCustomers();
        customers.forEach(this::saveCustomers);
    }

    private void saveCustomers(CustomerEntity customerEntity) {
        var customer = new TargetOrder.Customer(customerEntity.getId(), customerEntity.getName(), customerEntity.getAddress(), customerEntity.getRoute());
        saveSynchronizedCustomers.save(customer);
    }
}
