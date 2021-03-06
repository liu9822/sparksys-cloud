package com.sparksys.authorization.infrastructure.convert;

import com.sparksys.authorization.infrastructure.po.AuthUserDO;
import com.sparksys.authorization.interfaces.dto.AuthUserDTO;
import com.sparksys.authorization.interfaces.dto.AuthUserSaveDTO;
import com.sparksys.authorization.interfaces.dto.AuthUserStatusDTO;
import com.sparksys.authorization.interfaces.dto.AuthUserUpdateDTO;
import com.sparksys.commons.core.entity.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * description: AuthUser对象Convert
 *
 * @Author zhouxinlei
 * @Date 2020-06-05 21:28:06
 */
@Mapper
public interface AuthUserConvert {

    AuthUserConvert INSTANCE = Mappers.getMapper(AuthUserConvert.class);

    AuthUserDO convertAuthUserDO(AuthUserSaveDTO authUserSaveDTO);

    AuthUserDO convertAuthUserDO(AuthUserUpdateDTO authUserUpdateDTO);

    AuthUserDO convertAuthUserDO(AuthUserStatusDTO authUserStatusDTO);

    AuthUser convertAuthUser(AuthUserDO authUserDO);

    AuthUserDTO convertAuthUserDTO(AuthUserDO authUserDO);

}
