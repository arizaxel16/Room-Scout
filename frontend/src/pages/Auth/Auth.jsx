import React, { useEffect } from "react";
import "./Auth.css";
import LoginRegisterForm from "../../components/Auth/LoginRegisterForm/LoginRegisterForm";
import Logo from "../../components/Generic/Logo/Logo";
import { useTheme } from '../../context/ThemeContext';

const Auth = () => {
	const { forceTheme, restorePreviousTheme } = useTheme();

    useEffect(() => {
        forceTheme('dark');

        return () => {
            restorePreviousTheme();
        };
    }, [forceTheme, restorePreviousTheme]);

	return (
		<div className="login-register-page">
			<div className="logo-container-auth">
				<Logo />
			</div>
			<LoginRegisterForm />
		</div>
	);
};

export default Auth;
