package com.LeBao.sales.services;

import com.LeBao.sales.entities.Order;
import com.LeBao.sales.entities.OrderDetail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender sender;


    public void sendEmail(Order order) {

        Map<String, Object> model = new HashMap<>();
        model.put("orderDetails", order.getOrderDetails());
        MimeMessage mimeMessage = sender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

//            Template template = configuration.getTemplate("email-template.ftl");
//            String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

            helper.setTo(order.getUser().getEmail());
            helper.setSubject("Order Confirmation and Invoice");
            helper.setFrom("leducbao12a1cmb1920@gmail.com");

            String emailContent = "<h1>Here your order details</h1>";

            emailContent += "<p><strong>Recipient Name:</strong> " + order.getUser().getFirstName() + " " + order.getUser().getLastName() + "</p>";
            emailContent += "<p><strong>Address:</strong> , " + order.getShippingAddress() + "</p>";
            emailContent += "<p><strong>Total Price:</strong> $" + order.getTotalAmount() + "</p>";

            emailContent += "<table border='1' cellpadding='10' style='border-collapse: collapse;'>";
            emailContent += "<tr>";
            emailContent += "<th>Product's name</th>";
            emailContent += "<th>Price</th>";
            emailContent += "<th>Quantity</th>";
            emailContent += "</tr>";

            for (OrderDetail orderDetail : order.getOrderDetails()) {
                emailContent += "<tr>";
                emailContent += "<td>" + orderDetail.getProduct().getProductName() + "</td>";
                emailContent += "<td>$" + orderDetail.getUnitPrice() + "</td>";
                emailContent += "<td>" + orderDetail.getQuantity() + "</td>";
                emailContent += "</tr>";
            }
            helper.setText(emailContent, true);
            sender.send(mimeMessage);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
