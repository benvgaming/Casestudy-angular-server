package com.info5059.casestudy.purchaseorder;

import com.info5059.casestudy.product.Product;
import com.info5059.casestudy.product.ProductRepository;
import com.info5059.casestudy.vendor.Vendor;
import com.info5059.casestudy.vendor.VendorRepository;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;

import org.springframework.web.servlet.view.document.AbstractPdfView;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Generator - a class for testing how to create dynamic output in PDF
 * format using the iText 7 library
 */
public abstract class PurchaseOrderPDFGenerator extends AbstractPdfView {
        public static ByteArrayInputStream generatePurchaseOrder(String repid,
                        PurchaseOrderRepository purchaseorderRepository,
                        VendorRepository vendorRepository,
                        ProductRepository productRepository)
                        throws IOException, java.io.IOException {

                URL imageUrl = PurchaseOrderPDFGenerator.class.getResource("/static/image/logo.png");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter writer = new PdfWriter(baos);
                // Initialize PDF document to be written to a stream not a file
                PdfDocument pdf = new PdfDocument(writer);
                // Document is the main object
                Document document = new Document(pdf);
                PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
                // add the image to the document
                PageSize pg = PageSize.A4;
                Image img = new Image(ImageDataFactory.create(imageUrl)).scaleAbsolute(120, 40)
                                .setFixedPosition(pg.getWidth() / 2 - 60, 750);
                document.add(img);
                // now let's add a big heading
                document.add(new Paragraph("\n\n"));
                Locale locale = new Locale("en", "US");
                NumberFormat formatter = NumberFormat.getCurrencyInstance(locale);
                try {
                        document.add(new Paragraph("\n"));
                        Optional<PurchaseOrder> optPurchaseOrder = purchaseorderRepository.findById(Long.parseLong(repid));
                        if (optPurchaseOrder.isPresent()) {
                                document.add(new Paragraph("PurchaseOrder# " + repid).setFont(font).setFontSize(18).setBold()
                                                .setMarginRight(pg.getWidth() / 2 - 75).setMarginTop(-10)
                                                .setTextAlignment(TextAlignment.RIGHT));
                                document.add(new Paragraph("\n\n"));
                        }
                        // 2 column vendor table

                        Table venTable  = new Table(2);
                        venTable.setWidth((new UnitValue(UnitValue.PERCENT,30)));
                        venTable.setBorder(Border.NO_BORDER); 
                        Optional<Vendor> ven = vendorRepository.findById(optPurchaseOrder.get().getVendorid());
                        
                        Cell venCell = new Cell().add(new Paragraph("Vendor:"))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setBold()
                                                .setTextAlignment(TextAlignment.LEFT)
                                                .setBorder(Border.NO_BORDER);
                        venTable.addCell(venCell);
                        venCell = new Cell().add(new Paragraph(ven.get().getName()))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setBold()
                                                .setTextAlignment(TextAlignment.LEFT).setBackgroundColor(ColorConstants.LIGHT_GRAY)
                                                .setBorder(Border.NO_BORDER);


                        venTable.addCell(venCell);


                        venCell = new Cell().setBorder(Border.NO_BORDER);
                        venTable.addCell(venCell);


                        venCell = new Cell().add(new Paragraph(ven.get().getAddress1()))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setBold()
                                                .setTextAlignment(TextAlignment.LEFT).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBorder(Border.NO_BORDER);

;
                        venTable.addCell(venCell);

                        venCell = new Cell().setBorder(Border.NO_BORDER);
                        venTable.addCell(venCell);
                        venCell = new Cell().add(new Paragraph(ven.get().getCity()))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setBold()
                                                .setTextAlignment(TextAlignment.LEFT).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBorder(Border.NO_BORDER);
                        venTable.addCell(venCell);

                        venCell = new Cell().setBorder(Border.NO_BORDER);
                        venTable.addCell(venCell);
                        venCell = new Cell().add(new Paragraph(ven.get().getPostalcode()))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setBold()
                                                .setTextAlignment(TextAlignment.LEFT).setBackgroundColor(ColorConstants.LIGHT_GRAY).setBorder(Border.NO_BORDER);
                        venTable.addCell(venCell);
                        document.add(venTable);
                        Table table = new Table(5);
                        table.setWidth(new UnitValue(UnitValue.PERCENT, 100));
                        // Unfortunately we must format each cell individually :(
                        // table headings
                        Cell cell = new Cell().add(new Paragraph("Product Code")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        table.addCell(cell);
                        cell = new Cell().add(new Paragraph("Description")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        table.addCell(cell);
                        cell = new Cell().add(new Paragraph("Qty Sold")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold())
                                        .setTextAlignment(TextAlignment.CENTER);
                        table.addCell(cell);
                        // table details
                        cell = new Cell().add(new Paragraph("Price")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.LEFT));
                        table.addCell(cell);               

                        cell = new Cell().add(new Paragraph("Ext. Price")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.LEFT));
                        table.addCell(cell);                
                        BigDecimal total = new BigDecimal(0.0);                
                        for(PurchaseOrderLineitem item : optPurchaseOrder.get().getItems())
                        {
                                Optional<Product> prod = productRepository.findById(item.getProductid());
                                cell = new Cell().add(new Paragraph(prod.get().getId())
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setTextAlignment(TextAlignment.LEFT));
                                table.addCell(cell);

                                cell = new Cell().add(new Paragraph(prod.get().getName())
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setTextAlignment(TextAlignment.LEFT));
                                table.addCell(cell);
                        
                        
                                cell = new Cell().add(new Paragraph(String.valueOf( item.getQty()))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setTextAlignment(TextAlignment.LEFT));
                                table.addCell(cell);


                                cell = new Cell().add(new Paragraph(formatter.format( item.getPrice()))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setTextAlignment(TextAlignment.RIGHT));
                                table.addCell(cell);
                                BigDecimal qty = new BigDecimal(item.getQty());
                                
                                cell = new Cell().add(new Paragraph(formatter.format( item.getPrice().multiply(qty)))
                                                .setFont(font)
                                                .setFontSize(12)
                                                .setTextAlignment(TextAlignment.RIGHT));
                                table.addCell(cell);
                                total = total.add( item.getPrice().multiply(qty));
                        }

                        cell = new Cell(3, 4).add(new Paragraph("Sub Total:")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER);
                        table.addCell(cell);
                        cell = new Cell(4,5).add(new Paragraph(formatter.format(total))
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.RIGHT));
                        table.addCell(cell);

                        cell = new Cell(3,4).add(new Paragraph("Tax")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER);

                        table.addCell(cell);

                        BigDecimal tax = new BigDecimal(0.13);
                        cell = new Cell(4,5).add(new Paragraph(formatter.format(total.multiply(tax)))
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.RIGHT));
                        table.addCell(cell);

                        cell = new Cell(3,4).add(new Paragraph("PO Total")
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.RIGHT)).setBorder(Border.NO_BORDER);

                        table.addCell(cell);
                        cell = new Cell(4,5).add(new Paragraph(formatter.format(total.multiply(tax).add(total)))
                                        .setFont(font)
                                        .setFontSize(12)
                                        .setBold()
                                        .setTextAlignment(TextAlignment.RIGHT).setBackgroundColor(ColorConstants.YELLOW));

                        table.addCell(cell);
                        document.add(table);
                        document.add(new Paragraph("\n\n"));
                        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
                        document.add(new Paragraph(dateFormatter.format(LocalDateTime.now()))
                                        .setTextAlignment(TextAlignment.CENTER));
                        document.close();
                } catch (Exception ex) {
                        Logger.getLogger(PurchaseOrderPDFGenerator.class.getName()).log(Level.SEVERE, null, ex);
                }
                // finally send stream back to the controller
                return new ByteArrayInputStream(baos.toByteArray());
        }
}