package com.example.demo.model.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticateReq {
	@NotEmpty(message = "Email is required")
	@Email(message = "Dia chi email khong chinh xac")
	private String email;
//	@NotNull
//	@NotEmpty
//	private String name;
	@NotEmpty(message = "password is required")
	@Size(min = 4, max = 16, message = "Password must be between 4-16 characters")
	private String password;
}
