package com.medicalcenter.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.medicalcenter.dto.UserDTO;
import com.medicalcenter.entities.User;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface UserMapper {
	UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

	UserDTO userToUserDTO(User user);

	// @Mapping(target = "roles", ignore = true)
	User userDTOToUser(UserDTO userDTO);

}
