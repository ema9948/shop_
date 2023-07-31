package com.cristianAlbornoz.ShopRopa.controller.adminControllers;

import com.cristianAlbornoz.ShopRopa.DTO.AdminDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;
import com.cristianAlbornoz.ShopRopa.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @GetMapping("adminGet")
    public ResponseEntity<ResponseGen> adminGet() {
        return new ResponseEntity<>(adminService.adminGet(), HttpStatus.OK);
    }

    @PatchMapping("adminPatch")
    public ResponseEntity<ResponseGen> detailsAdminUpdate(@RequestBody AdminDTO adminDTO) {
        return new ResponseEntity<>(adminService.adminPatch(adminDTO), HttpStatus.OK);
    }

}
