package main.entity;

import javax.persistence.*;

@Entity
public class Cart {
    private @Id @GeneratedValue Long id;
    private int total;

    public Cart() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
