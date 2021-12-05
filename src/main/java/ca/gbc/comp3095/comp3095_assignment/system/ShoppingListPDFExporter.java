package ca.gbc.comp3095.comp3095_assignment.system;

import java.awt.Color;
import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import ca.gbc.comp3095.comp3095_assignment.recipe.ingredient.Ingredient;
import ca.gbc.comp3095.comp3095_assignment.shoppinglist.ShoppingList;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;


public class ShoppingListPDFExporter {
    private ShoppingList shoppingList;

    public ShoppingListPDFExporter(ShoppingList shoppingList) {
        this.shoppingList = shoppingList;
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(5);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Ingredient Name", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Amount", font));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table) {
        for (Ingredient ingredient : shoppingList.getIngredients()) {
            table.addCell(ingredient.getName());
            table.addCell(ingredient.getAmount());
        }
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Shopping List", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);

        document.add(p);

        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {3.5f, 3.5f});
        table.setSpacingBefore(10);

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);

        document.close();

    }
}