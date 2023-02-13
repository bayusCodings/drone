package com.musala.drone.util;

import com.musala.drone.dto.medication.MedicationInput;
import com.musala.drone.model.Medication;

import java.util.ArrayList;
import java.util.List;

public class MedicationUtil {
    public static MedicationInput getMockedMedicationInput() {
        return MedicationInput.builder()
            .name("Aspirin-1")
            .weight(40)
            .code("CW11")
            .imageUrl("http://aspirin-image-url")
            .build();
    }

    public static Medication getMockedMedication() {
        return Medication.builder()
            .name("Aspirin-1")
            .weight(40)
            .code("CW11")
            .imageUrl("http://aspirin-image-url")
            .build();
    }

    public static List<Medication> getMockedMedicationList() {
        List<Medication> medicationList = new ArrayList<>();
        medicationList.add(MedicationUtil.getMockedMedication());
        return medicationList;
    }
}
