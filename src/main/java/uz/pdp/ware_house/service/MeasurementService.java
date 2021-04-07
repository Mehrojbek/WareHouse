package uz.pdp.ware_house.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.pdp.ware_house.entity.Measurement;
import uz.pdp.ware_house.payload.Deleted;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.repository.MeasurementRepository;

import java.util.*;
import java.util.Optional;


@Service
public class MeasurementService {
    @Autowired
    MeasurementRepository measurementRepository;

    //CREATE
    public Result addMeasurement(Measurement measurement) {

        boolean existsByName = measurementRepository.existsByName(measurement.getName());
        if (existsByName){
            Measurement byName = measurementRepository.findByName(measurement.getName());
            if (byName.isActive())
            return new Result("Bu o'lchov birligi avval qo'shilgan", false);
            byName.setActive(true);
            measurementRepository.save(byName);
            return new Result("O'lchov birligi muvaffaqiyatli qo'shildi", true);
        }
        measurementRepository.save(measurement);
        return new Result("O'lchov birligi muvaffaqiyatli qo'shildi", true);
    }


    //READ ALL
    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }


    //READ ONE
    public Result getOneMeasurement(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        return optionalMeasurement.isPresent() ? new Result("Ushbu o'lchov birligi topilmadi", false) :
                new Result("O'lchov birligi muvaffaqiyatli topildi", true, optionalMeasurement.get());
    }


    //DELETE
    public Result deleteMeasurement(Integer id) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent())
            return new Result("Ushbu o'lchov birligi topilmadi", false);

        Measurement measurement = optionalMeasurement.get();
        measurement.setActive(false);
        measurementRepository.save(measurement);
        return new Result("Ushbu o'lchov birligi muvaffaqiyatli uchirildi", true);
    }


    //PUT
    public Result editMeasurement(Integer id, Measurement measurement) {
        Optional<Measurement> optionalMeasurement = measurementRepository.findById(id);
        if (!optionalMeasurement.isPresent())
            return new Result("Ushbu o'lchov birligi topilmadi", false);
        Measurement editingMeasurement = optionalMeasurement.get();

        //CHECK UNIQUE MEASUREMENT_NAME
        if (!editingMeasurement.getName().equals(measurement.getName())) {
            boolean existsByName = measurementRepository.existsByName(measurement.getName());
            if (existsByName)
                return new Result("Siz nomni ushbu o'lchov birligiga o'zgartira olmaysiz, " +
                        "Bu nom allaqachon mavjud", false);
            editingMeasurement.setName(measurement.getName());
        }

        measurementRepository.save(editingMeasurement);
        return new Result("O'lchov birligi muvaffaqiyatli o'zgartirildi", true);
    }


}
