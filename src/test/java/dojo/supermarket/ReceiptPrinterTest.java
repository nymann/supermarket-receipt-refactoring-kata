package dojo.supermarket;

import dojo.supermarket.model.Discount;
import dojo.supermarket.model.Product;
import dojo.supermarket.model.ProductUnit;
import dojo.supermarket.model.Receipt;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class ReceiptPrinterTest {
    Product toothbrush = new Product("toothbrush", ProductUnit.EACH);
    Product apples = new Product("apples", ProductUnit.KILO);
    Receipt receipt = new Receipt();

    @Test
    void oneItem() {
        double priceForOne = 0.75;
        receipt.addProduct(toothbrush, 1, priceForOne, priceForOne);
        Approvals.verify(new ReceiptPrinter().printReceipt(receipt));
    }

    @Test
    void twoItems() {
        receipt.addProduct(toothbrush, 1, 0.99, 0.99);
        receipt.addProduct(apples, 2, 1, 2);
        Approvals.verify(new ReceiptPrinter().printReceipt(receipt));
    }

    @Test
    void nonIntegerWeight() {
        receipt.addProduct(apples, 2.3, 0.99, 2.3 * 0.99);
        Approvals.verify(new ReceiptPrinter().printReceipt(receipt));
    }

    @Test
    void sameProductTwice() {
        double priceForOne = 0.99;
        receipt.addProduct(toothbrush, 2, priceForOne, 2 * priceForOne);
        receipt.addProduct(toothbrush, 3, priceForOne, 3 * priceForOne);
        Approvals.verify(new ReceiptPrinter().printReceipt(receipt));
    }

    @Test
    void discount() {
        double priceForOne = 0.99;
        receipt.addProduct(toothbrush, 3, priceForOne, 3 * priceForOne);
        receipt.addDiscount(new Discount(toothbrush, "3 for 2", -priceForOne));
        Approvals.verify(new ReceiptPrinter().printReceipt(receipt));
    }
}
