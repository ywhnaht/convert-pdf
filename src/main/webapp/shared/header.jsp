<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page import="model.bean.User" %>
        <% User currentUser=(User) session.getAttribute("user"); if (currentUser==null) {
            response.sendRedirect("/Login"); } String username=currentUser.getUsername(); %>

            <header class="app-header">
                <div class="header-container">
                    <!-- Logo & App Name -->
                    <div class="header-brand">
                        <div class="brand-icon">📄</div>
                        <div class="brand-info">
                            <h1 class="brand-title">PDF Converter</h1>
                            <span class="brand-subtitle">PDF to Word Tool</span>
                        </div>
                    </div>

                    <!-- User Menu -->
                    <div class="header-user">
                        <div class="user-info">
                            <div class="user-avatar">
                                <span class="avatar-text">
                                    <%= username.substring(0, 1).toUpperCase() %>
                                </span>
                            </div>
                            <div class="user-details">
                                <span class="user-name">Xin chào, <%= username %></span>
                                <span class="user-role">Member</span>
                            </div>
                        </div>

                        <div class="user-menu">
                            <button class="menu-toggle" id="userMenuToggle">
                                <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                    stroke-width="2">
                                    <polyline points="6,9 12,15 18,9"></polyline>
                                </svg>
                            </button>

                            <div class="dropdown-menu" id="userDropdown">
                                <a href="<%= request.getContextPath() %>/historyFile" class="dropdown-item">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                        stroke-width="2">
                                        <path d="M3 3h18v18H3zM9 9h6v6H9z" />
                                    </svg>
                                    Lịch sử file
                                </a>
                                <a href="<%= request.getContextPath() %>/upload" class="dropdown-item">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                        stroke-width="2">
                                        <path d="M3 3h18v18H3zM9 9h6v6H9z" />
                                    </svg>
                                    Convert file
                                </a>

                                <!-- <a href="<%= request.getContextPath() %>/views/profile.jsp" class="dropdown-item">
                        <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2">
                            <path d="M20 21v-2a4 4 0 0 0-4-4H8a4 4 0 0 0-4 4v2"/>
                            <circle cx="12" cy="7" r="4"/>
                        </svg>
                        Thông tin cá nhân
                    </a> -->

                                <div class="dropdown-divider"></div>

                                <a href="<%= request.getContextPath() %>/LogoutServlet"
                                    class="dropdown-item logout-item">
                                    <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor"
                                        stroke-width="2">
                                        <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4" />
                                        <polyline points="16,17 21,12 16,7" />
                                        <line x1="21" y1="12" x2="9" y2="12" />
                                    </svg>
                                    Đăng xuất
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </header>

            <script>
 
                document.addEventListener('DOMContentLoaded', function () {
                    const menuToggle = document.getElementById('userMenuToggle');
                    const dropdown = document.getElementById('userDropdown');

                    if (menuToggle && dropdown) {
                        menuToggle.addEventListener('click', function (e) {
                            e.stopPropagation();
                            dropdown.classList.toggle('show');
                        });

                        document.addEventListener('click', function () {
                            dropdown.classList.remove('show');
                        });

                        dropdown.addEventListener('click', function (e) {
                            e.stopPropagation();
                        });
                    }
                });
            </script>