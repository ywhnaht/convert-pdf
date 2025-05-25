<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký</title>
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

        .container {
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(127, 205, 205, 0.3);
            padding: 40px;
            width: 100%;
            max-width: 450px;
            border: 1px solid rgba(168, 230, 207, 0.2);
        }

        .form-header {
            text-align: center;
            margin-bottom: 30px;
        }

        .form-header h2 {
            color: #2d5a52;
            font-size: 28px;
            font-weight: 600;
            margin-bottom: 8px;
        }

        .form-header p {
            color: #4a7c74;
            font-size: 14px;
            opacity: 0.8;
        }

        .form-group {
            margin-bottom: 20px;
            position: relative;
        }

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
        .form-group input[type="password"],
        .form-group input[type="email"] {
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

        .submit-btn {
            width: 100%;
            background: linear-gradient(135deg, #7fcdcd 0%, #5fb3b3 100%);
            color: white;
            border: none;
            padding: 16px 20px;
            border-radius: 12px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            text-transform: uppercase;
            letter-spacing: 0.5px;
            margin-top: 10px;
        }

        .submit-btn:hover {
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(127, 205, 205, 0.4);
            background: linear-gradient(135deg, #6bb8b8 0%, #4a9999 100%);
        }

        .message {
            color: white;
            padding: 12px 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            font-size: 14px;
            text-align: center;
            font-weight: 500;
        }

        .message.error {
            background: linear-gradient(135deg, #ff6b6b, #ee5a52);
            box-shadow: 0 4px 15px rgba(255, 107, 107, 0.2);
        }

        .message.success {
            background: linear-gradient(135deg, #4CAF50, #45a049);
            box-shadow: 0 4px 15px rgba(76, 175, 80, 0.2);
        }

        .login-link {
            text-align: center;
            margin-top: 20px;
        }

        .login-link a {
            color: #2d5a52;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .login-link a:hover {
            color: #7fcdcd;
        }

    </style>
</head>
<body>
    <div class="container">
        <div class="form-header">
            <h2>ĐĂNG KÍ TÀI KHOẢN</h2>
        </div>
        <%
            String error = (String) request.getAttribute("error");
            String success = (String) request.getAttribute("success");
            String message = (String) request.getAttribute("message");
            
            if (error != null) {
        %>
            <div class="message error"><%= error %></div>
        <%
            } else if (success != null) {
        %>
            <div class="message success"><%= success %></div>
        <%
            } else if (message != null) {
        %>
            <div class="message error"><%= message %></div>
        <%
            }
        %>

        <form action="Register" method="post" onsubmit="return validateForm()">
            <div class="form-group">
                <label for="username">Tên đăng nhập</label>
                <div class="input-wrapper">
                    <input type="text" id="username" name="username" required 
                           placeholder="Nhập tên đăng nhập" 
                           value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" />
                </div>
            </div>

            <div class="form-group">
                <label for="password">Mật khẩu</label>
                <div class="input-wrapper">
                    <input type="password" id="password" name="password" required 
                           placeholder="Nhập mật khẩu" />
                </div>
            </div>

            <div class="form-group">
                <label for="confirmPassword">Nhập lại mật khẩu</label>
                <div class="input-wrapper">
                    <input type="password" id="confirmPassword" name="confirmPassword" required 
                           placeholder="Xác nhận mật khẩu" />
                </div>
            </div>

            <button type="submit" class="submit-btn">Đăng ký</button>
        </form>
        
        <div class="login-link">
            <p>Đã có tài khoản? <a href="Login.jsp">Đăng nhập</a></p>
        </div>
    </div>

    <script>
        function validateForm() {
            const username = document.getElementById('username').value;
            const password = document.getElementById('password').value;
            const confirmPassword = document.getElementById('confirmPassword').value;
            
            if (username.length < 8) {
                alert('Tên đăng nhập phải có ít nhất 8 ký tự!');
                return false;
            }
            
            
            if (password.length < 8) {
                alert('Mật khẩu phải có ít nhất 9 ký tự!');
                return false;
            }
            
            if (password !== confirmPassword) {
                alert('Mật khẩu xác nhận không khớp!');
                return false;
            }
            
            return true;
        }
    </script>
</body>
</html>