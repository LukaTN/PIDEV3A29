package com.example.gestionconference.Util;
import com.example.gestionconference.Models.BudgetModels.EstimatedIncomes;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PDFExporter {

    public static void exportToPDF(TableView<EstimatedIncomes> tableView, File file) throws IOException, DocumentException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());

        // Adding table headers
        for (TableColumn<EstimatedIncomes, ?> column : tableView.getColumns()) {
            pdfTable.addCell(column.getText());
        }

        // Adding table data
        for (EstimatedIncomes item : tableView.getItems()) {
            for (TableColumn<EstimatedIncomes, ?> column : tableView.getColumns()) {
                Object cellData = column.getCellData(item);
                pdfTable.addCell(cellData == null ? "" : cellData.toString());
            }
        }

        document.add(pdfTable);
        document.close();
    }
}
