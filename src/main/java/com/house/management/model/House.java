package com.house.management.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return id == house.id && roomNumber == house.roomNumber && floor == house.floor && size == house.size && Objects.equals(address, house.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNumber, floor, size, address);
    }
}
