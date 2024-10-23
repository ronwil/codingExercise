package com.example.codingexercise;

import com.example.codingexercise.entity.ProductPackage;
import com.example.codingexercise.gateway.ProductServiceGateway;
import com.example.codingexercise.service.PackageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PackageControllerTests {

	private final TestRestTemplate restTemplate;
    private final PackageService packageService;

    private final ProductServiceGateway productServiceGateway;

    @Autowired
    PackageControllerTests(TestRestTemplate restTemplate, PackageService packageService, ProductServiceGateway productServiceGateway) {
		this.restTemplate = restTemplate;
        this.packageService = packageService;
        this.productServiceGateway = productServiceGateway;
    }

    @Test
    void testCreatePackage() {
		ResponseEntity<ProductPackage> created = restTemplate.postForEntity("/api/v1/packages", new ProductPackage(null, "Test Name", "Test Desc", List.of("DXSQpv6XVeJm"), null), ProductPackage.class);
        assertEquals(HttpStatus.CREATED, created.getStatusCode(), "Unexpected status code");
        ProductPackage createdBody = created.getBody();
        assertNotNull(createdBody, "Unexpected body");
        assertEquals("Test Name", createdBody.getName(), "Unexpected name");
        assertEquals("Test Desc", createdBody.getDescription(), "Unexpected description");
        assertEquals(List.of("DXSQpv6XVeJm"), createdBody.getProductIds(), "Unexpected products");

        ProductPackage productPackage = packageService.getPackageById("USD", createdBody.getId());
        assertNotNull(productPackage, "Unexpected package");
        assertEquals(createdBody.getId(), productPackage.getId(), "Unexpected id");
        assertEquals(createdBody.getName(), productPackage.getName(), "Unexpected name");
        assertEquals(createdBody.getDescription(), productPackage.getDescription(), "Unexpected description");
        assertEquals(createdBody.getProductIds(), productPackage.getProductIds(), "Unexpected products");
    }

    @Test
    void getPackage() {
        ProductPackage productPackage = packageService.createPackage("USD", new ProductPackage("Test Name 2", "Test Desc 2", null, List.of("7Hv0hA2nmci7", "500R5EHvNlNB"), null));
        ResponseEntity<ProductPackage> fetched = restTemplate.getForEntity("/api/v1/packages/{id}", ProductPackage.class, productPackage.getId());
        assertEquals(HttpStatus.OK, fetched.getStatusCode(), "Unexpected status code");
        ProductPackage fetchedBody = fetched.getBody();
        assertNotNull(fetchedBody, "Unexpected body");
        assertEquals(productPackage.getId(), fetchedBody.getId(), "Unexpected id");
        assertEquals(productPackage.getName(), fetchedBody.getName(), "Unexpected name");
        assertEquals(productPackage.getDescription(), fetchedBody.getDescription(), "Unexpected description");
        assertEquals(productPackage.getProductIds(), fetchedBody.getProductIds(), "Unexpected products");
    }

    @Test
    void listPackages() {
        ProductPackage productPackage1 = packageService.createPackage("USD", new ProductPackage("Test Name 1", "Test Desc 1", null, List.of("500R5EHvNlNB"), null));
        ProductPackage productPackage2 = packageService.createPackage("USD", new ProductPackage("Test Name 2", "Test Desc 2", null, List.of("7Hv0hA2nmci7"), null));

        ResponseEntity<Object> fetched = restTemplate.getForEntity("/api/v1/packages", Object.class);
        assertEquals(HttpStatus.OK, fetched.getStatusCode(), "Unexpected status code");
    }

}
