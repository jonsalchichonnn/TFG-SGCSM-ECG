package com.sgcsm_ecg.common;

import lombok.Data;

@Data
public class UserDTO {
    Integer pageNum;
    Integer pageSize;
    String name;
    Integer sex;
    String account;

    public boolean allNull(){
        return pageNum == null && pageSize == null &&
                name == null && sex == null && account == null;
    }
}
