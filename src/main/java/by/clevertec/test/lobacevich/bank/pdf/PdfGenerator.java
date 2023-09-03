package by.clevertec.test.lobacevich.bank.pdf;

import by.clevertec.test.lobacevich.bank.di.Singleton;
import by.clevertec.test.lobacevich.bank.entity.Account;
import by.clevertec.test.lobacevich.bank.entity.Transaction;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Singleton
public class PdfGenerator {

    public void printCheck(Transaction transaction, String checkNumber) throws IOException {
        String path = "check/check" + checkNumber + ".pdf";
        PdfWriter pdfWriter = new PdfWriter(path);
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDocument, PageSize.A4);
        String fontPass = "./src/main/resources/SourceCodePro-VariableFont_wght.ttf";
        PdfFont font = PdfFontFactory.createFont(fontPass, PdfEncodings.IDENTITY_H);
        document.setFont(font);
        document.add(new Paragraph("----------------------------------------"));
        document.add(new Paragraph("|            Банковский чек            |"));
        document.add(new Paragraph(getLine("Чек:", checkNumber)));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        document.add(new Paragraph(getLine(transaction.getDateTime().toLocalDate().toString(),
                transaction.getDateTime().toLocalTime().format(formatter))));
        document.add(new Paragraph(getLine("Тип транзакции:",
                getTransactionType(transaction))));
        String senderBank;
        if (transaction.getAccountSender() != null) {
            senderBank = transaction.getAccountSender().getBank().getName();
        } else {
            senderBank = "";
        }
        document.add(new Paragraph(getLine("Банк отправителя:", senderBank)));
        String receiverBank;
        if (transaction.getAccountReceiver() != null) {
            receiverBank = transaction.getAccountSender().getBank().getName();
        } else {
            receiverBank = "";
        }
        document.add(new Paragraph(getLine("Банк получателя:", receiverBank)));
        String senderAccount;
        if (transaction.getAccountSender() != null) {
            senderAccount = transaction.getAccountSender().getAccountNumber();
        } else {
            senderAccount = "";
        }
        document.add(new Paragraph(getLine("Счет отправителя:", senderAccount)));
        String receiverAccount;
        if (transaction.getAccountReceiver() != null) {
            receiverAccount = transaction.getAccountSender().getAccountNumber();
        } else {
            receiverAccount = "";
        }
        document.add(new Paragraph(getLine("Счет получателя:", receiverAccount)));
        document.add(new Paragraph(getLine("Сумма:", transaction.getSum().toString())));
        document.add(new Paragraph("|--------------------------------------|"));
        document.close();
    }

    private String getLine(String left, String right) {
        String spaces = getSpaces(left, right);
        return "| " + left + spaces + right + " |";
    }

    private String getSpaces(String left, String right) {
        return " ".repeat(Math.max(0, 36 - left.length() - right.length()));
    }

    private String getTransactionType(Transaction transaction) {
        Account sender = transaction.getAccountSender();
        Account receiver = transaction.getAccountReceiver();
        if (sender != null && receiver == null) {
            return "Снятие";
        } else if (sender == null && receiver != null) {
            return "Пополнение";
        } else {
            return "Перевод";
        }
    }
}
