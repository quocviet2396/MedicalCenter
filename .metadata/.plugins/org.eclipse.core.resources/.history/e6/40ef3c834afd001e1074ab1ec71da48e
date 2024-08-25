package com.medicalcenter.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tbMedicineRecord")
@Data
@EqualsAndHashCode(callSuper = false)
public class MedicineRecord extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "medical_record_id", nullable = false)
    private MedicalRecord medicalRecord;

    @ManyToOne
    @JoinColumn(name = "medicine_id", nullable = false)
    private Medicine medicine;

    @Column(nullable = true)
    private String note;

    @Column(nullable = false)
    private String quantity;
    

    public MedicineRecord() {
        super();
    }

	public MedicineRecord(MedicalRecord medicalRecord, Medicine medicine, String note, String quantity) {
		super();
		this.medicalRecord = medicalRecord;
		this.medicine = medicine;
		this.note = note;
		this.quantity = quantity;
		
	}

	
    
    
	
}
