package uz.pdp.ware_house.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import uz.pdp.ware_house.entity.Measurement;
import uz.pdp.ware_house.payload.Result;
import uz.pdp.ware_house.service.MeasurementService;

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
    public Page<Measurement> getMeasurement(@RequestParam int page){
        return measurementService.getMeasurements(page);
    }

    @GetMapping("/{id}")
    public Result getOneMeasurement(@PathVariable Integer id){
        return measurementService.getOneMeasurement(id);
    }

    @DeleteMapping("/{id}")
    public Result deleteMeasurement(@PathVariable Integer id){
        return measurementService.deleteMeasurement(id);
    }

    @PutMapping("/{id}")
    public Result editMeasurement(@PathVariable Integer id, @RequestBody Measurement measurement){
        return measurementService.editMeasurement(id,measurement);
    }

}
