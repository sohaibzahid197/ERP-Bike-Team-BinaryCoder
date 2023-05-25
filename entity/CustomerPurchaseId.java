package com.soen390.team11.entity;
/**

This class represents an embeddable ID for a customer purchase in the system.
It is used to uniquely identify a customer's purchase by combining the customer ID, product ID, and invoice ID.
The class is marked as @Embeddable, indicating that its fields will be embedded within the owning entity.
It implements Serializable to support serialization and deserialization of the object.
The class provides getters and setters for each field and overrides the equals() and hashCode() methods
to enable proper comparison and hashing of objects based on their customer ID, product ID, and invoice ID.
*/
import com.sun.istack.NotNull;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * embeddable id of customer purchase
 */

@Embeddable
public class CustomerPurchaseId implements Serializable {
    @NotNull
    private String customerID;
    @NotNull
    private String productID;
    @NotNull
    private String invoiceID;

    public CustomerPurchaseId() {
    }

    public CustomerPurchaseId(String customerID, String productID, String invoiceID) {
        this.customerID = customerID;
        this.productID = productID;
        this.invoiceID = invoiceID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(String invoiceID) {
        this.invoiceID = invoiceID;
    }

   
   /**
 * Overrides the equals() method to compare two CustomerPurchaseId objects for equality.
 * Two objects are considered equal if they have the same customer ID, product ID, and invoice ID.
 */
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerPurchaseId that = (CustomerPurchaseId) o;
        return Objects.equals(customerID, that.customerID) && Objects.equals(productID, that.productID) && Objects.equals(invoiceID, that.invoiceID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerID, productID, invoiceID);
    }
}

