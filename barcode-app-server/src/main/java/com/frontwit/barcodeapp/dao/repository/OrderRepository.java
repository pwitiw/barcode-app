package com.frontwit.barcodeapp.dao.repository;

import com.frontwit.barcodeapp.model.Order;
import com.itextpdf.text.pdf.Barcode;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.Set;

public interface OrderRepository extends MongoRepository<Order, ObjectId> {

    Collection<Order> findByBarcodeIn(Set<Long> barcodes);

}
