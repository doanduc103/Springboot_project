package com.example.demo.model.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserReq {
	@NotEmpty(message = "Họ tên không được để trống")
	private String name;
	@NotEmpty(message = "Email không được để trống")
	private String email;
	@NotEmpty(message = "Mật khẩu không được để trống")
	@Size(min = 4, max = 20, message = "Mật khẩu phải chứa từ 4-20 kí tự")
	private String password;

}
