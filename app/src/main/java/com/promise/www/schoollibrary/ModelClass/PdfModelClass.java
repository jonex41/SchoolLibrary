package com.promise.www.schoollibrary.ModelClass;

import java.io.Serializable;

public class PdfModelClass implements Serializable {

    private String pdfName, pdfUrl, pdfImage, typeOfBook;

    public PdfModelClass() {
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    public String getPdfImage() {
        return pdfImage;
    }

    public void setPdfImage(String pdfImage) {
        this.pdfImage = pdfImage;
    }

    public String getTypeOfBook() {
        return typeOfBook;
    }

    public void setTypeOfBook(String typeOfBook) {
        this.typeOfBook = typeOfBook;
    }
}
