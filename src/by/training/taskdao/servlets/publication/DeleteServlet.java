package by.training.taskdao.servlets.publication;

import by.training.taskdao.dao.factory.DAOFactory;
import by.training.taskdao.dao.interfaces.PaymentDAO;
import by.training.taskdao.dao.interfaces.PublicationDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int publicationId = Integer.valueOf(String.valueOf(req.getParameter(
                                                        "publicationId")));
        DAOFactory daoFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        PublicationDAO publicationDAO = daoFactory.getPublicationDAO();
        try {
            publicationDAO.delete(publicationId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().write("Done");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
