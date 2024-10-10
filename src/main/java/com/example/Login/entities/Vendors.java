package com.example.Login.entities;

import com.example.Login.constants.TableNames;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = TableNames.VENDOR)
@Getter
@Setter
@Builder
public class Vendors {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vendor_id")
    private Integer vendorId;

    private String vendorName;
}
