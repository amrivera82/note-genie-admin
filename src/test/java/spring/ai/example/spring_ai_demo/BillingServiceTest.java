package spring.ai.example.spring_ai_demo;

import com.thoughtful.notegenie.admin.data.model.Customer;
import com.thoughtful.notegenie.admin.data.model.Invoice;
import com.thoughtful.notegenie.admin.data.model.Payment;
import com.thoughtful.notegenie.admin.data.model.Product;
import com.thoughtful.notegenie.admin.service.BillingService;
import com.thoughtful.notegenie.admin.type.InvoiceStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BillingServiceTest {

    @Autowired
    private BillingService billingService;

    @Test
    public void testInvoiceGeneration() {
        // Test setup
        Customer customer = new Customer();
        Product product = new Product();

        // Test execution
        Invoice invoice = billingService.generateInvoice(customer.getId(), List.of(product));

        // Assertions
        assertNotNull(invoice);
        assertEquals(InvoiceStatus.CREATED, invoice.getStatus());
    }

    @Test
    public void testPaymentProcessing() {
        // Test setup
        Invoice invoice = new Invoice();

        // Test execution
        billingService.processPayment(invoice, Payment.builder().build());

        // Assertions
        assertNotNull(invoice.getPaymentId());
        assertEquals(InvoiceStatus.PAID, invoice.getStatus());
    }
}
