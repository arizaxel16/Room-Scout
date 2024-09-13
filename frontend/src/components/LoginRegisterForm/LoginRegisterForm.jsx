import React from 'react';
import './LoginRegisterForm.css';
import { FaUser, FaLock, FaEnvelope } from "react-icons/fa";
import { HiIdentification } from "react-icons/hi2";

const LoginRegisterForm = () => {
    const [action, setAction] = React.useState('');
    const registerLink = () => {
        setAction('active');
    }

    const loginLink = () => {
        setAction('');
    }

    return (
        <div className={`form-container ${action}`}>
            <div className='form-box login'>
                <form action="">
                    <h1>Login</h1>
                    <div className='input-box'>
                        <input type="text" placeholder='Enter your email' required/>
                        <FaEnvelope className="icon"/>
                    </div>
                    <div className='input-box'>
                        <input type="password" placeholder='Enter your password' required/>
                        <FaLock className="icon"/>
                    </div>
                    <div className='remember-forgot'>
                        <label className='label-holder'><input type="checkbox"/>Remember me</label>
                        <a href="#">Forgot password?</a>
                    </div>

                    <button type='submit'>Login</button>
                    <div className='register-link'>
                        <p>Don't have an account? <a href="#" onClick={registerLink}>Register</a></p>
                    </div>
                </form>
            </div>

            <div className='form-box register'>
                <form action="">
                    <h1>Registration</h1>
                    <div className='input-box'>
                        <input type="text" placeholder='Enter your full name' required/>
                        <FaUser className="icon"/>
                    </div>
                    <div className='input-box'>
                        <input type="text" placeholder='Enter your Id' required/>
                        <HiIdentification className="icon"/>
                    </div>
                    <div className='input-box'>
                        <input type="text" placeholder='Enter your email' required/>
                        <FaEnvelope className="icon"/>
                    </div>
                    <div className='input-box'>
                        <input type="password" placeholder='Enter your password' required/>
                        <FaLock className="icon"/>
                    </div>
                    <div className='remember-forgot'>
                        <label className='label-holder'><input type="checkbox" required/>I agree to the terms & conditions</label>
                    </div>

                    <button type='submit'>Register</button>
                    <div className='register-link'>
                        <p>Already have an account? <a href="#" onClick={loginLink}>Login</a></p>
                    </div>
                </form>
            </div>
        </div>
    );
};

export default LoginRegisterForm;
