/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.controller;

import com.model.account.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Anh Quan
 */
public class AccountServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AccountServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AccountServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        Account account = null;
        if (session.getAttribute("account") != null) {
            account = (Account) session.getAttribute("account");
            if (action.equals("updateInfo")) {
                String fullname = request.getParameter("name");
                String email = request.getParameter("email");
                String phoneNumber = request.getParameter("phone");
                String birthday = request.getParameter("birthdate");
                String gender_raw = request.getParameter("gender");
                int gender = 1;
                if (gender_raw.equals("male")) {
                    gender = 1;
                } else if (gender_raw.equals("female")) {
                    gender = 0;
                } else if (gender_raw.equals("other")) {
                    gender = 2;
                }
                boolean result = WebController.getInstance().accountdao.updateAccountById(account.getId(), fullname, phoneNumber, email, gender, birthday, null);
                Account updateAccount = WebController.getInstance().accountdao.getAccountById(account.getId());
                session.setAttribute("account", updateAccount);
                if (result) {
                    request.setAttribute("updateResult", "Cập nhật thành công");
                } else {
                    request.setAttribute("updateResult", "Cập nhật không thành công");
                }
                request.getRequestDispatcher("profile.jsp").forward(request, response);
            } else if (action.equals("password")) {
                String currentPassword = request.getParameter("currentPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");
                if (account.getPassword().equals(currentPassword)) {
                    if (newPassword.equals(confirmPassword)) {
                        boolean result = WebController.getInstance().accountdao.updatePassword(account.getId(), newPassword);
                        if (result) {
                            request.setAttribute("resultChangePass", "Đổi mật khẩu thành công");
                            session.setAttribute("account", account);
                        }else    request.setAttribute("resultChangePass", "Đổi mật khẩu không thành công");
                    }else {
                        request.setAttribute("resultChangePass", "Mật khẩu không trùng khớp");
                    }
                } else {
                    request.setAttribute("resultChangePass", "Sai mật khẩu");
                }
                request.getRequestDispatcher("updatepassword.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
