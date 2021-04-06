package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Measurement;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.MeasurementService;

import java.util.*;

@RestController
@RequestMapping("/measurement")
public class MeasurementController {
    @Autowired
    MeasurementService measurementService;

    @PostMapping
    public Result addMeasurement(@RequestBody Measurement measurement){
        return measurementService.addMeasurement(measurement);
    }

    @GetMapping
    public List<Measurement> getAll(){
        return measurementService.getAll();
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable Integer id){
        return measurementService.getOneMeasurement(id);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        return measurementService.deleteMeasurement(id);
    }

    @PutMapping("/{id}")
    public Result edit(@PathVariable Integer id, @RequestBody Measurement measurement){
        return measurementService.editMeasurement(id,measurement);
    }

}
