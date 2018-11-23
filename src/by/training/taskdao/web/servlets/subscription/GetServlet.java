package by.training.taskdao.web.servlets.subscription;

import by.training.taskdao.dao.factory.DAOFactory;
import by.training.taskdao.dao.interfaces.PaymentDAO;
import by.training.taskdao.dao.interfaces.PublicationDAO;
import by.training.taskdao.dao.interfaces.ReaderDAO;
import by.training.taskdao.dao.interfaces.SubscriptionDAO;
import by.training.taskdao.entities.Payment;
import by.training.taskdao.entities.Publication;
import by.training.taskdao.entities.Reader;
import by.training.taskdao.entities.Subscription;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/secure/getSubscription")
public class GetServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
        SubscriptionDAO subscriptionDAO = factory.getSubscriptionDAO();
        ReaderDAO readerDAO = factory.getReaderDAO();
        PaymentDAO paymentDAO = factory.getPaymentDAO();
        PublicationDAO publicationDAO = factory.getPublicationDAO();
        String parameterId = req.getParameter("subscriptionId");
        if (parameterId != null) {
            int subscriptionId = Integer.valueOf(parameterId);
            Subscription entity = null;
            Reader reader = null;
            Payment payment = null;
            Publication publication = null;
            try {
                entity = subscriptionDAO.get(subscriptionId);
                reader = readerDAO.get(entity.getReaderId());
                payment = paymentDAO.get(entity.getPaymentId());
                publication = publicationDAO.get(entity.getPublicationId());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            JSONObject jsonSubscription = new JSONObject();
            JSONObject jsonReader = new JSONObject();
            JSONObject jsonPayment = new JSONObject();
            JSONObject jsonPublication = new JSONObject();

            jsonReader.put("id", reader.getId());
            jsonReader.put("login", reader.getLogin());
            jsonReader.put("password", reader.getPassword());
            jsonReader.put("languageId", reader.getLanguageId());

            jsonPublication.put("id", publication.getId());
            jsonPublication.put("author", publication.getAuthor());
            jsonPublication.put("name", publication.getName());
            jsonPublication.put("cost", publication.getCost());
            jsonPublication.put("languageId", publication.getLanguageId());

            jsonPayment.put("id", payment.getId());
            jsonPayment.put("cost", payment.getCost());
            jsonPayment.put("isPayed", payment.isPayed());

            jsonSubscription.put("subscriptionId", entity.getId());
            jsonSubscription.put("reader", jsonReader);
            jsonSubscription.put("publication", jsonPublication);
            jsonSubscription.put("payment", jsonPayment);
            jsonSubscription.put("startDate", entity.getStartSubscription().toString());
            jsonSubscription.put("endDate", entity.getEndSubscription().toString());

            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(jsonSubscription.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}