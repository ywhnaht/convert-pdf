<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
    * {
        margin: 0;
        padding: 0;
        box-sizing: border-box;
    }

    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: linear-gradient(135deg, #a8e6cf, #7fcdcd);
        min-height: 100vh;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 20px;
    }

    .login-container {
        background: rgba(255, 255, 255, 0.95);
        backdrop-filter: blur(10px);
        border-radius: 20px;
        box-shadow: 0 15px 35px rgba(127, 205, 205, 0.3);
        padding: 40px;
        width: 100%;
        max-width: 420px;
        border: 1px solid rgba(168, 230, 207, 0.2);
        animation: slideUp 0.6s ease-out;
    }

    .login-header {
        text-align: center;
        margin-bottom: 30px;
    }

    .login-header h2 {
        color: #2d5a52;
        font-size: 28px;
        font-weight: 600;
        margin-bottom: 8px;
    }

    .login-header p {
        color: #4a7c74;
        font-size: 14px;
        opacity: 0.8;
    }

    .form-group {
        margin-bottom: 20px;
        position: relative;
        animation: fadeInUp 0.6s ease-out forwards;
        opacity: 0;
    }

    .form-group:nth-child(1) { animation-delay: 0.1s; }
    .form-group:nth-child(2) { animation-delay: 0.2s; }

    .form-group label {
        display: block;
        color: #2d5a52;
        font-weight: 500;
        margin-bottom: 8px;
        font-size: 14px;
    }

    .input-wrapper {
        position: relative;
    }

    .form-group input[type="text"],
    .form-group input[type="password"] {
        width: 100%;
        padding: 15px 20px;
        border: 2px solid #e0f7f0;
        border-radius: 12px;
        font-size: 16px;
        transition: all 0.3s ease;
        background: #f8fffe;
        color: #2d5a52;
    }

    .form-group input:focus {
        outline: none;
        border-color: #7fcdcd;
        box-shadow: 0 0 0 3px rgba(127, 205, 205, 0.1);
        transform: translateY(-2px);
    }

    .form-group input:hover {
        border-color: #a8e6cf;
    }

    .button-group {
        display: flex;
        gap: 15px;
        margin-top: 25px;
        animation: fadeInUp 0.6s ease-out 0.3s forwards;
        opacity: 0;
    }

    .btn {
        flex: 1;
        padding: 16px 20px;
        border: none;
        border-radius: 12px;
        font-size: 16px;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
        text-transform: uppercase;
        letter-spacing: 0.5px;
        position: relative;
        overflow: hidden;
    }

    .btn-login {
        background: linear-gradient(135deg, #7fcdcd 0%, #5fb3b3 100%);
        color: white;
    }

    .btn-login:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(127, 205, 205, 0.4);
        background: linear-gradient(135deg, #6bb8b8 0%, #4a9999 100%);
    }

    .btn-reset {
        background: linear-gradient(135deg, #f8f9fa 0%, #e9ecef 100%);
        color: #2d5a52;
        border: 2px solid #e0f7f0;
    }

    .btn-reset:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 25px rgba(0, 0, 0, 0.1);
        background: linear-gradient(135deg, #e9ecef 0%, #dee2e6 100%);
        border-color: #7fcdcd;
    }

    .btn:active {
        transform: translateY(0);
    }

    .btn::before {
        content: '';
        position: absolute;
        top: 50%;
        left: 50%;
        width: 0;
        height: 0;
        border-radius: 50%;
        background: rgba(255, 255, 255, 0.3);
        transform: translate(-50%, -50%);
        transition: width 0.6s, height 0.6s;
    }

    .btn:active::before {
        width: 300px;
        height: 300px;
    }

    .error-message {
        background: linear-gradient(135deg, #ff6b6b, #ee5a52);
        color: white;
        padding: 12px 20px;
        border-radius: 10px;
        margin-top: 20px;
        font-size: 14px;
        text-align: center;
        box-shadow: 0 4px 15px rgba(255, 107, 107, 0.2);
        animation: shake 0.5s ease-in-out;
    }

    .login-footer {
        text-align: center;
        margin-top: 25px;
        padding-top: 20px;
        border-top: 1px solid rgba(127, 205, 205, 0.2);
        animation: fadeInUp 0.6s ease-out 0.4s forwards;
        opacity: 0;
    }

    .login-footer p {
        color: #4a7c74;
        font-size: 14px;
    }

    .login-footer a {
        color: #7fcdcd;
        text-decoration: none;
        font-weight: 600;
        transition: color 0.3s ease;
    }

    .login-footer a:hover {
        color: #5fb3b3;
        text-decoration: underline;
    }

    @keyframes fadeInUp {
        from {
            opacity: 0;
            transform: translateY(20px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

    @keyframes shake {
        0%, 100% { transform: translateX(0); }
        10%, 30%, 50%, 70%, 90% { transform: translateX(-3px); }
        20%, 40%, 60%, 80% { transform: translateX(3px); }
    }

    @keyframes slideUp {
        from {
            opacity: 0;
            transform: translateY(30px);
        }
        to {
            opacity: 1;
            transform: translateY(0);
        }
    }

</style>
</head>
<body>
    <%
        String temp = "temp";
        session.setAttribute("temp", temp);
    %>

    <div class="login-container">
        <div class="login-header">
            <h2>ĐĂNG NHẬP</h2>
        </div>

        <form name="loginForm" action="Login" method="post">
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <div class="input-wrapper">
                    <input type="text" id="username" name="username" required placeholder="Nhập tên đăng nhập" />
                </div>
            </div>

            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <div class="input-wrapper">
                    <input type="password" id="password" name="password" required placeholder="Nhập mật khẩu" />
                </div>
            </div>

            <div class="button-group">
                <button type="submit" class="btn btn-login">Đăng nhập</button>
                <button type="reset" class="btn btn-reset">Đặt lại</button>
            </div>
        </form>

        <%
            String error = (String) session.getAttribute("error-massage");
            if (error != null) {
        %>
            <div class="error-message"><%= error %></div>
        <%
                session.removeAttribute("error-massage"); 
            }
        %>

        <div class="login-footer">
            <p>Chưa có tài khoản? <a href="Register.jsp">Đăng ký</a></p>
        </div>
    </div>

</body>
</html>