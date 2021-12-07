package edu.ktu.ktukrash;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.source.DocumentSource;
import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;;
import com.itextpdf.kernel.pdf.PdfDictionary;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class activity_pdf extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);


        InputStream inputStream;
        Bundle bundle = getIntent().getExtras();
        HashMap<String, String> data1 = (HashMap<String, String>) bundle.get("pdfData1");
        HashMap<String, Object> data2 = (HashMap<String, Object>) bundle.get("pdfData2");
        //Toast.makeText(this, data1.get("FP_Name"), Toast.LENGTH_LONG).show();
        //Toast.makeText(this, (String) data2.get("SP_Name"), Toast.LENGTH_LONG).show();



        try {
            String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();

            //DynamicPDF works good but is only a trial
            //Itext
            //PdfReader reader = new PdfReader(getAssets().open("deklaracija.pdf"));
            //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(path + "/Filled.pdf"));
            //AcroFields form = stamper.getAcroFields();
            //form.setField("A_draudejas_pavarde", "Taip");
            //reader.close();

            PdfReader reader = new PdfReader(getAssets().open("deklaracija.pdf"));
            String outputName = path + "/" + data1.get("FP_CarNumber") + "_" + data2.get("SP_CarNumber") + ".pdf";
            PdfWriter writer = new PdfWriter(outputName);
            PdfDocument pdfDoc = new PdfDocument(reader, writer);
            PdfAcroForm form = PdfAcroForm.getAcroForm(pdfDoc, true);
            Map<String, PdfFormField> fields = form.getFormFields();
            PdfDictionary style = new PdfDictionary();
            fields.get("A_draudejas_pavarde").setValue(data1.get("FP_LastName")).setFontSize(12).setBorderWidth(0f);
            fields.get("A_draudejas_vardas").setValue(data1.get("FP_Name")).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudejas_pavarde").setValue(data2.get("SP_LastName").toString()).setFontSize(12).setBorderWidth(0f);
            fields.get("B_draudejas_vardas").setValue(data2.get("SP_Name").toString()).setFontSize(12).setBorderWidth(0f);

            form.flattenFields();
            pdfDoc.close();


            //perdaryti nes fieldu nerodo
            PDFView myPdf = (PDFView) findViewById(R.id.pdfView);
            File file = new File(outputName);
            myPdf.fromFile(file).load();

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}