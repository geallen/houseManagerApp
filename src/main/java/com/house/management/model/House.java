package com.house.management.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "HOUSE")
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "roomnumber")
    @Min(value = 1, message = "Please provide room number")
    private int roomNumber;

    @Column(name = "floor")
    @Min(value = 1, message = "Please provide which floor")
    private int floor;

    @Column(name = "size")
    @Min(value = 1, message = "Please provide size")
    private int size;

	@Column(name = "address", unique=true)
    @NotEmpty(message = "Please provide address")
	private String address;

    public House(){}

    public House(int roomNumber, int floor, int size, String address) {
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.size = size;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
