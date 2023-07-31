package com.cristianAlbornoz.ShopRopa.service;


import com.cristianAlbornoz.ShopRopa.DTO.AdminDTO;
import com.cristianAlbornoz.ShopRopa.Utils.ResponseGen;

public interface AdminService {

    ResponseGen adminGet();

    ResponseGen adminPatch(AdminDTO adminDTO);

}
