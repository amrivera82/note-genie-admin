package com.thoughtful.notegenie.admin.data.repository;

import com.thoughtful.notegenie.admin.data.model.Invoice;
import com.thoughtful.notegenie.admin.type.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findByCustomerId(Long customerId);

    List<Invoice> findByStatusIn(@Param("statuses") List<InvoiceStatus> statuses);
}

