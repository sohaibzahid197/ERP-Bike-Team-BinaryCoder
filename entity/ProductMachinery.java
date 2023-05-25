package com.soen390.team11.entity;

import com.soen390.team11.constant.MachineryState;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Database Entity for Product Machinery
 */

/**
 * This class represents the database entity for Product Machinery.
 * It stores information about a specific machinery used in the production process of a product.
 * Each machinery has a unique identifier (id), a name, a status indicating its operational state,
 * a timer representing the time remaining for the current operation, and a reference to the associated product.
 * The status of the machinery can be one of the predefined values defined in the MachineryState enum.
 * The timer indicates the remaining time for the current operation being performed by the machinery.
 * The product field represents the product associated with the machinery.
 * This class provides methods to access and modify the attributes of the machinery, including setting the status,
 * updating the timer, and assigning a product to the machinery.
 */


@Entity
public class ProductMachinery {
    @Id
    @GenericGenerator(name="machineryId", strategy = "com.soen390.team11.generator.ProductMachineryIDGenerator")
    @GeneratedValue(generator="machineryId")
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MachineryState status;

    @Column(nullable = false)
    private int timer;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public ProductMachinery() {
    }

    public ProductMachinery(String name, MachineryState status, int timer, Product product) {
        this.name = name;
        this.status = status;
        this.timer = timer;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MachineryState getStatus() {
        return status;
    }

    // Updates the status of the machinery if the transition is valid, returns true if successful.
    public boolean setStatus(MachineryState status) {
        if (MachineryState.validateStateTransition(this.status, status)) {
            this.status = status;
            return true;
        }
        return false;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Product getProduct() {
        return product;
    }

    /**
 * Assigns a product to the machinery if the machinery is currently in the "UNASSIGNED" state.Returns true if the product is successfully assigned, false otherwise.
 */
    public boolean setProduct(Product product) {
        if (this.status.equals(MachineryState.UNASSIGNED)) {
            this.product = product;
            return true;
        }
        return false;
    }
}
