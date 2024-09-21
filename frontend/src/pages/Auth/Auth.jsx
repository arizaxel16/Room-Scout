import React from "react";
import "./Auth.css";
import LoginRegisterForm from "../../components/Auth/LoginRegisterForm/LoginRegisterForm";
import Logo from "../../components/Generic/Logo/Logo";

const LoginRegister = () => {
	return (
		<div className="login-register-page">
			<div className="logo-container-auth">
				<Logo />
			</div>
			<LoginRegisterForm />
		</div>
	);
};

export default LoginRegister;
