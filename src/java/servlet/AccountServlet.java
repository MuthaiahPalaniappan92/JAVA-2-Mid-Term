/*
 * Copyright 2015 Len Payne <len.payne@lambtoncollege.ca>.
 * Updated 2015 Mark Russell <mark.russell@lambtoncollege.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Account;

/**
 * Provides an Account Balance and Basic Withdrawal/Deposit Operations
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {

    Account a = new Account();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setHeader("cache-control", "private, no-store,no-cache,must-revalidate");
        response.setHeader("pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try {
            PrintWriter out = response.getWriter();

            out.println(a.getBalance());
        } catch (IOException e) {
            System.out.println("Error Message. " + e.getMessage());
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try (PrintWriter out = response.getWriter()) {
            double depositAmount = Double.parseDouble(request.getParameter("deposit"));
            if (request.getParameter("deposit") != null) {
                a.deposit(depositAmount);
            }
            double withdrawlAmount = Double.parseDouble(request.getParameter("withdraw"));
            if (request.getParameter("withdraw") != null) {
                a.withdraw(withdrawlAmount);
            }
            String closeAccount = request.getParameter("close");
            if (request.getParameter("closeAccount") != null) {
                a.close();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        doGet(request, response);
    }
}
